# Pi Motion Detector (Java)
This uses a Rasperry Pi Zero W combined with a LV-MaxSonar-EZ0 to monitor a door or other area and notify you via email when motion is detected.

## Build Instructions

### Compile And Run
```bash
mvn compile
mvn exec:java
```

### Compile Into Jar With Dependencies 
```bash
mvn clean compile assembly:single
```

## Command line Arguments
```bash
 -from VAL    : Email source. (default: pi@motiondetector.com)
 -pass VAL    : SMTP password.
 -port N      : SMTP port.
 -serial VAL  : Sensor serial port. (default: /dev/serial0)
 -server VAL  : SMTP server.
 -subject VAL : Email subject. (default: Motion Detected.)
 -text VAL    : Email text body. (default: Motion has been detected at the time this email was sent.)
 -to VAL      : Email destination.
 -user VAL    : SMTP username.
```

## Required Hardware
### Raspberry PI Zero W
Any model will do. A wireless enabled pi may be helpful to some people. I'll be using the pi zero w.
### LV-MxxSonar-EZ0
* [DataSheet](https://www.pololu.com/file/0J68/LV-MaxSonar-EZ0-Datasheet.pdf)
