/*
 ============================================================================
 Name        : CGI.c
 Author      : DreamTeam (Brian Viviers, Philip Edwards, Dominic Youel, Richard Imms)
 Version     :
 Copyright   : Your copyright notice
 Description : This CGI script is used to request temperature and pressure
 	 	 	   readings from the SensorServer as well as other functions
 	 	 	   that are provided by the SensorServer.
 	 	 	   It also can request a current noise level from the SoundServer
 	 	 	   as well as other functions provided by the SoundServer
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>

#include <netinet/in.h>
#include <arpa/inet.h>


//----------------------------------------------
// NEED TO REMOVE ALL ERROR MESSAGES UNLESS WE
// WANT THE WEB PAGE TO SEE THEM.
//----------------------------------------------

int n;
int socket_fd;	//file descriptor
int countReturned;

/*
 * Function:  openConnectionSensors
 * --------------------
 * Opens a socket connection to the SensorServer.
 *
 *  returns: int being the socket file descriptor.
 */
int openConnectionSensors() {

	int len, result;
	struct sockaddr_in address;
	socket_fd = socket(AF_INET, SOCK_STREAM, 0);

	//Name the socket - align with the server
	address.sin_family      = AF_INET;
	address.sin_addr.s_addr = inet_addr("127.0.0.1");
	address.sin_port        = htons(9734);
	len = sizeof(address);

	//Connect client socket to server socket
	result = connect(socket_fd, (struct sockaddr*)&address, len);
	if (result == -1) {
		printf("Error - cannot connect socket\n");
		close(socket_fd);
		return result;
	}
	else
		return socket_fd;
}

/*
 * Function:  openConnectionSound
 * --------------------
 * Opens a socket connection to the SoundServer.
 *
 *  returns: int being the socket file descriptor.
 */
int openConnectionSound() {

	int len, result;
	struct sockaddr_in address;
	socket_fd = socket(AF_INET, SOCK_STREAM, 0);

	//Name the socket - align with the server
	address.sin_family      = AF_INET;
	address.sin_addr.s_addr = inet_addr("127.0.0.1");
	address.sin_port        = htons(9735);
	len = sizeof(address);

	//Connect client socket to server socket
	result = connect(socket_fd, (struct sockaddr*)&address, len);
	if (result == -1) {
		printf("Error - cannot connect socket\n");
		close(socket_fd);
		return result;
	}
	else
		return socket_fd;
}

/*
 * Function:  readAndWriteSocket
 * --------------------
 * Reads from and writes to the currently open socket.
 *
 *  message[]: char pointer array with the message to
 *  		   send to the server required.
 *
 *  getMSamplesTrue: a boolean to say whether this read/write
 *  				 is for getting many samples.
 *
 *  returnValues[]: char pointer array that is to be filled
 *  with the reply from the server.
 */
void readAndWriteSocket(char* message[], int getMSamplesTrue, char* returnValues[]) {
	char buffer[32];
	// Write initial message to say what is required
	write(socket_fd, message[0], strlen(message[0]));

	// If requesting m samples then need to write 'm' value next
	// but first read to make sure the first message was received.
	if (getMSamplesTrue == 1) {
		n = read(socket_fd, buffer, 32);
		if (n < 0)
			perror("ERROR reading from socket");
		else if (strcmp(buffer, "z") == 0) {
			write(socket_fd, message[1], strlen(message[1]));
		}
	}

	int count = 0;
	// Loop until all messages have been read
	while (1) {
		// Reset all elements of buffer to empty
		bzero(buffer, 32);
		// Read a message into buffer
		n = read(socket_fd, buffer, 32);
		if (n < 0)
			perror("ERROR reading from socket");
		else if (n == 0) {
			// If the server on the other end closed.
			// printf("EOF\n");
			break;
		} else {
			returnValues[count++] = strdup(buffer);
		}

		// Write a z to say the client received a
		// message and is ready to receive another.
		write(socket_fd, "z", 1);
	}
	countReturned = count;
}

/*////////////////////////////////////////////
 *
 * Start of SensorServer methods
 *
 *////////////////////////////////////////////

/*
 * Function:  getCurrentReadings
 * --------------------
 * Requests the current temperature and pressure
 * readings from the SensorServer.
 */
void getCurrentReadings() {
	char* returnValues[2];
	char* strMessage[1] = {"current"};
	int result;
	result = openConnectionSensors();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
		int i;
		for (i = 0;i<2;i++) {
			if (i == 0) {
				puts("<td>");
				puts(returnValues[i]);
				puts("'C</td>");

			} else {
				puts("<td>");
				puts(returnValues[i]);
				puts(" mbar</td>");
			}
		}
	}
	close(socket_fd);
}

