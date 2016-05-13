/*
 ============================================================================
 Name        : SENSORS.c

 Author      : DreamTeam (Brian Viviers, Philip Edwards, Dominic Youel, Richard Imms)
 Version     :
 Copyright   : Your copyright notice
 Description : SENSORS will only return two values when run.
 	 	 	   These two values are the current temperature as a float
 	 	 	   and the current pressure as a float.
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

#define BMP180_ADDR        (0x77) // BMP180 Pressure/Temperature Sensor using i2c Address
#define WRITE              (0x00) // BMP180 using i2c Write bit
#define READ               (0x01) // BMP180 using i2c Read bit
#define BMP180_MID         (0xD0) // Manufacturer ID Address (Read Only 0x55==Bosch)
#define BMP180_CTRL		   (0xF4) // BMP180 Control Register
#define BMP180_TMPRD       (0x2E) // BMP180 Start Temperature Read
#define BMP180_PRERD	   (0x34) // BMP180 Start Pressure Read
#define AC1_MSB            (0xAA) // - Start of Calibration registers to get true Temperature and Pressure
#define AC2_MSB	           (0xAC) // NB All two bytes long
#define AC3_MSB            (0xAE) //
#define AC4_MSB            (0xB0) //
#define AC5_MSB            (0xB2) //
#define AC6_MSB            (0xB4) //
#define B1_MSB             (0xB6) //
#define B2_MSB             (0xB8) //
#define MB_MSB             (0xBA) //
#define MC_MSB             (0xBC) //
#define MD_MSB             (0xBE) // - End of Calibration Registers
#define OUT_MSB            (0xF6) // Result of EITHER Temp or Pressure depending on what was asked for MSB
#define OUT_LSB            (0xF7) // Result of EITHER Temp or Pressure depending on what was asked for LSB
#define OUT_XLSB           (0xF8) // Result of EITHER Temp or Pressure depending on what was asked for XLSB!
#define SOFT_RST           (0xE0) // Soft Reset if you wish to restart the sensor

#define RTC_ADDR           (0xD0) // DS1307 RTC using i2c Address
// since LSB is used to to control a WRITE(0) or a READ(1) Operation
#define WRITE              (0x00) // i2c Write bit
#define READ               (0x01) // i2c Read bit
#define RTCSec             (0x00) // Seconds    (BCD) 0 to 59
#define RTCMin             (0x01) // Minutes    (BCD) 0 to 59
#define RTCHrs             (0x02) // Hours      (BCD) 1 to 12 or 0 to 24
#define RTCDoW             (0x03) // Day of Week(BCD) 1 to 7
#define RTCDay             (0x04) // Day        (BCD) 1 to 31
#define RTCMth             (0x05) // Month      (BCD) 1 to 12
#define RTCYer             (0x06) // Year       (BCD) 00 to 99
#define RTCCTL             (0x07) // Control Register
// Bit7   Bit4    Bit1    Bit0 (all other bits READ as Zero
// OUT    SQWE    RS1     RS2
// OUT  = SET/RESET (1/0) the Output Pin
// SQWE = Square Wave Enable 1=EN 0=DIS
// RS1,RS2 = Freq of Square Wave 1, 4096, 8192 and 32768 Hz (0 to 3)

/* This is a program to demonstrate the ease of use of the i2c bus utilising BMP180 pressure/temperature sensor
 and a RTC DS1307. (RTC Real Time Clock)
 Do not forget that Pull Up resistors are required on the SDA and SCL lines
 (Typically 2K ohms for 400KHz bus speeds) NB these are provided on the PCB Module.
 These are required since devices connected with the i2c will have 'open drain' (or open collector)
 circuitry to allow wire 'ORing' of their respective connections. Used with a stm32-Nucleo-F401RE
 Martin Simpson January 2015 */

char ucdata_write[2], ucdata_read[2], SecCount = 0x60; //NB set SecCount to 0x60 to ensure ALWAYS catch within first Minute send Data to serial line
short AC1, AC2, AC3, B1, B2, MB, MC, MD, oss = 0;
unsigned short AC4, AC5, AC6;
long B3, B5, B6, X1, X2, X3, p;
unsigned long B4, B7;
long True_Temp, True_Press;
char CurrentTime[8];
int file, fd;
char *filename = "/dev/i2c-1";

