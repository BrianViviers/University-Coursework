/*
 ============================================================================
 Name        : NoiseLevelRecord.c
 Author      : DreamTeam (Brian Viviers, Philip Edwards, Dominic Youel, Richard Imms)
 Version     :
 Copyright   : Your copyright notice
 Description : NoiseLevelRecord application can be used from the command line
 	 	 	   to calculate the noise level, in decibels, of the line input of
 	 	 	   the device it is run on. It will return through stdout the
 	 	 	   noise level as a float.
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <math.h>

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

/*
 * Function:  recordSample
 * --------------------
 * Method which records audio for a short duration (2 seconds) and
 * then calculates a noise level in decibels for that duration.
 */
float recordSample() {
	while (inputStream != NULL) {

	}
	float db;
	//Record samples using arecord

	//Build the command string
	char strCommand[64];
	sprintf(strCommand, "arecord -q -c 1 -t raw -f U8 -r %u -D plughw:0,0", SAMPLING_RATE);
	//printf("Issuing command %s\n", strCommand);

	//Open arecord as a child process for read (stdout will be directed to the pipe)
	inputStream = popen(strCommand,"r");
	if (inputStream == NULL) {
		perror("Cannot open child process for record");
	} else {
		//We get back a C Stream - I prefer to use a UNIX file descriptor
		fidIn = fileno(inputStream);

		//Read a finite block of data into a buffer
		char* pBuffer = buf;
		int iSamplesRead;
		iBytesRead = 0;

		while (iBytesRead < BUFFER_SIZE_IN_BYTES) {

			//Grab a block samples
			iSamplesRead = read(fidIn, pBuffer, BUFFER_SIZE_IN_BYTES);			//4000 is max for one hit

			//It is likely that the actual number of samples read is not as many as we wanted

			if (iSamplesRead > 0) {
				iBytesRead += iSamplesRead;
				pBuffer += iSamplesRead;
			} else {
				break;
			}
		}
		// Close the child process
		pclose(inputStream);
		inputStream = NULL;

		long sum = 0;
		int i = 0;
		unsigned char s;
		float fSamp;
		for (i=0;i<BUFFER_SIZE_IN_BYTES;i++) {
			s = buf[i];
			fSamp = (float)s -128.0;

			// Calculate instantaneous power value
			fSamp *= fSamp;

			// Sum up all instantaneous power values
			sum += fSamp;
		}
		// Calculate average power value
		float average = (float)sum / (float)BUFFER_SIZE_IN_BYTES;

		// Convert average power value into decibels.
		db = 20 * log10(average);

	}
	return db;
}

int main(void) {
	float noiseLevel = recordSample();
	printf("%f", noiseLevel);
	return EXIT_SUCCESS;
}
