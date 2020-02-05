//Last Update: 2020/02/04 21:24

#include <Servo.h>

String dataReceived;          //Usart 1 PA9 y PA10
String device;
String percentage = "s";
String writeTo;
char* str;
char* end_;

//These are the pins for STM32
const int pinServo1 = PB8;
const int pinServo2 = PA8;
const int pinServo3 = PB9;
const int pump1 = PA11;
const int pump2 = PA12;
const int pump3 = PA2;
const int pump4 = PB3;
const int pump5 = PA4;
const int motorIzq = PB11;
const int motorDer = PB10;
//const int selecter = PB0;
//const int confirm = PB1;

/*
 * These are the pins for Arduino
const int pinServo1 = 2;
const int pinServo2 = 3;
const int pinServo3 = 4;
const int pump1 = 5;
const int pump2 = 6;
const int pump3 = 7;
const int pump4 = 8;
const int pump5 = 9;
const int motorIzq = 10;
const int motorDer = 11;
*/

const byte numChars = 100;
char receivedChars[numChars];   // an array to store the received data

bool inProgress = false;
boolean newData = false;

Servo servo1;
Servo servo2;
Servo servo3;

//position in degrees
int dispenser1 = 0;
int dispenser2 = 120;
int dispenser3 = 240;
int pump1Pos = dispenser1;
int pump2Pos = dispenser2;
int pump3Pos = dispenser2;
int pump4Pos = dispenser3;
int pump5Pos = dispenser3;
int pos;
int lastPos;
int dly;

void setup() {
  //Declare push buttons for manual control
//  pinMode(selecter, INPUT);
//  pinMode(confirm, INPUT);
  
  //Attach servo pins
  servo1.attach(pinServo1);
  servo2.attach(pinServo2);
  servo3.attach(pinServo3);

  //Set pumps as outputs
  pinMode(pump1, OUTPUT);  
  pinMode(pump2, OUTPUT);  
  pinMode(pump3, OUTPUT);  
  pinMode(pump4, OUTPUT);  
  pinMode(pump5, OUTPUT);

  //Set pins for motor control
  pinMode(motorIzq, OUTPUT);
  pinMode(motorDer, OUTPUT);

  servo1.write(0);
  servo2.write(0);
  servo3.write(0);

  Serial.begin(19200);
  Serial.println("Welcome!");
}

void rotateTo (int fromPos, int toPos){
  lastPos = toPos;
  int deltaGrad;
  deltaGrad = abs(toPos - fromPos);
  //Position is less than desired position
  //Motor spins counter-clockwise
  if (fromPos < toPos){
//    display.clearDisplay();
//    display.setCursor(30,30);
//    display.print("Moving");
//    display.display();
    digitalWrite (motorIzq,HIGH);
    digitalWrite (motorDer,LOW);
    Serial.println("Moving");
    delay (23.5*deltaGrad);       //Calculate how much time in ms is required for one degree
  }
  //Position is greater than desired position
  //Motor spins clockwise
  else if (fromPos > toPos){
    digitalWrite (motorIzq,LOW);
    digitalWrite (motorDer,HIGH);
    Serial.println("Moving");
    delay(23.5*deltaGrad);
  }
  digitalWrite (motorIzq,LOW);
  digitalWrite (motorDer,LOW);
  Serial.println("Stopped");
}

void addMixer(int dev, int qty, int desiredPos){
  if (lastPos != desiredPos){
    rotateTo(lastPos, desiredPos);
  }
  digitalWrite(dev, HIGH);
  Serial.println("Adding Mixer");
  delay(10*qty);
  digitalWrite(dev, LOW);
  Serial.println("Mixer Added");
}

void addAlcohol(int dev, int qty, int desiredPos){
  if (lastPos != desiredPos) {
    rotateTo(lastPos, desiredPos);
  }
  Serial.println("Adding Alcohol");
  if (dev == pinServo1) {
    servo1.write(120);
    delay(500*qty);
    servo1.write(0);
  } else if (dev == pinServo2) {
    servo2.write(120);
    delay(500*qty);
    servo2.write(0);
  } else if (dev == pinServo3) {
    servo3.write(120);
    delay(500*qty);
    servo3.write(0);
  }
  delay(1000);
  Serial.println("Alcohol Added");
}

void loop() {
  recvWithEndMarker();
  showNewData();
  if (inProgress){
    char str_arr[dataReceived.length() + 2];
    dataReceived.toCharArray(str_arr, dataReceived.length() + 2);
    str = strtok_r(str_arr, ",", &end_);
    do {
      char* end_str;
      device = strtok_r(str, ":", &end_str);
      writeTo = device;
      while(device != NULL) {
        device = strtok_r(NULL, ":", &end_str);
        percentage = device;
        if (percentage == NULL)
          break;
        dly = percentage.toInt();
      }
      str = strtok_r(NULL, ",", &end_);
      Serial.print(writeTo);
      Serial.print(" for ");
      Serial.print(dly);
      Serial.println(" milliseconds");
      if (writeTo == String ("p1")){
        addMixer(pump1, dly, pump1Pos);
      } else if (writeTo == String ("p2")){
        addMixer(pump2, dly, pump2Pos);
      } else if (writeTo == String ("p3")){
        addMixer(pump3, dly, pump3Pos);
      } else if (writeTo == String ("p4")){
        addMixer(pump4, dly, pump4Pos);
      } else if (writeTo == String ("p5")){
        addMixer(pump5, dly, pump5Pos);
      } else if (writeTo == String ("d1")){
        addAlcohol(pinServo1, dly, dispenser1);
      } else if (writeTo == String ("d2")){
        addAlcohol(pinServo2, dly, dispenser2);
      } else if (writeTo == String ("d3")){
        addAlcohol(pinServo3, dly, dispenser3);
      }
      else {
        Serial.println ("Error. Select valid device.");
        inProgress = false;
      }
    } while(str != NULL);
    Serial.println("Done!");
    inProgress = false;
  }
}

void recvWithEndMarker() {
    static byte ndx = 0;
    char endMarker = '\n';
    char rc;
   
    while (Serial.available() > 0 && newData == false) {
        rc = Serial.read();

        if (rc != endMarker) {
            receivedChars[ndx] = rc;
            ndx++;
            if (ndx >= numChars) {
                ndx = numChars - 1;
            }
        }
        else {
            receivedChars[ndx] = '\0'; // terminate the string
            ndx = 0;
            newData = true;
        }
    }
}

void showNewData() {
    if (newData == true) {
        dataReceived = receivedChars;
        Serial.println(dataReceived);
        inProgress = true;
        newData = false;
    }
}

// p1:30,p2:40,d2:30,p3:50
// 0123456789012345
