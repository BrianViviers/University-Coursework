/*
 ============================================================================
 Name        : CircularBuffer.c

 Author      : DreamTeam (Brian Viviers, Philip Edwards, Dominic Youel, Richard Imms)
 Version     :
 Copyright   : Your copyright notice
 Description : CircularBuffer application can be used by any other application
 	 	 	   that wishes to save values to a text file. The complete path and filename
 	 	 	   of the text file must be given as the second command line argument. If the
 	 	 	   text file does not exist then it will be created.
 	 	 	   The values can be either floats or integers and can save a
 	 	 	   maximum of 30 values.
 	 	 	   Strings up to a length of 19 characters can also be saved.
 ============================================================================
 */

#define _XOPEN_SOURCE 500
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int counter;

/*
 * Function:  openFile
 * --------------------
 * Opens the noise levels text file where all
 * noise levels are stored.
 * If the file does not exist it creates it first
 * and initialises it with 0 for total and 0 for head.
 *
 *  filename[]: The text file that should be opened.
 *
 *  howToOpen[]: The way the calling method wants the file
 *  			 to be opened.
 *
 *  returns: A pointer to the opened file.
 */
FILE* openFile(char filename[], char howToOpen[]) {
	FILE *file;
	int file_exists;
	//const char * filename = "/root/noiseLevels.txt";

	/*first check if the file exists...*/
	file = fopen(filename,"r");
	if (file == NULL)
		file_exists=0;
	else {
		file_exists=1;
		fclose(file);
	}

	/*...then open it in the appropriate way*/
	if (file_exists == 0) {
		file = fopen(filename,"w");

		// Write a new total to file
		fprintf(file, "%d", 0);
		fprintf(file, "\n");

		// Write a new head to file
		fprintf(file, "%d", 0);
		fprintf(file, "\n");

		// Close the file
		fclose(file);
	}

	// Open the file in the way that was
	// requested either 'w' or 'r'
	file = fopen(filename,howToOpen);

	return file;
}

/*
 * Function:  getTotalStoredSamples
 * --------------------
 * Reads the first line of the saved noise level readings
 * file and then returns the total readings.
 *
 *  filename[]: The text file that contains the data.
 *
 *  returns: the approximate value of pi obtained by summing the first n terms
 *           in the above series
 *           returns zero on error (if n is non-positive)
 */
int getTotalStoredSamples(char filename[]) {
	FILE * fp;
	char * line = NULL;
	size_t len = 0;
	ssize_t read;
	int total = 0;

	// Open file in read mode
	fp = openFile(filename, "r");
	if (fp != NULL) {

		if ((read = getline(&line, &len, fp)) != -1) {
			sscanf(line, "%d", &total);
		}

		fclose(fp);
		if (line)
			free(line);
	}
	return total;
}

/*
 * Function:  readInFile
 * --------------------
 * Reads in the entire file containing all saved
 * noise level readings
 *
 *  filename[]: The text file that contains the data.
 *
 * contents[]: char pointer array that will be filled
 *   		   by all the noise level readings in the file.
 *  		   index 0 always is total saved in file
 *   		   index 1 always the location of the head of
 *             the ring buffer.
 */
void readInFile(char filename[], char* contents[]) {
	FILE * fp;
	char * line = NULL;
	size_t len = 0;
	ssize_t read;

	// Open file in read mode
	fp = openFile(filename, "r");
	if (fp != NULL) {

		counter = 0;
		while ((read = getline(&line, &len, fp)) != -1) {
			contents[counter++] = strdup(line);
			if (counter == 32)
				break;
		}

		fclose(fp);
		if (line)
			free(line);
	}
}

/*
 * Function:  saveToFile
 * --------------------
 * Saves a new noise level reading to a file
 *
 *  filename[]: The text file that contains the data.
 *
 * sample[]: char array holding the new noise level reading.
 */
