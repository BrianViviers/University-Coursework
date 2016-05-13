/*
 ============================================================================
 Name        : EEPROM.c

 Author      : DreamTeam (Brian Viviers, Philip Edwards, Dominic Youel, Richard Imms)
 Version     :
 Copyright   : Your copyright notice
 Description : EEPROM can be run from the terminal or opened by another process
  	  	  	   using popen(). This script will write readings into the EEPROM
  	  	  	   on the BeagleBoard as well as read and erase readings. The maximum
  	  	  	   number of possible saved readings is 42. 21 for temperature and 21
  	  	  	   for pressure.

  	  	  	   There are a number of different arguments:

  	  	  	   	   - read_m_samples M   -  Where M is the number to request
  	  	  	   	   - total_samples
  	  	  	   	   - write T P  - T => Temperature, P => Pressure e.g: write 24.3 1023.7
  	  	  	   	   - erase_all
 ============================================================================
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <linux/i2c-dev.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <time.h>
#define MCP24AA02_ADDR     (0x50) // 24AA02 2K EEPROM using i2c Address NB LINUX (Beagle Board) using 7 bit and then left shifted to OR with read/write bit
                                  // for the 22AA025 variant this can be 0xA0,0xA2,0xA4,0xA6,0xA8,0xAA,0xAC,0xAE
                                  // since LSB is used to to control a WRITE(0) or a READ(1) Operation
#define WRITE              (0x00) // 24AA02 2K EEPROM using i2c Write bit
#define READ               (0x01) // 24AA02 2K EEPROM using i2c Read bit
#define MCP24AA02_MID      (0xFA) // Manufacturer ID Address (Read Only 0x29==Microchip)
#define MCP24AA02_DID      (0xFB) // Device ID Adress (Read Only 0x41==4 is i2c family and 1 is 2K device)
                                  // Information from Microchip Datasheet DS20005202A Part #24AA02UID/24AA025UID
/* This is a very simple program to demonstrate the ease of use of the i2c bus utilising an EEPROM.
   Do not forget that Pull Up resistors are required on the SDA and SCL lines
   (Typically 2K ohms for 400KHz bus speeds)
   These are required since devices connected with the i2c will have 'open drain' (or open collector)
   circuitry to allow wire 'ORing' of their respective connections
   */

char readEENoPrint(int addy);

int file;

void readID(int addy)
{
    char ucdata_write[1];
    char ucdata_read[1];
    ucdata_write[0] = addy; //MCP24AA02_DID, Address here for Device ID
    if (ioctl(file,I2C_SLAVE,MCP24AA02_ADDR)==0)
	{
		ucdata_write[0] = addy;
		while(write(file, ucdata_write, 1)!=1){};
		read(file,ucdata_read,1);
		if (ucdata_read[0]==0x29)
		{
			printf("Code=%#x (Microchip Technology ltd.(c))\n\r",ucdata_read[0]);return;
		}
		if (ucdata_read[0]==0x41)
		{
			printf("Code=%#x (2K EEPROM Using i2c)\n\r",ucdata_read[0]);
		}
		else
		{
			printf("0x%x 0x%X %c\n\r",addy,ucdata_read[0],(ucdata_read[0]));
		}
	}
}

char readEE(int addy)
{
    char ucdata_write[1];
    char ucdata_read[1];

    //ucdata_write[0] = addy; //Address here to read
	if (ioctl(file,I2C_SLAVE,MCP24AA02_ADDR)==0)
	{
    	ucdata_write[0] = addy;
    	while(write(file, ucdata_write, 1) !=1 ) {}; //Block until ready
		read(file,ucdata_read,1);
		printf("%d",ucdata_read[0]);
	}
	return ucdata_read[0];
 }

void writeEE(int addy,int data)
{
    char ucdata_write[2];
    ucdata_write[0] = addy;
    ucdata_write[1] = data;
    char currentData;
	if (ioctl(file,I2C_SLAVE,MCP24AA02_ADDR)==0)
	{
		while(write(file, ucdata_write, 1)!=1) {} //Block until ready i.e. wait for i2c ACK
		currentData = readEENoPrint(addy);
		if (!(currentData == data))
			write(file, ucdata_write, 2);			  //Now WRITE DATA to ADDRESS!
		//printf("%04d %c\t",addy,data);
	}
}

