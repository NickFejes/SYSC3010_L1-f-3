The following ReadMe file contains instructions for setting up the project associated with this repository.

Dispenser setup:
  Place dispenser outside house with motion dispenser facing out. Plug the dispenser into a power supply.
Database setup:
  Execute these commands in mysql workbench.

CREATE DATABASE CandyDispenser;
USE CandyDispenser;

CREATE TABLE machines ( 
TsChannelId INT UNIQUE NOT NULL, 
ReadKey Text NOT NULL, 
MachineName TEXT NOT NULL,CurrentAmount int ,
TotalDispensed int ,
location TEXT NOT NULL ) ;

CREATE TABLE timeLog (
TsChannelId INT NOT NULL,
record DATETIME);

Download the file: “DatabaseUpdate.py” and run it. 

Server setup:
  Create new ROOT folder at “apache-tomcat-9.0.39\webapps”
  Place all Html and jsp files at “apache-tomcat-9.0.39\webapps\ROOT”
  Place the .class files for all java classes at “apache-tomcat-9.0.39\webapps\ROOT\WEB-INF\classes” 
  Place the web.xml file at “apache-tomcat-9.0.39\webapps\ROOT\WEB-INF” 

Initial Raspberry Pi Units Setup: 
  1. Download the files: “main.py”, “bot.py”, and “UARTW.py”.
  2. Download the audio files: “out_of_candy.mp3”, “candy_refilled.mp3”, “greeting.mp3” and “happy_halloween.mp3”.
  3. Connect to your raspberry pi via SSH. Once in the command line, execute the following commands to download and install the required code libraries and third party programs.
    sudo apt-get install python3
    sudo apt-get install python3-pip 
    sudo apt-get install omxplayer
    sudo apt-get
    sudo pip install python-csv
    sudo pip install discord.py
    sudo pip install python_http_client
    sudo pip install datetime
  4. Download the file var.csv and modify the four internal variables:
    Transaction number, set to one for initial setup
    Candy remaining, set to the current amount of candy in the dispenser, should be equal to the maximum candy for setup.
    Machine id, set to your team id (a,b,c…)
    Maximum candy. Set to the maximum amount of candy in the dispenser.

Arduino Setup: 
  Install the code “SYSC3010_Hardware_Interface_Layer.ino” onto the arduino using the arduino IDE. You may need to modify the value of the photoresistor sensitivity depending on the light conditions where the dispenser is set up.

Run Procedure: 
  1. Initialize the virtual machine containing the database.
  2. Run main.py on each machine connected to the database.
