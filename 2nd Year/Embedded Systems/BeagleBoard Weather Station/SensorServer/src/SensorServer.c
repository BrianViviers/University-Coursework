/*
 ============================================================================
 Name        : SensorServer.c

 Author      : DreamTeam (Brian Viviers, Philip Edwards, Dominic Youel, Richard Imms)
 Version     :
 Copyright   : Your copyright notice
 Description : SensorServer is responsible for receiving requests from the
 	 	 	   EnvServer (CGI.c) to request temperature and pressure readings
 	 	 	   from the on board sensors. These readings are saved into the on board
 	 	 	   EEPROM. It can also be requested to start/stop sampling and saving
 	 	 	   those samples, erase all saved samples and get a total saved samples value.
 	 	 	   It communicates with the EnvServer using TCP/IP sockets on port 9734
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <time.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <signal.h>
#include <syslog.h>

pthread_t samplerThread;
int doSampling; //Sampling start/stop variable
int res;
void* thread_res;

int client_sock_fd;		//file descriptor for client socket

/*
 * Function:  writeToSocket
 * --------------------
 * computes an approximation of pi using:
 *    pi/6 = 1/2 + (1/2 x 3/4) 1/5 (1/2)^3  + (1/2 x 3/4 x 5/6) 1/7 (1/2)^5 +
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
 * Function:  getCurrentReadings
 * --------------------
 * Opens the SENSORS executable and requests the
 * current temperature and pressure readings.
 * Sends the readings back to the calling socket.
 */
void getCurrentReadings() {
	FILE *sensors = popen("./SENSORS",   "r");
	if (!sensors) {
		//perror("Cannot open SENSORS.");
	} else {
		char buffer[64];
		char temp[10];
		char pres[10];

		fscanf(sensors, "%s", buffer);
		strcpy(temp,buffer);
		fscanf(sensors, "%s", buffer);
		strcpy(pres,buffer);

		pclose(sensors);

		if (strlen(temp) > 0 && strlen(pres) > 0) {
			char* strMessage[2] = {temp, pres};
			writeToSocket(strMessage, 2);
		} else {
			//perror("Failed to read current values");
		}
	}
}

/*
 * Function:  saveNewReadings
 * --------------------
 * Saves a new temperature and pressure reading
 * into the EEPROM
 */
void saveNewReadings() {
	FILE *sensors = popen("./SENSORS",   "r");
	if (!sensors) {
		//perror("Cannot open SENSORS.");
	} else {
		char buffer[100];
		char temp[10];
		char pres[10];

		fscanf(sensors, "%s", buffer);
		strcpy(temp,buffer);
		fscanf(sensors, "%s", buffer);
		strcpy(pres,buffer);
		pclose(sensors);

		char* arguments;
		arguments = malloc(14 + strlen(temp) + strlen(pres) + 2);
		strcpy(arguments, "./EEPROM write ");
		strcat(arguments, temp);
		strcat(arguments, " ");
		strcat(arguments, pres);

		FILE *eeprom = popen(arguments, "r");
		if (!eeprom) {
			//perror("Cannot open EEPROM.");
		} else {
			pclose(eeprom);
			free(arguments);
		}
	}
}

/*
 * Function:  getTotalStoredSamples
 * --------------------
 * Opens the EEPROM executable and requests the total
 * saved reading in the EEPROM. Sends the total back to
 * the calling socket
 */
void getTotalStoredSamples() {
	FILE *eeprom = popen("./EEPROM total_samples", "r");
	if (!eeprom) {
		//perror("Cannot open EEPROM.");
	} else {
		char buffer[64];
		fscanf(eeprom, "%s", buffer);
		pclose(eeprom);
		if (strlen(buffer) > 0) {
			char* strMessage[2] = {buffer};
			writeToSocket(strMessage, 1);
		} else {
			//perror("Failed to read current values");
		}
	}
}

/*
 * Function:  getMPastSamples
 * --------------------
 * Gets stored temperature and pressure readings
 * and send them to the caller.
 *
 * M: number of how many readings to get
 */
void getMPastSamples(int M) {
	char* arguments;
	int mSize = 1;
	if (M > 9) {
		mSize = 2;
	}

	arguments = malloc(strlen("./EEPROM read_m_samples ") + mSize + 1);
	strcpy(arguments, "./EEPROM read_m_samples ");
	char numberArray[3];
	sprintf(numberArray,"%d", M);

	size_t len = strlen(arguments);
	arguments[len] = numberArray[0];
	if (mSize > 1)
		arguments[len + 1] = numberArray[1];
	arguments[len + mSize] = '\0';

	FILE *eeprom = popen(arguments, "r");

	free(arguments);

	if (!eeprom) {
		//perror("Cannot open EEPROM.");
	} else {
		char buffer[255];
		char* samples[M*2];
		int count = 0;

		while (fscanf(eeprom, "%s", buffer) != EOF) {
			samples[count] = malloc(30);
			strcpy(samples[count++], buffer);
		}
		pclose(eeprom);
		writeToSocket(samples, count);

		int i;
		for (i=0;i<count;i++) {
			free(samples[i]);
		}
	}
}

/*
 * Function:  getSampleEveryMinute
 * --------------------
 * The method which is called every minute while sampling
 * temperature and pressure readings.
 *
 *  *arg: a void parameter required by threads.
 */
void* getSampleEveryMinute(void *arg) {
	while (doSampling == 1) {
		saveNewReadings();
		sleep(60);
	}
	return NULL;
}

/*
 * Function:  startSampling
 * --------------------
 * Called to start the temperature and
 * pressure sampling.
 */
void startSampling() {
	doSampling = 1;
	if(pthread_create(&samplerThread, NULL, getSampleEveryMinute, (void*)"")) {
		//printf("Error creating thread\n");
	}
	char* response[1] = {"Sensor sampling started"};
	writeToSocket(response, 1);
}

/*
 * Function:  stopSampling
 * --------------------
 * Called to terminate the temperature
 * and pressure sampling.
 */
void stopSampling() {
	doSampling = 0;
	if(pthread_join(samplerThread, &thread_res)) {
		//printf("Error joining thread\n");
	}
	char* response[1] = {"Sensor sampling stopped"};
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
 * Function:  eraseAllSamples
 * --------------------
 * Erases all the saved temperature and pressure readings.
 */
void eraseAllSamples() {
	FILE *eeprom = popen("./EEPROM erase_all", "r");
	if (!eeprom) {
		//perror("Cannot open EEPROM.");
	} else {
		char buffer[64];
		char* samples[1];

		fscanf(eeprom, "%s", buffer);
		samples[0] = malloc(30);
		strcpy(samples[0], buffer);

		pclose(eeprom);

		writeToSocket(samples, 1);

		free(samples[0]);
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
				"- To stop SensorServer type: kill %d\n", process_id, process_id);
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
	server_address.sin_port         = htons(9734);				//remember to use host-to-network-type conversion
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
				if (strcmp("current",buffer) == 0) {
					getCurrentReadings();

				} else if (strcmp("total_samples",buffer) == 0) {
					getTotalStoredSamples();

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
					getMPastSamples(M);

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

int main(void) {
	startDaemon();
	run();
	close(client_sock_fd);
	return EXIT_SUCCESS;
}
