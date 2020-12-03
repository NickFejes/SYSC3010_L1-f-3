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
# Transaction_Number, candy_remaining, max_candy, and machine are all read in from an external 
# .csv file that serves as device specific configuration and non-volatile storage
Transaction_Number = 0
candy_remaining = 0
max_candy = 0
machine = ''
refilled = threading.Event()
greeting_length = 0
farewell_length = 0
bagTimeout = 60
waitToLeave = 5


def read_stored_data():
    with open('var.csv', newline = '') as f:
        reader = csv.reader(f)

        data = []

        for row in reader:
            data = row

        return data



def play_audio(filename):
    call("omxplayer " + str(filename), shell=True)


def refill_button_pushed():
    play_audio('candy_refilled.mp3')
    global candy_remaining
    candy_remaining = max_candy
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
    global Transaction_Number
    global max_candy
    Transaction_Number += 1
    if candy_remaining == 0:
        play_audio('out_of_candy.mp3')
        Bot.alert(machine)
        refilled.wait()
        refilled.clear()
    else:
        candy_remaining -= 1
    with open('var.csv', 'w') as csvfile:
        writer = csv.writer(csvfile, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)
        writer.writerow([str(Transaction_Number), str(candy_remaining), machine, str(max_candy)])


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
            break

    if __name__ == "__main__":
        get_data()


while True:

    # read the transaction number and remaining candy from file
    machine_params = read_stored_data()

    Transaction_Number = int(machine_params[0])
    candy_remaining = int(machine_params[1])
    machine = machine_params[2]
    max_candy = int(machine_params[3])

    print(candy_remaining)

    # initialize the serial connection to the arduino
    Connection = UARTW.initializeUART('/dev/ttyACM0')
    # Wait a little while for the connection to be set up
    sleep(2)
    # Wait one minute for the motion sensor to set up
    sleep(60)
    # check if the motion sensor has been triggered
    while True:
        if UARTW.isMotionTriggered(Connection):
            sleep(5)
            # reset the status of the isMotionTriggered parameter to false
            UARTW.clearMotionTriggered(Connection)
            # check again in 3 seconds to confirm movement
            if UARTW.isMotionTriggered(Connection):
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
            sleep(waitToLeave)
            UARTW.clearMotionTriggered(Connection)

    input("Press enter to close")
