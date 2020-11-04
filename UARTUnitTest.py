import unittest
import UARTWrapper as UART
import serial
from time import sleep

class TestUARTWrapperMethods(unittest.TestCase):

	def test_initializeUART(self):

		try:
			con = UART.initializeUART('COM7')
			# wait a couple of seconds to allow the UART connection to be opened
			sleep(2)

			# confirm that the UARTACK returns the expected value when ACK is called
			self.assertTrue(UART.UARTACK(con))

			UART.closeUART(con)

		except serial.serialutil.SerialException:
			print('Could not open UART connection.  Check device is plugged in and that the correct port was entered')
			self.assertTrue(False)#force an exception failure because connection could not be opened

if __name__ == '__main__':

	unittest.main()
	input('Press enter to continue')
  