int eraseEE(void)
{
	char data;
	int i;
    for (i=0;i<0xFA;i++) //0xFA to 0xFF are read only with Manufacture/Hardware ID and a Unique Serial Number
    {
    	data = readEENoPrint(i);
    	// Only erase if the data is not already set erased.
    	if (!(data == 0xFE))
    		writeEE(i,0xFE);
    }
    return 0;
}

// DreamTeam code --------------------------------------------------
// -----------------------------------------------------------------

char readEENoPrint(int addy)
{
    char ucdata_write[1];
    char ucdata_read[1];

    //ucdata_write[0] = addy; //Address here to read
	if (ioctl(file,I2C_SLAVE,MCP24AA02_ADDR)==0)
	{
    	ucdata_write[0] = addy;
    	while(write(file, ucdata_write, 1) !=1 ) {}; //Block until ready
		read(file,ucdata_read,1);
		//printf("%#X %c",ucdata_read[0],ucdata_read[0]);
	}
	return ucdata_read[0];
 }



// A union used to set individual bytes of a
// 2 byte integer. Useful as the EEPROM
// can only save 1 byte numbers.
typedef union {
	 struct {
		 int16_t secondByte :8;
		 int16_t firstByte :8;
	} int_16_bit;
	int16_t int_16;
} int_data;

/*
 * Function:  getDataHead
 * --------------------
 * Finds the location of the data head. The position of the
 * head info section maps onto the position of the head in
 * the data section.
 *
 * head: The address of the head in the head info section as int
 *
 * Returns a char containing the address
 */
int getDataHead(int head) {
	int dataHead;

	if (head == 84 || head == 105)
		dataHead = 0;
	else if (head == 85 || head == 106)
		dataHead = 4;
	else if (head == 86 || head == 107)
		dataHead = 8;
	else if (head == 87 || head == 108)
		dataHead = 12;
	else if (head == 88 || head == 109)
		dataHead = 16;
	else if (head == 89 || head == 110)
		dataHead = 20;
	else if (head == 90 || head == 111)
		dataHead = 24;
	else if (head == 91 || head == 112)
		dataHead = 28;
	else if (head == 92 || head == 113)
		dataHead = 32;
	else if (head == 93 || head == 114)
		dataHead = 36;
	else if (head == 94 || head == 115)
		dataHead = 40;
	else if (head == 95 || head == 116)
		dataHead = 44;
	else if (head == 96 || head == 117)
		dataHead = 48;
	else if (head == 97 || head == 118)
		dataHead = 52;
	else if (head == 98 || head == 119)
		dataHead = 56;
	else if (head == 99 || head == 120)
		dataHead = 60;
	else if (head == 100 || head == 121)
		dataHead = 64;
	else if (head == 101 || head == 122)
		dataHead = 68;
	else if (head == 102 || head == 123)
		dataHead = 72;
	else if (head == 103 || head == 124)
		dataHead = 76;
	else if (head == 104 || head == 125)
		dataHead = 80;

	return dataHead;
}

/*
 * Function:  findHead
 * --------------------
 * Finds the location of the head. This head stores the
 * total number of samples saved and the position of it maps
 * to the position of the head of the data section.
 *
 * Returns a char containing the address
 */
char findHead(void) {
	char data;
	int i;

	// The head info section starts at address 84 (0x54)
	// and ends at 125 (0x7D)
	for (i=0x54;i<=0x7D;i++) {
		data = readEENoPrint(i);
		// Read each byte until a none blank is found
		// then this is the head.
		if (data != 0xFE) {
			break;
		}
	}
	// If loop finished without break then head is at start.
	if (data == 0xFE)
		i = 0x54;
	return i;
}

/*
 * Function:  getTotalSamples
 * --------------------
 * Reads the address associated with the total samples.
 * This address moves after every new reading added to
 * ensure wear-levelling.
 */