/*
 * Function:  getTotalSamplesSensors
 * --------------------
 * Requests the total stored temperature and pressure
 * readings from the SensorServer.
 */
int getTotalSamplesSensors(int dontDisplay) {
	char* returnValues[1];
	char* strMessage[1] = {"total_samples"};
	int total = 0;
	int result;
	result = openConnectionSensors();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
		if (dontDisplay)
			sscanf(returnValues[0], "%d", &total);
		else {
			puts("<div class='col-sm-offset-4'>");
			puts("<h4>");
			printf("Total stored temperature & pressure readings: <b>%s</b>\n", returnValues[0]);
			puts("</h4>");
			puts("</div>");
		}
	}
	close(socket_fd);
	return total;
}

/*
 * Function:  getMPastSamples
 * --------------------
 * Requests a certain number of temperature and pressure
 * readings from the SensorServer.
 *
 * M: The number provided by the user for a request.
 */
void getMPastSamples(int M) {
	char* returnValues[M*2];
	char mVal[3];
	sprintf(mVal,"%d", M);
	char* strMessage[2] = {"read_m_samples", mVal};
	int result;
	result = openConnectionSensors();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 1, returnValues);

		puts("<div class='row col-sm-6 col-sm-offset-3'>");
		puts("<table class='table table-striped''>");
		puts("<thead>");
		puts("<tr>");
		puts("<th>No.</th>");
		puts("<th>Temperature</th>");
		puts("<th>Pressure</th>");
		puts("</tr>");
		puts("</thead>");
		puts("<tbody>");
		int i;
		int count = 1;
		for (i=0; i<countReturned; i+= 2) {
			puts("<tr>");
			puts("<td width='10%'>");
			printf("%d", count++);
			puts("</td>");
			puts("<td width='45%'>");
			puts(returnValues[i]);
		    puts("</td>");
		    puts("<td width='45%'>");
			puts(returnValues[i+1]);
			puts("</td>");
			puts("</tr>");
		}
		puts("</tbody>");
		puts("</table>");
		puts("</div>");
	}
	close(socket_fd);
}

/*
 * Function:  sampleSensors
 * --------------------
 * Requests that the SensorServer either start
 * or stop sampling new readings.
 *
 * action[]: String holding the required
 * 			 action either start or stop.
 */
void sampleSensors(char action[]) {
	char* returnValues[1];
	char* strMessage[1] = {action};
	int result;
	result = openConnectionSensors();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
	}
	close(socket_fd);
}

/*
 * Function:  checkSampcheckSamplingSensorslingSound
 * --------------------
 * Checks with the SensorServer to see if sampling
 * is currently active or not.
 *
 * returns: 1 if active 0 if inactive
 */
int checkSamplingSensors() {
	char* returnValues[1];
	char* strMessage[1] = {"is_sampling"};
	int result;
	int isActive = 0;
	result = openConnectionSensors();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
		if (strcmp(returnValues[0], "Active") == 0) {
			isActive = 1;
		}
	}
	close(socket_fd);
	return isActive;
}

/*
 * Function:  eraseAllSamples
 * --------------------
 * Requests that the SensorServer erase all temperature
 * and pressure reading from the EEPROM.
 */
void eraseAllSamples() {
	char* returnValues[1];
	char* strMessage[1] = {"erase_all_samples"};
	int result;
	result = openConnectionSensors();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
		puts("<div class='col-sm-offset-4'>");
		puts("<h4>");
		printf("%s\n", returnValues[0]);
		puts("</h4>");
		puts("</div>");
	}
	close(socket_fd);
}

/*////////////////////////////////////////////
 *
 * Start of SoundServer methods
 *
 *////////////////////////////////////////////

/*
 * Function:  getCurrentAudio
 * --------------------
 * Requests the noise level
 * readings from the SoundServer.
 */
void getCurrentAudio() {
	char* returnValues[1];
	char* strMessage[1] = {"current_audio"};
	int result;
	result = openConnectionSound();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
		puts("<td>");
		puts(returnValues[0]);
		puts(" dB</td>");
	}
	close(socket_fd);
}

/*
 * Function:  getTotalAudio
 * --------------------
 * Requests the total stored noise level
 * readings from the SoundServer.
 */
int getTotalAudio(int dontDisplay) {
	char* returnValues[1];
	char* strMessage[1] = {"total_audio_samples"};
	int result;
	int total = 0;
	result = openConnectionSound();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
		if (dontDisplay)
			sscanf(returnValues[0], "%d", &total);
		else {
			puts("<div class='col-sm-offset-4'>");
			puts("<h4>");
			printf("Total stored noise level readings: <b>%s</b>\n", returnValues[0]);
			puts("</h4>");
			puts("</div>");
		}
	}
	close(socket_fd);
	return total;
}

