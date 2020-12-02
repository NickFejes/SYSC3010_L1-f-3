import UARTW
import http.client
import urllib
import datetime
from time import sleep
from subprocess import call
import RPi.GPIO as GPIO
import Bot
import threading

# Variable declerations, Machine is different for each dispenser and matches student id "a, b, c"
Transaction_Number = 1
candy_remaining = 10
machine = 'a'
refilled = threading.Event()


def play_audio(filename):
    call("omxplayer " + str(filename), shell=True)


def refill_button_pushed():
    play_audio('candy_refilled.mp3')
    global candy_remaining
    candy_remaining = 10
    refilled.set()


# Set up GPIO for refill button

GPIO.setwarnings(False)# Ignore warning for now
GPIO.setmode(GPIO.BOARD)# Use physical pin numbering
# Set pin 17 to be an input pin and set initial value to be pulled low (off)
GPIO.setup(17, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.add_event_detect(17,GPIO.RISING,callback=refill_button_pushed) # Setup event on pin 17 rising edge


def check_remaining_candy():
    global candy_remaining
    global machine
    if candy_remaining == 0:
        play_audio('out_of_candy.mp3')
        Bot.alert(machine)
        refilled.wait()
        refilled.clear()
    else:
        pass


def update_database():
    key = "RA2AK3DH8XKKEOQE"  # Put your API Key here

    def get_data():
        global candy_remaining
        global Transaction_Number
        while True:
            time = datetime.datetime.now().time()
            param = urllib.parse.urlencode({'field1': Transaction_Number, 'field2': time, 'field3': candy_remaining, 'key': key})
            headers = {"Content-typZZe": "application/x-www-form-urlencoded", "Accept": "text/plain"}
            conn = http.client.HTTPConnection("api.thingspeak.com:80")
            try:
                conn.request("POST", "/update", param, headers)
                response = conn.getresponse()
                print(Transaction_Number, ',\n ', time, ',\n ', candy_remaining)
                print(response.status, response.reason)
                conn.close()
            except:
                print("connection failed")
            Transaction_Number += 1
            candy_remaining -= 1
            break

    if __name__ == "__main__":
        get_data()


while True:
    # initialize the serial connection to the arduino
    Connection = UARTW.initializeUART('/dev/ttyACM0')
    # Wait a little while for the connection to be set up
    sleep(2)
    # Wait one minute for the motion sensor to set up
    sleep(60)
    # check if the motion sensor has been triggered
    while True:
        if UARTW.isMotionTriggered(Connection) == 1:
            # reset the status of the isMotionTriggered parameter to false
            UARTW.clearMotionTriggered(Connection)
            # check again in 3 seconds to confirm movement
            sleep(3)
            if UARTW.isMotionTriggered(Connection) == 1:
                # Play audio greeting
                play_audio('greeting.mp3')
                # check if there is a bag to be dispensed into
                if UARTW.isBagPresent(Connection) == 1:
                    # dispense candy
                    UARTW.dispenseCandy(Connection)
                    # play audio well wishing
                    play_audio('happy_halloween.mp3')
                check_remaining_candy()
        # reset the status of the isMotionTriggered parameter to false
        UARTW.clearMotionTriggered(Connection)

    input("Press enter to close")