int getTotalSamples() {
	int total;
	char headAdd;

	headAdd = findHead();

	// If head is at start of EEPROM
	if (headAdd == 0x54) {
		// Check to see if there is a total value stored
		if (readEENoPrint(headAdd) == 0xFE) {
			// If no value then 0
			total = 0;
		} else {
			// If value is stored then use that.
			total = readEENoPrint(headAdd);
		}
	} else {
		// If head is not at start the a total value must exist
		total = readEENoPrint(headAdd);
	}

	return total;
}

/*
 * Function:  writeToHead
 * --------------------
 *
 * Function used to write a set of readings (temperature and pressure)
 * into the EEPROM at the next available slots. The following way of
 * saving into the EEPROM allows for 21 values to be saved.
 * Readings can be saved in the range from 3276.7 t0 -3276.8 and this
 * is sufficient even for the pressure reading. For normal use the pressure
 * should only vary about 30 mbar either side of 1013 mbar (Sea level).
 *
 *  data[]: The temperature and pressure readings as 16 bit Integers
 *          e.g. Temp: 285 ==> 28.5'C * 10
 *
 * EEPROM memory layout:
 * 0 	1	2	3	4	5	6	7	|| These 84 addresses hold the actual
 * 8 	9	10	11	12	13	14	15	|| readings. They are saved in the following
 * 16	17	18	19	20	21	22	23	|| way:  If we have readings of 28.5'C & 1000.5 mbar then
 * 24	25	26	27	28	29	30	31	||       28.5 * 10 = 285 -- convert into binary = 00000001 00011101
 * 32	33	34	35	36	37	38	39	||						 						  dec: 1    dec: 29
 * 40	41	42	43	44	45	46	47	||       1000.5 * 10 = 10005 - convert to binary = 00100111 00010101
 * 48	49	50	51	52	53	54	55	|| 						   						   dec: 39    dec: 21
 * 56	57	58	59	60	61	62	63	|| First row would then be:
 * 64	65	66	67	68	69	70	71	||    1 29 39 21 - - - -
 * 72	73	74	75	76	77	78	79	|| So 8 bytes hold 4 readings
 * 80	81	82	83
 *
 *				    84	85	86	87	|| These next 42 addresses are used to save the total readings
 * 88	89	90	91	92	93	94	95	|| currently saved. The position of the total also maps onto the
 * 96	97	98	99	100	101	102	103	|| head of the data section. See getDataHead(). The head is the next available space to write to
 * 104	105	106	107	108	109	110	111	|| Example: 84 --> 0
 * 112	113	114	115	116	117	118	119	||	    85 --> 4
 * 120	121	122	123	124	125			||  	    86 --> 8
 * 								    || It takes two complete writes of the data section to write every byte in
 *									|| the head info section. This ensures wear-levelling because for every write to a byte in the
 *									|| data section, the bytes in the head info section get written to twice
 *									|| This is because a total is written to a byte and when a new total is entered
 *									|| then the previous total byte gets cleared and so on.
 *									|| Example: Same row of bytes being overwritten.
 *									|| 		0 0 0 0 0 0 0 0  <-- 0 saved readings (a reading being both temp and pressure)
 *									|| 		0 1 0 0 0 0 0 0  <-- 1 saved reading
 *									|| 		0 0 2 0 0 0 0 0  <-- 2 saved readings
 *									|| 		0 0 0 3 0 0 0 0  <-- 3 saved readings
 *									|| 		0 0 0 0 4 0 0 0  <-- 4 saved readings
 *
 *					        126	127	|| These last 2 bytes are unused unfortunately
 *									|| as they do not fit into this method of saving
 */
void writeToHead(int_data data[]) {
	char headAdd;
	int totalSamples;
	int dataHead;

	headAdd = findHead();
	totalSamples = getTotalSamples();
	dataHead = getDataHead((int)headAdd);

	// Write temperature split by bytes
	writeEE(dataHead,data[0].int_16_bit.firstByte);
	writeEE(dataHead+1,data[0].int_16_bit.secondByte);

	// Write pressure split by bytes
	writeEE(dataHead+2,data[1].int_16_bit.firstByte);
	writeEE(dataHead+3,data[1].int_16_bit.secondByte);

	// Set old head to blank
	writeEE(headAdd, 0xFE);

	// Set the new head (head+1) to the new totalSamples
	// Wrap head to start if needed.
	if (headAdd == 0x7D){
		headAdd = 0x54;
		writeEE(headAdd, totalSamples);
	} else {
		if (totalSamples < 21)
			writeEE(++headAdd, totalSamples+1);
		else
			writeEE(++headAdd, totalSamples);
	}
}

