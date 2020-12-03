import UARTW
import http.client
import urllib
import datetime
from time import sleep
from subprocess import call
import RPi.GPIO as GPIO
import Bot
import threading
import csv


# Variable declerations, Machine is different for each dispenser and matches student id "a, b, c"
Transaction_Number = 0
candy_remaining = 0
machine = ''
refilled = threading.Event()
greeting_length = 0
farewell_length = 0
bagTimeout = 60
waitToLeave = 5


def read_stored_data():
    with open('var.csv', newline = '') as f:
        reader = csv.reader(f)

        i = 0

        data = []

        for row in reader:
            if i == 1:
                print(row)
                data = row
            i += 1
        return data



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
# Set pin 10 to be an input pin and set initial value to be pulled low (off)
GPIO.setup(10, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.add_event_detect(10,GPIO.RISING,callback=refill_button_pushed) # Setup event on pin 17 rising edge


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
    key = "S14H8SNRPP3OLY9H"  # Put your API Key here

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

    # read the transaction number and remaining candy from file
    machine_params = read_stored_data()
#    print(machine_params)

    Transaction_Number = machine_params[0]
    candy_remaining = machine_params[1]
    machine = machine_params[2]

    print(candy_remaining)

    # initialize the serial connection to the arduino
    Connection = UARTW.initializeUART('/dev/ttyACM0')
    # Wait a little while for the connection to be set up
    sleep(2)
    # Wait one minute for the motion sensor to set up
#    sleep(60)
    # check if the motion sensor has been triggered
    while True:
        if UARTW.isMotionTriggered(Connection):
            print('sleep(5)')
            sleep(5)
            # reset the status of the isMotionTriggered parameter to false
            print('clear motion')
            UARTW.clearMotionTriggered(Connection)
            print('sleep(2)')
#            sleep(2)
            # check again in 3 seconds to confirm movement
            if UARTW.isMotionTriggered(Connection):
                print('play audio')
                # Play audio greeting
                play_audio('greeting.mp3')
                sleep(greeting_length)
                for i in range(2*bagTimeout):
                    # check if there is a bag to be dispensed into
                    if UARTW.isBagPresent(Connection):
                        # dispense candy
                        UARTW.dispenseCandy(Connection)
                        # play audio well wishing
                        play_audio('happy_halloween.mp3')
                        sleep(farewell_length)
                        update_database()
                        check_remaining_candy()
                        break
                    sleep(0.5)
            # reset the status of the isMotionTriggered parameter to false
            print('wait to leave')
            sleep(waitToLeave)
            UARTW.clearMotionTriggered(Connection)

    input("Press enter to close")
