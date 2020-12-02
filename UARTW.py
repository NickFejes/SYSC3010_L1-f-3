import serial# use pyserial
from time import sleep

"""
initializeUART(portName):

Initializes the Serial connection to the given serial port.  Returns an object
reference to the serial connection.

"""
def initializeUART(portName):

    # initialize serial connection
    ser = serial.Serial(portName, baudrate=9600, timeout=1)

    # return true if the connection was openned successfully
    return ser


"""
isMotionTriggered(Connection):

Returns whether the motion sensor of the given Serial Connection reference
has been tripped. 

"""
def isMotionTriggered(Connection):

    Connection.write(b'1\x10')

    data = Connection.readline().decode('ascii')

    if(int(data) == 1):
        return True
    else:
        return False


"""
UARTACK(Connection):

requests that the Arduino return a 7 over UART.  This is intended to be used in
order to confirm that the UART connection was successfully openned and is 
reading correctly.

"""
def UARTACK(Connection):

    Connection.write(b'7\x10')

    data = Connection.readline().decode('ascii')

    if int(data) == 7:
        return True
    else:
        return False


"""
isBagPresent(Connection):

Returns whether there is a bag in close enough proximity to the device at the 
given Connection reference. 

"""
def isBagPresent(Connection):
    Connection.write(b'2\x10')

    data = Connection.readline().decode('ascii')

    if(int(data) == 1):
        return True
    else:
        return False


"""
dispenseCandy(Connection):

Dispenses one gumball from the device at the given Connection reference 

"""
def dispenseCandy(Connection):
    Connection.write(b'3\x10')


"""
clearMotionTriggered(Connection):

Clears the triggered status of the motion sensor with the given Connection 
reference. 

"""
def clearMotionTriggered(Connection):
    Connection.write(b'4\x10')


"""
closeUART(Connection):

Closes the given serial Connection reference.

"""
def closeUART(Connection):
    Connection.close()


"""Example for usage of functions in this module"""
if __name__ == '__main__':

    # initialize the serial connection on COM7
    try:
        Connection = initializeUART('/dev/ttyACM0')
    except serial.serialutil.SerialException:
        print('Could not open UART connection.  Check device is plugged in and that the correct port was entered')
        #input('press enter to close')
        exit()

    # Wait a little while for the connection to be set up
    sleep(2)

    if UARTACK(Connection):
        print("UART Opened Successfully")
    else:
        #If there was an issue reading the ACK response then an error message is displayed
        print('Unexpected response from UART')
        #input('press enter to quit')
        exit()

    # check if the motion sensor has been triggered
    print(isMotionTriggered(Connection))

    # check if there is a bag to be dispensed into
    isBag = isBagPresent(Connection)
    print(isBag)

    # if there is a bag then dispense a gumball
    if isBag:
        dispenseCandy(Connection)

    # reset the status of the isMotionTriggered parameter to false
    clearMotionTriggered(Connection)

    # close the UART connection when finished
    closeUART(Connection)
