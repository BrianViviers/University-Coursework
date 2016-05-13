/*
 ============================================================================
 Name        : SoundServer.c

 Author      : DreamTeam (Brian Viviers, Philip Edwards, Dominic Youel, Richard Imms)
 Version     :
 Copyright   : Your copyright notice
 Description : SoundServer is responsible for receiving requests from the
 	 	 	   EnvServer (CGI.c) to record short samples of audio and calculate
 	 	 	   noise levels for that sample. It can also be requested to start/stop
 	 	 	   sampling and saving those samples, erase all saved samples and get
 	 	 	   a total saved samples value.
 	 	 	   It communicates with the EnvServer using TCP/IP sockets on port 9735
 ============================================================================
 */

#define _XOPEN_SOURCE 500
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdint.h>
#include <string.h>
#include <strings.h>
#include <errno.h>
#include <math.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <pthread.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#include <fcntl.h>
#include <time.h>
#include <pthread.h>
#include <signal.h>
#include <syslog.h>

typedef int8_t SAMPLE;

//Sampling rate (samples per second) - recommend 16000 or higher
#define SAMPLING_RATE 16000

//Duration in seconds
#define PLAY_DURATION 2

//Size of buffer
#define BUFFER_SIZE_IN_BYTES (SAMPLING_RATE*PLAY_DURATION*sizeof(SAMPLE))

// **************************************************
// 					 GLOBAL VARIABLES
// **************************************************


pthread_t samplerThread;
int doSampling; //Sampling start/stop variable
int res;
void* thread_res;

int client_sock_fd;		//file descriptor for client socket

//Buffer to hold the data
char buf[BUFFER_SIZE_IN_BYTES];

//Streams that represent the process pipes
FILE* inputStream=NULL;

//Corresponding fileid
int fidIn;
int fidOut;

//Bytes read/written
int iBytesRead=0;
int iBytesWritten = 0;

int counter;

/*
 * Function:  recordSample
 * --------------------
 * Method which records audio for a short duration (2 seconds) and
 * then calculates a noise level in decibels for that duration.
 */
float recordSample() {
	float db = 0.0f;

	FILE *noiseLevelRec = popen("./NoiseLevelRecord", "r");
	if (noiseLevelRec) {
		char buffer[64];

		fscanf(noiseLevelRec, "%s", buffer);
		pclose(noiseLevelRec);

		if (strlen(buffer) > 0) {
			db = atof(buffer);
		}
	}

	return db;
}

/*
 * Function:  writeToSocket
 * --------------------
 * Writes the values given to the open socket.
 *
 *  values[]: The content to be written out through
 *    		  the socket from which the request was
 *    		  made.
 *
 *  size: The number of elements with values[].
 */
void writeToSocket(char* values[], int size) {
	char buffer[30];
	int n;
	unsigned int i;
	for (i=0; i<size; i++) {
		n = write(client_sock_fd, values[i], strlen(values[i]));

		if (n < 0) {
			//perror("ERROR writing to socket");
			break;
		}
		n = read(client_sock_fd, buffer, 32);
		if (n < 0) {
			//perror("ERROR reading from socket");
		}
		else if (strcmp(buffer, "z") == 0) {
			continue;
		} else {
			break;
		}
	}
}

/*
 * Function:  getCurrentSample
 * --------------------
 * Records a new noise level sample and writes
 * the value to the the socket of the caller
 */
void getCurrentSample() {
	float db = recordSample();
	char decibels[20];
	sprintf(decibels, "%.1f", fabs(db));

	char* strMessage[1] = {decibels};
	writeToSocket(strMessage, 1);
}

/*
 * Function:  getTotalStoredSamples
 * --------------------
 * Reads the first line of the saved noise level readings
 * file and then returns the total readings.
 * This is done by opening the CircularBuffer application with an argument
 * indicating which text file the noise levels are located in and
 * another argument indicating to get total saved samples.
 *
 *  returns: the approximate value of pi obtained by suming the first n terms
 *           in the above series
 *           returns zero on error (if n is non-positive)
 */
int getTotalStoredSamples() {
	int total = 0;
	FILE *circularBuffer = popen("./CircularBuffer /root/noiseLevels.txt total_samples", "r");
	if (!circularBuffer) {
		//perror("Cannot open EEPROM.");
	} else {
		char buffer[64];
		fscanf(circularBuffer, "%s", buffer);
		pclose(circularBuffer);
		if (strlen(buffer) > 0) {
			sscanf(buffer, "%d", &total);
		}
	}
	return total;
}

/*
 * Function:  returnTotalStoredSamples
 * --------------------
 * Sends the value of the total saved noise
 * level readings to the caller through the socket.
 */
