
#include <Servo.h>


//PWM
#define SERVOPIN 9// PWM pin to control servo
//Analog
#define SERVOFEEDBACKPIN A1// optional feedback pin for the servo to double check the PWM
//Analog
#define PHOTORESISTORPIN A0// pin to read the photoresistor's value
//Analog or digital TBD
#define PIRPIN 4// pin to read the PIR sensor's output value

/*
 * PIR SENSOR:
 * 
 * Neuftech 3X HC-SR501
 * 
 */

//constants
#define BAGPROXIMITYTHRESHOLD 670
//servo max position
#define SERVOHIGH 70
//servo min position
#define SERVOLOW 5


// command sent from pi;
int command;
//temporary value for readings to be read into
int val;
//the motion sensor has been triggered
bool motionIsTriggered;


Servo dispensingServo;

void setup() {

  //set up the serial interface to communicate with the pi
  Serial.begin(9600);

  //set up all pins to be used
  //pinMode(SERVOPIN, OUTPUT);
  //pinMode(SERVOFEEDBACKPIN, INPUT);// not strickly needed but would be a good sanity check or can be used to manually impliment PWM
  
  //pinMode(PHOTORESISTORPIN, INPUT);
  
  
  pinMode(PIRPIN, INPUT);

  //servo should start in a known position
  //analogWrite(SERVOPIN, 0);

  dispensingServo.attach(9);
  dispensingServo.write(SERVOLOW);

  command = 0;
  val = 0;


  //setup the onboard LED to output for testing
  pinMode(13, OUTPUT);
  digitalWrite(13, LOW);
}

/*
 * 
 * Command list:
 * 
 * 0  null : null
 * 1  Check Motion Triggered : BOOLEAN
 * 2  READ BAG PRESENT  : BOOLEAN
 * 3  DISPENSE CANDY  : VOID
 * 4  Clear Motion Triggered  : VOID
 * 5  NOP : VOID
 * 6  NOP : VOID
 * 7  NOP : VOID
 * 
 */

void serialEvent(){
  if (Serial.available() > 0) {
    //command = Serial.read();
    command = Serial.parseInt();
    //Serial.println(command);
  }
  
}
 
void loop() {
  /*if (Serial.available() > 0) {

    command = Serial.parseInt();
    //Serial.println(command);
    
   }*/

  
  switch(command){
    case (0):
      
    break;
    case (1):
      //Check Motion Triggered : BOOLEAN
      if(motionIsTriggered){
        Serial.println("1");
        Serial.flush();
      }
      else{
        Serial.println("0");
        Serial.flush();
      }
      
    break;
    case (2):
      //READ BAG PRESENT  : BOOLEAN
      
      val = analogRead(PHOTORESISTORPIN);
      if(val <= BAGPROXIMITYTHRESHOLD){
        //Serial.println(val);
        Serial.println("1");
        Serial.flush();
      }
      else{
        //Serial.println(val);
        Serial.println("0");
        Serial.flush();
      }
      
    break;
    case (3):
      //3  DISPENSE CANDY  : VOID
      dispensingServo.write(SERVOLOW);
      delay(500);
      dispensingServo.write(SERVOHIGH);
      delay(500);
      dispensingServo.write(SERVOLOW);
    break;
    case (4):
      //4  Clear Motion Triggered  : VOID
      motionIsTriggered = false;
      break;
    default:
      //invalid command becomes a NOP
    break;
  }

  //Read value of motion sensor
      if(digitalRead(PIRPIN)){
        digitalWrite(13, HIGH);
        motionIsTriggered = true;
      }
      else{
        digitalWrite(13, LOW);
      }

  

}
