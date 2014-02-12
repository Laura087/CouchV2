Couch
=====

Mecanum Couch Project Version 2 includes comms with motor controllers

###NOTE
--It runs fine on Windows and Ubuntu, you should get a screen with an output feild and information about each motor. 

--For Debian you may need to install the driver for the xbox controller (xboxdrv) via apt-get and then run it with xboxdrv

--If you run it with no gamepad connected it will output the message "ERROR: no controllers found Controller Initialisation Error". 

--It is configured to work with a microsoft wired USB Xbox controller

--With the default control scheme, holding the right trigger and moving the thumbsticks should change the motor values

-- If this does not work try configuring the controller, go to the CONFIG file and change the configured from a 1 to a 0 and follow the prompts after running the program

--Currently checking status and faults is disabled


###The CONFIG file

The config file uses JSON to change settings for the couch in one place. However, you should not need to do anything with it to get things working. 

###To RUN


####WINDOWS - working

manually install the driver for libusb so the motor controllers work (im working on a way to package this)
compile.bat
run.bat

####LINUX - working

TYPE:
mkdir bin
./compile
./run

####LINUX ARM (Rasperry Pi) - so far working - testing incomplete

TYPE:
mkdir bin
./compile-pi
./run-pi