/*
 * Function:  eraseAllAudio
 * --------------------
 * Requests that the SoundServer erase all noise
 * level reading from the storage.
 */
void eraseAllAudio() {
	char* returnValues[1];
	char* strMessage[1] = {"erase_all_samples"};
	int result;
	result = openConnectionSound();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
		puts("<div class='col-sm-offset-4'>");
		puts("<h4>");
		printf("%s\n", returnValues[0]);
		puts("</h4>");
		puts("</div>");
	}
	close(socket_fd);
}

/*
 * Function:  getMPastSamplesAudio
 * --------------------
 * Requests a certain number of noise level
 * readings from the SensorServer.
 *
 * M: The number provided by the user for a request.
 */
void getMPastSamplesAudio(int M) {
	char* returnValues[M];
	char mVal[3];
	sprintf(mVal,"%d", M);
	char* strMessage[2] = {"read_m_samples", mVal};
	int result;
	result = openConnectionSound();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 1, returnValues);

		puts("<div class='row col-sm-6 col-sm-offset-3'>");
		puts("<table class='table table-striped''>");
		puts("<thead>");
		puts("<tr>");
		puts("<th>No.</th>");
		puts("<th>Noise Levels (dB)</th>");
		puts("</tr>");
		puts("</thead>");
		puts("<tbody>");
		int i;
		for (i=0; i<countReturned; i++) {
			puts("<tr>");
			puts("<td width='10%'>");
			printf("%d", i+1);
			puts("</td>");
			puts("<td width='90%'>");
			puts(returnValues[i]);
		    puts("</td>");
			puts("</tr>");
		}
		puts("</tbody>");
		puts("</table>");
		puts("</div>");
	}
	close(socket_fd);
}

/*
 * Function:  sampleSound
 * --------------------
 * Requests that the SoundServer either start
 * or stop sampling new readings.
 *
 * action[]: String holding the required
 * 			 action either start or stop.
 */
void sampleSound(char action[]) {
	char* returnValues[1];
	char* strMessage[1] = {action};
	int result;
	result = openConnectionSound();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
	}
	close(socket_fd);
}

/*
 * Function:  checkSamplingSound
 * --------------------
 * Checks with the SoundServer to see if sampling
 * is currently active or not.
 *
 * returns: 1 if active 0 if inactive
 */
int checkSamplingSound() {
	char* returnValues[1];
	char* strMessage[1] = {"is_sampling"};
	int result;
	int isActive = 0;
	result = openConnectionSound();
	if (!(result == -1)) {
		readAndWriteSocket(strMessage, 0, returnValues);
		if (strcmp(returnValues[0], "Active") == 0) {
			isActive = 1;
		}
	}
	close(socket_fd);
	return isActive;
}

/*
 * Function:  getParameters
 * --------------------
 * The following method has been adapted from the code found
 * at this URL: http://www.jmarshall.com/easy/cgi/hello.c.txt
 *
 * Read the CGI input and place all name/val pairs into list.
 *
 * returns: list containing name1, value1, name2, value2, ... , NULL
 */