void returnTotalStoredSamples() {
	int total = getTotalStoredSamples();
	char* totalAsAstring = malloc(3);
	sprintf(totalAsAstring, "%d", total);

	char* strMessage[1] = {totalAsAstring};
	writeToSocket(strMessage, 1);
	free(totalAsAstring);
}

/*
 * Function:  saveToFile
 * --------------------
 * Saves a new noise level reading to a file. This is done by
 * opening the CircularBuffer application with an argument
 * indicating which text file the noise levels are located in and
 * another two arguments, the first indicating to write and the second
 * is the value to write.
 *
 * sample[]: char array holding the new noise level reading.
 */
void saveToFile(char sample[]) {
	char* arguments;
	char args[] = {"./CircularBuffer /root/noiseLevels.txt write "};

	arguments = malloc(strlen(args) + strlen(sample) + 1);
	strcpy(arguments, args);
	strcat(arguments, sample);

	FILE *circularBuffer = popen(arguments, "r");
	if (circularBuffer) {
		pclose(circularBuffer);
		free(arguments);
	}
}

/*
 * Function:  eraseAllSamples
 * --------------------
 * Erases all the saved noise level readings. This is done by
 * opening the CircularBuffer application with an argument
 * indicating which text file the noise levels are located in and
 * another argument indicating to erase all.
 * The result of the erase is written to the socket.
 */
void eraseAllSamples() {

	FILE *circularBuffer = popen("./CircularBuffer /root/noiseLevels.txt erase_all", "r");
	if (circularBuffer) {
		char buffer[64];
		char* samples[1];

		fscanf(circularBuffer, "%s", buffer);
		samples[0] = malloc(30);
		strcpy(samples[0], buffer);

		pclose(circularBuffer);

		writeToSocket(samples, 1);

		free(samples[0]);
	}
}

/*
 * Function:  getMPastSamples
 * --------------------
 * Gets stored noise level readings and send them to the caller.
 * This is done by opening the CircularBuffer application with an argument
 * indicating which text file the noise levels are located in and
 * another two arguments indicating to read M samples and the value of M.
 * The returned values are written to the socket.
 *
 * M: number of how many readings to get
 */
void getMPastSamples(int M) {

	char* arguments;

	// If the value of M is greater than 9 then the length of the
	// int will be 2 characters wide.
	int mSize = 1;
	if (M > 9) {
		mSize = 2;
	}
	char args[] = {"./CircularBuffer /root/noiseLevels.txt read_m_samples "};

	// Allocate memory for the arguments dynamically.
	arguments = malloc(strlen(args) + mSize + 1);
	strcpy(arguments, args);

	// Convert M to a string to send as an argument to CircularBuffer.
	char numberArray[3];
	sprintf(numberArray,"%d", M);

	size_t len = strlen(arguments);
	arguments[len] = numberArray[0];
	if (mSize > 1)
		arguments[len + 1] = numberArray[1];
	arguments[len + mSize] = '\0';

	// Open the CircularBuffer
	FILE *circularBuffer = popen(arguments, "r");

	// Free the memory previously allocated.
	free(arguments);


	if (circularBuffer) {
		char buffer[255];
		char* samples[M];
		int count = 0;

		// Read in every samples one at a time and save into an array.
		while (fscanf(circularBuffer, "%s", buffer) != EOF) {
			// Dynamically allocate memory for the samples.
			samples[count] = malloc(30);
			strcpy(samples[count++], buffer);
		}
		pclose(circularBuffer);
		writeToSocket(samples, count);

		// Free the memory
		int i;
		for (i=0;i<count;i++) {
			free(samples[i]);
		}
	}
}

/*
 * Function:  getSampleEveryMinute
 * --------------------
 * The method which is called every minute while sampling noise levels.
 * It will request a new noise level and save it to the circular buffer
 * text file.
 *
 *  *arg: a void parameter required by threads.
 */
void* getSampleEveryMinute(void *arg) {
	while (doSampling == 1) {
		float db = recordSample();
		char decibels[20];
		sprintf(decibels, "%.1f", fabs(db));

		saveToFile(decibels);
		sleep(60);
	}
	return NULL;
}

/*
 * Function:  startSampling
 * --------------------
 * Called to start the noise leveling sampling.
 */
void startSampling() {
	doSampling = 1;
	if(pthread_create(&samplerThread, NULL, getSampleEveryMinute, (void*)"")) {
		//printf("Error creating thread\n");
	}
	char* response[1] = {"Sound sampling started"};
	writeToSocket(response, 1);
}

/*
 * Function:  stopSampling
 * --------------------
 * Called to terminate the noise leveling sampling.
 */
void stopSampling() {
	doSampling = 0;
	if(pthread_join(samplerThread, &thread_res)) {
		//printf("Error joining thread\n");
	}
	char* response[1] = {"Sound sampling stopped"};
	writeToSocket(response, 1);
}