void msdelay(int milliseconds) {
	int i;
	for (i=0;i<milliseconds;i++){
	usleep(1000);//microsecond sleep for 1000 times! to give 1 millisecond 'naps'
	}
}

void readID(int addy) {
	ucdata_write[0] = addy; //_DID, Address here for Device ID
	if (ioctl(file, I2C_SLAVE, BMP180_ADDR) == 0) {
		write(file, ucdata_write, 1);
		read(file, ucdata_read, 1);
		if (ucdata_read[0] == 0x55) {
			//printf("Code=%#x (Bosch Sensortec GmbH(c))\n\r", ucdata_read[0]);
		} else {
			//printf("0x%x 0x%X %c\n\r", addy, ucdata_read[0], (ucdata_read[0]));
		}
	}
}
int read_i2c(int Device, int addy) {
	if (ioctl(file, I2C_SLAVE, Device) == 0) {
		char ucdata_write[1];
		char ucdata_read[1];
		ucdata_write[0] = addy;
		write(file, ucdata_write, 1);
		read(file, ucdata_read, 1);
		return ucdata_read[0];
	} else {
		return 0;
	}
}

signed int readBMP(int addy) {
	if (ioctl(file, I2C_SLAVE, BMP180_ADDR) == 0) {
		short int siReadData;
		ucdata_write[0] = addy;
		write(file, ucdata_write, 1);
		read(file, ucdata_read, 2);
		siReadData = ucdata_read[0] << 8; 			// Put the TWO elements of ucdata_read[0,1], single bytes
		siReadData = siReadData + ucdata_read[1]; 	// And put into siReadData NB Signed
		return siReadData;							// Then return with this two byte word
	} else {
		return 0;									// couldn't find device so return with a ZERO
	}
}
void initialise_BMP(void) {
	//readID(BMP180_MID);
	AC1 = readBMP(AC1_MSB);
	AC2 = readBMP(AC2_MSB);
	AC3 = readBMP(AC3_MSB);
	AC4 = readBMP(AC4_MSB);
	AC5 = readBMP(AC5_MSB);
	AC6 = readBMP(AC6_MSB);
	B1 = readBMP(B1_MSB);
	B2 = readBMP(B2_MSB);
	MB = readBMP(MB_MSB);
	MC = readBMP(MC_MSB);
	MD = readBMP(MD_MSB);

	//You might want to comment these lines out
	/*printf("AC1=%#X\n\r", AC1);
	printf("AC2=%#X\n\r", AC2);
	printf("AC3=%#X\n\r", AC3);
	printf("AC4=%#X\n\r", AC4);
	printf("AC5=%#X\n\r", AC5);
	printf("AC6=%#X\n\r", AC6);
	printf("B1=%#X\n\r", B1);
	printf("B2=%#X\n\r", B2);
	printf("MB=%#X\n\r", MB);
	printf("MC=%#X\n\r", MC);
	printf("MD=%#X\n\r", MD);*/
}

void write_i2c(int Device, int addy, int data) {
	char ucdata_write[2];
	ucdata_write[0] = addy;
	ucdata_write[1] = data;
	write(file, ucdata_write, 2);
}