char **getParameters() {
    register int i ;
    char *request_method ;
    int content_length;
    char *cgiinput ;
    char **cgivars ;
    char **pairlist ;
    int paircount ;
    char *nvpair ;
    char *eqpos ;

    /** Depending on the request method, read all CGI input into cgiinput. **/
    request_method= getenv("REQUEST_METHOD") ;

    if (!strcmp(request_method, "GET") || !strcmp(request_method, "HEAD") ) {
        /* Some servers apparently don't provide QUERY_STRING if it's empty, */
        /*   so avoid strdup()'ing a NULL pointer here.                      */
        char *qs ;
        qs= getenv("QUERY_STRING") ;
        cgiinput= strdup(qs  ? qs  : "") ;
    }
    else if (!strcmp(request_method, "POST")) {
        /* strcasecmp() is not supported in Windows-- use strcmpi() instead */
        if ( strcasecmp(getenv("CONTENT_TYPE"), "application/x-www-form-urlencoded")) {
	    printf("Content-Type: text/plain\n\n") ;
            printf("getcgivars(): Unsupported Content-Type.\n") ;
        }
        if ( !(content_length = atoi(getenv("CONTENT_LENGTH"))) ) {
	    printf("Content-Type: text/plain\n\n") ;
            printf("getcgivars(): No Content-Length was sent with the POST request.\n") ;
        }
        if ( !(cgiinput= (char *) malloc(content_length+1)) ) {
	    printf("Content-Type: text/plain\n\n") ;
            printf("getcgivars(): Couldn't malloc for cgiinput.\n") ;
        }
        if (!fread(cgiinput, content_length, 1, stdin)) {
	    printf("Content-Type: text/plain\n\n") ;
            printf("getcgivars(): Couldn't read CGI input from STDIN.\n") ;
        }
        cgiinput[content_length]='\0' ;
    }
    else {
	printf("Content-Type: text/plain\n\n") ;
        printf("getcgivars(): Unsupported REQUEST_METHOD.\n") ;
    }

    //Split parameters on '&' character
    pairlist= (char **) malloc(256*sizeof(char **)) ;
    paircount= 0 ;
    nvpair= strtok(cgiinput, "&") ;
    while (nvpair) {
        pairlist[paircount++]= strdup(nvpair) ;
        if (!(paircount%256))
            pairlist= (char **) realloc(pairlist,(paircount+256)*sizeof(char **)) ;
        nvpair= strtok(NULL, "&;") ;
    }
    // Terminate end of string
    pairlist[paircount]= 0;

    // Extract param name and values.
    cgivars= (char **) malloc((paircount*2+1)*sizeof(char **));
    for (i= 0; i<paircount; i++) {
        if (eqpos=strchr(pairlist[i], '=')) {
            *eqpos= '\0';
            cgivars[i*2+1]= strdup(eqpos+1);
        } else {
            cgivars[i*2+1]= strdup("");
        }
        cgivars[i*2]= strdup(pairlist[i]);
    }
    // Terminate list with null
    cgivars[paircount*2]= 0;

    /** Free anything that needs to be freed. **/
    free(cgiinput);
    for (i=0; pairlist[i]; i++) free(pairlist[i]);
    free(pairlist);

    /** Return the list of name-value strings. **/
    return cgivars;
}

/*
 * Function:  printFormStartTag
 * --------------------
 * Form tag placed in its own method to allow for easy editing of the URL
 * if it ever needs changing. It also reduces code repetition.
 */
void printFormStartTag() {
	puts("<form method='post' action='http://10.0.0.2/cgi-bin/EnvServer.cgi'>\n");
}

/*
 * Function:  header
 * --------------------
 * Prints the header of the web page in HTML.
 */
void header() {
	puts("Content-Type: text/html;charset=iso-8859-1\n\n");
	puts("<html>\n");
	puts("<head><title>DreamTeam's Environment Monitor</title>");
	puts("<link href='../css/bootstrap.css' rel='stylesheet'>");
	puts("<link href='../css/style.css' rel='stylesheet'>");
	puts("</head>\n");
	puts("<body>\n");

	puts("<div class='navbar navbar-inverse navbar-fixed-top'>");
	puts("<div class='container-fluid'>");
	puts("<div class='navbar-header logo'>");
	puts("DreamTeam's Environment Monitor");
	puts("</div>");
	puts("</div>");
	puts("</div>");
}

/*
 * Function:  currentReadings
 * --------------------
 * Requests the current readings and then prints it
 * out onto the web page in HTML.
 */
void currentReadings() {
	puts("<div class='container-fluid'>");
	puts("<div class='main'>");
	puts("<div class='row'>");
	puts("<div class='col-xs-offset-1 col-xs-3'>");
	puts("<h2 class='page-header'>Current Readings</h2>");


	puts("<table class='table table-striped''>");
	puts("<thead>");
	puts("<tr>");
	puts("<th>Temperature</th>");
	puts("<th>Pressure</th>");
	puts("<th>Noise Level</th>");
	puts("</tr>");
	puts("</thead>");
	puts("<tbody>");
	puts("<tr>");

	getCurrentReadings();
	getCurrentAudio();

	puts("</tr>");
	puts("</tbody>");
	puts("</table>");

	printFormStartTag();

	puts("<div><input type='submit' name='refresh' value='Refresh Readings'></div>\n");
	puts("</form>\n");
	puts("</div>");
	puts("<hr>");
}

/*
 * Function:  controlSections
 * --------------------
 * Prints out the controls sections for the Sensors and Sound servers.
 * Select boxes are dynamically allocated options depending on
 * how many total readings there are for each server.
 */