/*
 * Function:  isSampling
 * --------------------
 * Called to query whether the sampling
 * is currently active or not.
 */
void isSampling() {
	if (doSampling == 1) {
		char* response[1] = {"Active"};
		writeToSocket(response, 1);
	} else {
		char* response[1] = {"Inactive"};
		writeToSocket(response, 1);
	}
}

/*
 * Function:  run
 * --------------------
 * Contains all the code for receiving a request
 * through a socket and calling the required functions.
 */
void run() {
	/* Socket variable */
	int server_sock_fd;		/* file descriptor for server socket */

	socklen_t server_len, client_len;

	struct sockaddr_in server_address;
	struct sockaddr_in client_address;

	int n;
	char buffer[32];

	/* Remove any old sockets and re-create */
	unlink("server_socket");

	/* Obtain the file descritor for the socket */
	server_sock_fd = socket(AF_INET, SOCK_STREAM, 0);

	/* Name the socket and set properties */
	server_address.sin_family 		= AF_INET;					//This is the Internet protocol
	server_address.sin_addr.s_addr  = htonl(INADDR_ANY);		//Accept from any address (note the conversion function)
	server_address.sin_port         = htons(9735);				//remember to use host-to-network-type conversion
	server_len = sizeof(server_address);
	bind(server_sock_fd, (struct sockaddr*)&server_address, server_len);	/* bind socket to address */

	/* Create a connection queue and define max number of client connections */
	listen(server_sock_fd, 1);


	/* Main loop (ctrl-C will quit this application) */
	while (1) {

		client_len = sizeof(client_address);

		/*Accept incoming client connection and create client socket
		Returns a file descriptor for each connection in the queue (BLOCKS IF QUEUE IS EMPTY) */
		client_sock_fd = accept(server_sock_fd, (struct sockaddr*)&client_address, &client_len);

		if (client_sock_fd < 0) {
			//perror("ERROR on accept");
		}
		else {
			bzero(buffer,32);

			n = read(client_sock_fd,buffer,32);
			if (n < 0) {
				//perror("ERROR reading from socket");
			}
			else {
				if (strcmp("current_audio",buffer) == 0) {
					getCurrentSample();

				} else if (strcmp("total_audio_samples",buffer) == 0) {
					returnTotalStoredSamples();

				} else if (strcmp("read_m_samples",buffer) == 0) {

					// Write a z to say the server received the message
					write(client_sock_fd, "z", 1);
					int M;

					char value[3];
					bzero(value, 3);
					n = read(client_sock_fd,value,3);
					value[2] = '\0';

					if (n < 0) {
						//perror("ERROR reading from socket");
						break;
					}

					sscanf(value, "%d", &M);

					if (M > 1) {
						int totalSamples;
						totalSamples = getTotalStoredSamples();

						// If trying to read more than total samples
						// then limit to total samples.
						int m;
						if (M > totalSamples)
							m = totalSamples;
						else
							m = M;

						getMPastSamples(m);
					} else {
						// Do something here if requested 1 or less samples
					}
				} else if (strcmp("start_sampling",buffer) == 0) {

					startSampling();

				} else if (strcmp("stop_sampling",buffer) == 0) {

					stopSampling();

				} else if (strcmp("erase_all_samples",buffer) == 0) {

					eraseAllSamples();

				} else if (strcmp("is_sampling",buffer) == 0) {

					isSampling();

				} else {
					//printf("Unrecognised_call\n");
					/* Do something else */
				}
			}
		}
		/* Close the client socket */
		close(client_sock_fd);
	}
}

/*
 * Function:  startDaemon
 * --------------------
 * Method which converts the main process into a daemon process.
 */
void startDaemon(){
	pid_t process_id = 0;
	pid_t sid = 0;

	/* Create child process */
	process_id = fork();

	/* Indication of fork() failure */
	if (process_id < 0)	{
		printf("fork failed!\n");
		exit(1);
	}

	/* Kill the parent process */
	if (process_id > 0) {
		printf("- Process_id of child process %d \n"
				"- To stop SoundServer type: kill %d\n", process_id, process_id);
		// return success in exit status
		exit(0);
	}

	/* unmask the file mode */
	umask(0);

	/* set new session */
	sid = setsid();
	if(sid < 0)	exit(1);

	/* Change the current working directory to root. */
	chdir("/root");

	/* Close stdin, stdout and stderr */
	close(STDIN_FILENO);
	close(STDOUT_FILENO);
	close(STDERR_FILENO);
}

int main(void) {
	startDaemon();
	run();

	close(client_sock_fd);
	return EXIT_SUCCESS;
}