/*
 * Function:  readMSamples
 * --------------------
 * Reads the temperature and pressure readings starting at the
 * data head and works backwards to sort into most recent order.
 *
 * M: 				number of how many readings to get
 * readings[m][2]:	Float array to fill with the saved readings
 */
void readMSamples(int m, float readings[m][2]) {
	char headAdd;
	int dataHead;
	int_data myInt[2];

	headAdd = findHead();
	dataHead = getDataHead((int)headAdd);

	int i;
	for (i = 0; i < m; i++) {

		// If data head is start of EEPROM the wrap around
		// to last section of bytes.
		// Minus 4 as 4 bytes hold 1 set temperature and pressure readings
		if (dataHead - 4 < 0)
			dataHead = 0x50; // Address 80
		else
			dataHead -= 4;

		// Read in temperature bytes and save into 16 bit int
		myInt[0].int_16_bit.firstByte = readEENoPrint(dataHead);
		myInt[0].int_16_bit.secondByte = readEENoPrint(dataHead+1);

		// Read in pressure bytes and save into 16 bit int
		myInt[1].int_16_bit.firstByte = readEENoPrint(dataHead+2);
		myInt[1].int_16_bit.secondByte = readEENoPrint(dataHead+3);

		// Convert integer to float e.g temp : 285 / 10 = 28.5'C
		readings[i][0] = (float)myInt[0].int_16 / 10.0f;
		readings[i][1] = (float)myInt[1].int_16  / 10.0f;
	}
}

int main(int argc, char *argv[]) {
	char *filename = "/dev/i2c-1"; 	// assign pointer to variable 'filename'
	file = open(filename, O_RDWR); 	// Try to open and return a 'file' description

	//Check if exists
	if (file < 0) {
		printf("Failed_to_Open_i2c_Bus!_e.g._Check_Permissions_or_connections\n\r ");
		return EXIT_FAILURE;
	}

	if (ioctl(file,I2C_SLAVE,MCP24AA02_ADDR)==0) {

		// If 4 arguments the it must be a write into EEPROM
		if (argc == 4) {
			if (strcmp (argv[1], "write") == 0) {
				int_data data[2];
				float temp = atof(argv[2]);
				float pressure = atof(argv[3]);

				// Multiply readings by 10 to
				// remove decimal places
				data[0].int_16 = temp * 10;
				data[1].int_16 = pressure * 10;
				writeToHead(data);
			} else {
				printf("No_matching_arguments.");
			}

		// If 3 arguments then it must be a request for M samples
		} else if (argc == 3){

			if (strcmp (argv[1], "read_m_samples") == 0) {
				int m;
				m = atoi(argv[2]);
				if (m > 1) {
					int totalSamples;
					totalSamples = getTotalSamples();

					// If trying to read more than total samples
					// then limit to total samples.
					int M;
					if (m > totalSamples)
						M = totalSamples;
					else
						M = m;

					if (totalSamples > 0) {
						float readings[M][2];

						// Read M samples and fill the array 'readings'
						readMSamples(M, readings);
						int i;
						for (i = 0; i < M; i++) {
							printf("%.1f ",readings[i][0]);
							printf("%.1f ",readings[i][1]);
						}
					} else {
						printf("No_Samples");
					}
				}
			} else {
				printf("No_matching_arguments.");
			}

		// If two arguments then it could be a request to erase all
		// or get a total of saved readings.
		} else if (argc == 2){
			if (strcmp (argv[1], "erase_all") == 0) {
				int success;
				success = eraseEE();
				if (success == 0) {
					printf("EEPROM_erased");
				} else {
					printf("Failed_to_erase");
				}
			} else if (strcmp (argv[1], "total_samples") == 0) {
				printf("%d", getTotalSamples());
			}
		} else {
			printf("Not_enough_arguments.\n\r");
			return 1;
		}
	} else {
		printf("\n\rCannot_get_an_ACK_from_the_Device_check_connections!\n\r");
	}

	printf("\n\r");
	return EXIT_SUCCESS;
}