void controlSections() {
	int isSamplingActive;
	puts("<div class='col-xs-3'>");
	puts("<h4>Temperature and Pressure Controls</h4><br>");

    printFormStartTag();
	puts("<div><label>Number of samples: <select name='numMSens'>\n");
	int total = getTotalSamplesSensors(1);

	int i;
	for (i=2; i <= total; i++) {
		printf("<option  value='");
		printf("%d",i);
		printf("'>");
		printf("%d",i);
		printf("</option>\n");
	}
	puts("</select></label></div>\n");
	puts("<div><input type='submit' value='Get Temperature & Pressure!' ");
	if (total < 2)
		puts("disabled");
	puts("></div>\n");
	puts("</form>\n");

	printFormStartTag();
	puts("<div><input type='submit' name='totalSensors' value='Get total number samples'></div>\n");
	puts("</form>\n");

	isSamplingActive = checkSamplingSensors();
	if (isSamplingActive == 0) {
		puts("<p>Sampling is currently <span class='red'>inactive</span></p>");
		printFormStartTag();
		puts("<div><input type='submit' name='startSampSens' value='Start sampling'></div>\n");
		puts("</form>\n");
	} else {
		puts("<p>Sampling is currently <span class='green'>active</span></p>");
		printFormStartTag();
		puts("<div><input type='submit' name='stopSampSens' value='Stop sampling'></div>\n");
		puts("</form>\n");
	}

	printFormStartTag();
	puts("<div><input type='submit' name='eraseAllReadings' value='Erase all temperature & pressure readings'></div>\n");
	puts("</form>\n");

	puts("</div>");
	puts("<div class='col-xs-3'>");
	puts("<h4>Sound Controls</h4><br>");

	printFormStartTag();
	puts("<div><label>Number of samples: <select name='numMAudio'>\n");
	total = getTotalAudio(1);

	for (i=2; i <= total; i++) {

		printf("<option  value='");
		printf("%d",i);
		printf("'>");
		printf("%d",i);
		printf("</option>\n");
	}
	puts("</select></label></div>\n");
	puts("<div><input type='submit' value='Get Audio Samples!' ");
	if (total < 2)
		puts("disabled");
	puts("></div>\n");
	puts("</form>\n");

	printFormStartTag();
	puts("<div><input type='submit' name='totalAudio' value='Get total number audio samples'></div>\n");
	puts("</form>\n");

	isSamplingActive = checkSamplingSound();
	if (isSamplingActive == 0) {
		puts("<p>Sampling is currently <span class='red'>inactive</span></p>");
		printFormStartTag();
		puts("<div><input type='submit' name='startSampSound' value='Start sampling'></div>\n");
		puts("</form>\n");
	} else {
		puts("<p>Sampling is currently <span class='green'>active</span></p>");
		printFormStartTag();
		puts("<div><input type='submit' name='stopSampSound' value='Stop sampling'></div>\n");
		puts("</form>\n");
	}

	printFormStartTag();
	puts("<div><input type='submit' name='eraseAllAudio' value='Erase all previous noise levels'></div>\n");
	puts("</form>\n");

	puts("</div>");
	puts("</div>");
	puts("</div>");
	puts("</div>");
	puts("<hr>");
}

int main() {
    char **parameters;
    int i;

    parameters= getParameters();

    // First check to see if they requested to start/stop sampling
    // so that the correct buttons can be displayed on the page.
    for (i=0; parameters[i]; i+= 2){
		if (strcmp(parameters[i], "startSampSound") == 0)
			sampleSound("start_sampling");

		else if (strcmp(parameters[i], "stopSampSound") == 0)
			sampleSound("stop_sampling");

		else if (strcmp(parameters[i], "startSampSens") == 0)
			sampleSensors("start_sampling");

		else if (strcmp(parameters[i], "stopSampSens") == 0)
			sampleSensors("stop_sampling");
    }

    header();
    currentReadings();
    controlSections();

    for (i=0; parameters[i]; i+= 2){
    	if (strcmp(parameters[i],"numMSens") == 0)
    		getMPastSamples(atoi(parameters[i+1]));

    	else if (strcmp(parameters[i], "totalSensors") == 0)
    		getTotalSamplesSensors(0);

		else if (strcmp(parameters[i], "eraseAllReadings") == 0)
			eraseAllSamples();

		else if (strcmp(parameters[i], "totalAudio") == 0)
			getTotalAudio(0);

		else if (strcmp(parameters[i], "eraseAllAudio") == 0)
			eraseAllAudio();

		else if (strcmp(parameters[i], "numMAudio") == 0)
			getMPastSamplesAudio(atoi(parameters[i+1]));
    }


	puts("</body>\n");
    puts("</html>\n");

    // Free variables
    for (i=0; parameters[i]; i++) free(parameters[i]);
    free(parameters);

    exit(0);
}