void saveToFile(char filename[], char sample[]) {
	char * new_str ;
	FILE *fp;

	if((new_str = malloc(strlen(sample)+strlen("\n")+1)) != NULL){
	    new_str[0] = '\0';   // ensures the memory is an empty string
	    strcat(new_str,sample);
	    strcat(new_str,"\n");

		// Only ever store 30 readings + 2 for total and head.
		char* fileContents[32];
		readInFile(filename,fileContents);

		int total = atoi(fileContents[0]);
		if(!(total == 30)) {
			total++;
			sprintf(fileContents[0], "%d\n", total);
		}

		int head = atoi(fileContents[1]);
		if (head == 30)
			head = 0;

		head++;

		// Open file in write mode
		fp = openFile(filename, "w");
		if (fp != NULL) {

			// Write the new total to file
			fprintf(fp, "%d", total);
			fprintf(fp, "\n");

			// Write the new head to file
			fprintf(fp, "%d", head);
			fprintf(fp, "\n");

			if (head < total) {
				// Set new head to contain new sample
				fileContents[head+1] = new_str;
				// Write the file contents back to file.
				int i;
				for (i=2; i<counter; i++) {
					fprintf(fp, "%s", fileContents[i]);
				}
			} else {
				// Write the file contents back to file.
				int i;
				for (i=2; i<counter; i++) {
					fprintf(fp, "%s", fileContents[i]);
				}
				// Write the new value to the end of the file.
				fprintf(fp, "%s", new_str);
			}

			fclose(fp);

			free(new_str);
		}
	}
}

/*
 * Function:  eraseAllSamples
 * --------------------
 * Erases all the saved noise level readings.
 *
 *  filename[]: The text file that contains the data.
 */
int eraseAllSamples(char filename[]) {
	FILE *f = openFile(filename, "w");
	if (f != NULL)
	{
		// Write the new total to file
		fprintf(f, "%d", 0);
		fprintf(f, "\n");

		// Write the new head to file
		fprintf(f, "%d", 0);
		fprintf(f, "\n");

		fclose(f);
		return 0;
	}
	return 1;
}

/*
 * Function:  getMPastSamples
 * --------------------
 * Gets stored noise level readings and send them to the caller.
 *
 *  filename[]: The text file that contains the data.
 *
 * 	M: number of how many readings to get
 */
void getMPastSamples(char filename[], int M) {
	char* fileContents[32];
	readInFile(filename, fileContents);

	int total = atoi(fileContents[0]);
	if(total >= M) {
		int head = atoi(fileContents[1]);

		int i = head + 1;
		// Read in the file contents backwards so that they
		// will be displayed in order of most recent first.
		while(M > 0) {
			printf("%s", fileContents[i]);
			M--;
			i--;

			// Wrap buffer around
			if (i == 1)
				i = 31;
		}
	}
}

int main(int argc, char *argv[]) {
	char filename[30];
	// If 4 arguments then it must be a write into CircularBuffer
	// or read M samples
	if (argc == 4) {
		if (strcmp (argv[2], "write") == 0) {
			strcpy(filename, argv[1]);
			char decibels[31];
			strcpy(decibels, argv[3]);
			saveToFile(filename,decibels);
		} else if (strcmp (argv[2], "read_m_samples") == 0) {
			int m;
			m = atoi(argv[3]);
			if (m > 1) {
				int totalSamples;
				totalSamples = getTotalStoredSamples(argv[1]);

				int M;
				if (m > totalSamples)
					M = totalSamples;
				else
					M = m;

				if (totalSamples > 0) {
					getMPastSamples(argv[1], M);
				} else {
					printf("No_Samples");
				}
			}
		} else {
			printf("No_matching_arguments.");
		}

	// If 3 arguments then it must be a request to erase all
	// or get total saved samples.
	} else if (argc == 3){

		if (strcmp (argv[2], "erase_all") == 0) {
			int success;
			success = eraseAllSamples(argv[1]);
			if (success == 0) {
				printf("Noise_levels_erased");
			} else {
				printf("Failed_to_erase");
			}
		} else if (strcmp (argv[2], "total_samples") == 0) {
			printf("%d", getTotalStoredSamples(argv[1]));
		}

	} else {
		printf("Not_enough_arguments.\n\r");
		return EXIT_FAILURE;
	}

	return EXIT_SUCCESS;
}