int trueTemp(int siUT) { // Convert to True Temperature from BMP180 Datasheet
	True_Temp = 0;
	X1 = ((siUT - AC6) * AC5) / (1 << 15);
	X2 = (MC * (1 << 11)) / (X1 + MD);
	B5 = X1 + X2;
	True_Temp = (B5 + 8) / (1 << 4);
	return True_Temp;
}
int truePress(int siUP) { // Convert to True Pressure from BMP180 Datasheet
	True_Press = 0;	oss = 0;p = 0;
	B6 = B5 - 4000;
	X1 = (B2 * (B6 * B6) / (1 << 12)) / (1 << 11);
	X2 = (AC2 * B6) / (1 << 11);
	X3 = X1 + X2;
	B3 = (((AC1 * 4 + X3) << oss) + 2) / 4;
	X1 = AC3 * B6 / (1 << 13);
	X2 = (B1 * (B6 * B6 / (1 << 12))) / (1 << 16);
	X3 = ((X1 + X2) + 2) / (1 << 2);
	B4 = AC4 * (unsigned long) (X3 + 32768) / (1 << 15);
	B7 = ((unsigned long) siUP - B3) * (50000 >> oss);
	if (B7 < 0x80000000) {p = (B7 * 2) / B4;}
	else {p = (B7 / B4) * 2;}
	X1 = (p / (1 << 8)) * (p / (1 << 8));
	X1 = (X1 * 3038) / (1 << 16);
	X2 = (-7357 * p) / (1 << 16);
	p = p + (X1 + X2 + 3791) / (1 << 4);
	return p;	//(where p is the TRUE Calibrated Pressure in Pa)
}
int main() {

	char *filename = "/dev/i2c-1"; 	// assign pointer to variable 'filename'
	file = open(filename, O_RDWR); // Try to open and return a 'file' description

	//Check if exists
	if (file < 0) {
		printf("Failed to Open i2c Bus! e.g. Check Permissions or connections\n\r");
		return EXIT_FAILURE;
	}

	//Connect to sensors using I2C Bus
	ucdata_write[0] = 0;
	ucdata_write[1] = 0;
	int siUTemp = 0;
	long siUPress = 0;

	if (ioctl(file, I2C_SLAVE, BMP180_ADDR) == 0) {
		initialise_BMP();

		ucdata_write[0] = BMP180_CTRL;				//Control Register=0xF4
		ucdata_write[1] = BMP180_TMPRD; 			//Control Code to Start Temperature Read=0x2E
		ioctl(file, I2C_SLAVE, BMP180_ADDR);
		write(file, ucdata_write, 2);             	//Send Code to Device
		msdelay(5); 								//WAIT FOR CONVERSION TO COMPLETE (See Pressure setting for Notes on This Delay)
		ucdata_write[0] = 0xF6;                     //Result Register

		//(NB Can be used for Temperature OR Pressure depending on what was asked for
		write(file, ucdata_write, 1); 						//Write to the i2c device with the register address
		read(file, ucdata_read, 2); 						//Read the data from the i2c device at the register address as above
		siUTemp = ucdata_read[0] * 0x100 + ucdata_read[1]; 	//Put both Bytes into a 16bit Signed Word
		True_Temp = trueTemp(siUTemp); 						//Conversion Subroutine call to a 'true' temperature
		float fTemp = (float) True_Temp / 10; 				//For the purpose of display divide by ten into a float

		ucdata_write[0] = BMP180_CTRL;               	//Control Register=0xF4
		ucdata_write[1] = BMP180_PRERD; 				//Control Code to Start Pressure Read 0x34
		write(file, ucdata_write, 2);					//Send Code to Device
		msdelay(5);                          			//WAIT FOR CONVERSION TO COMPLETE

		/* NB to self don't like this should be checking for ACK from device to i2c Master.
		 * However checking Datasheet you HAVE to use a delay.
		 * Since we have selected sampling rate of 1
		 * A 4.5ms Max time required for Pressure Data to settle.
		 * So use a delay of 5ms (next highest integer).
		 * For the Temperature data the time to settle is ALWAYS a Maximum of 4.5ms
		 * So use a delay of 5ms (next highest integer).
		 * ucdata_write[0] = 0xF6; //Result Register,(NB Can be used for Temperature OR Pressure depending on
		 * what was asked for previously!*/

		 ucdata_write[0]=0xF6;

		write(file, ucdata_write, 1); 							//Write to the i2c device with the register address
		read(file, ucdata_read, 2); 							//Read the data from the i2c device at the register address as above
		siUPress = ucdata_read[0] * 0x100 + ucdata_read[1]; 	//Put both Bytes into a 16bit Signed Word
		True_Press = truePress(siUPress); 						//Conversion Subroutine call to a 'true' pressure
		float fPress = (float) True_Press / 100; 				//For the purpose of display divide by hundred into a float

		char bufftime[20];
		struct tm *sTm;

		time_t now = time(0);
		sTm = gmtime(&now);

		strftime(bufftime, sizeof(bufftime), "%Y-%m-%d %H:%M:%S", sTm);

		//Log to stdout (useful for IPC)
		//printf("%s", bufftime);
		printf("%0.1f ", fTemp);
		printf("%4.1f\r\n", fPress);

		//Log to file
		//FILE *fd;
		//fd = fopen("pressure.txt", "w");
		//fprintf(fd, "%s\n\r", bufftime);
		//fprintf(fd, "%0.1f DegC, %4.1f mBar\r\n", fTemp, fPress);
		//fclose(fd);
		/////////////

	} else {
		printf("\n\rCannot_get_an_ACK_from_the_Device_check_connections!\n\r");
	}

	return EXIT_SUCCESS;
}
