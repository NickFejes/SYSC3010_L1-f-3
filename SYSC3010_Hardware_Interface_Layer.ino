
#include <Servo.h>


/*----------------------------------------------------------------------------
 * 
 * Command list:
 * 
 * 0  NOP : VOID
 * 1  Check Motion Triggered : BOOLEAN
 * 2  READ BAG PRESENT  : BOOLEAN
 * 3  DISPENSE CANDY  : VOID
 * 4  Clear Motion Triggered  : VOID
 * 5  NOP : VOID
 * 6  NOP : VOID
 * 7  ACK : 7 //the ACK command can be used to confirm that the hardware is connected and is communicating properly via UART
 * 
 ----------------------------------------------------------------------------*/

 #define CHECK_MOTION 1 
 #define READ_BAG_PRESENT 2
 #define DISPENSE_CANDY 3
 #define CLEAR_MOTION 4
 #define ACK 7


/*
 * PIR SENSOR Specs:
 * 
 * Neuftech 3X HC-SR501
 * 
 * Datasheet can be found at https://www.mpja.com/download/31227sc.pdf
 * 
 */


/*----------------------------------------------------------------------------
 * 
 * Hardware Constants
 * 
 * This section has the constants that will be used within functions
 * 
 ---------------------------------------------------------------------------*/

// pin on the arduino to control servo.  Must be PWM capable
#define SERVOPIN 9

// pin on the arduino to read the photoresistor's value
#define PHOTORESISTORPIN A0

// pin on the arduino to read the PIR sensor's output value
#define PIRPIN 4



/*----------------------------------------------------------------------------
 * 
 * Software Constants
 * 
 * This section has the constants that will be used within functions
 * 
 ---------------------------------------------------------------------------*/

//The analog value read from the voltage divider with the photoresistor above which a bag is considered to be present
#define BAGPROXIMITYTHRESHOLD 200

//max angle of the servo when dispensing candy
#define SERVOHIGH 70

//max angle of the servo when dispensing candy.  This is also the steady state angle of the servo
#define SERVOLOW 5


// command sent from pi;
int command;

//temporary value for storing sensor values
int val;

//the trigger state of motion sensor has been triggered a 1 value means that motion has been detected a 0 value means that no motion has been detected
bool motionIsTriggered;

//declare a servo object.  Must be global to be properly use Arduino's library.
Servo dispensingServo;

void setup() {

  //set up the serial interface to communicate with the pi
  Serial.begin(9600);
  
  pinMode(PIRPIN, INPUT);

  //initialize the servo and set it to the steady state location
  dispensingServo.attach(9);
  dispensingServo.write(SERVOLOW);

  command = 0;
  val = 0;

}


//any time a UART event is detected this function is called
void serialEvent(){
  if (Serial.available() > 0) {
    
    command = Serial.parseInt();
    
  }
  
}
 
void loop() {
  
  
  switch(command){
    case (CHECK_MOTION):
      
      if(motionIsTriggered){
        Serial.println("1");
        Serial.flush();
      }
      else{
        Serial.println("0");
        Serial.flush();
      }
      
    break;
    case (READ_BAG_PRESENT):
      
      val = analogRead(PHOTORESISTORPIN);
      if(val <= BAGPROXIMITYTHRESHOLD){
        
        Serial.println("1");
        Serial.flush();
      }
      else{
        
        Serial.println("0");
        Serial.flush();
      }
      
    break;
    case (DISPENSE_CANDY):

      //command servo to release a piece of candy
      dispensingServo.write(SERVOLOW);
      delay(500);
      dispensingServo.write(SERVOHIGH);
      delay(500);
      dispensingServo.write(SERVOLOW);
      
    break;
    case (CLEAR_MOTION):
      
      motionIsTriggered = false;
      
    break;
    case (ACK):
    //Acknowledge returns a 7 to over UART.  This is intended to be used to confirm that the UART connection is open and reading correctly
    
      Serial.print("7");
      Serial.flush();
      
    break;
    default:
      //invalid command or no operation (NOP)
    break;
  }

  // check for motion
  if(digitalRead(PIRPIN)){
    
    motionIsTriggered = true;
    
  }

}
