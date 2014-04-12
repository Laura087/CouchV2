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

manually install the driver for libusb so the motor controllers work (I have been using zadig to do this)
compile.bat
run.bat

####LINUX - working

In ubuntu the xbox controller driver is installed already
In debian you need to apt-get install xboxdrv
Then run it with sudo xboxdrv

mkdir bin
./compile
./run

####LINUX ARM (Rasperry Pi - Raspbian) - working

Only tested in raspbian
sudo apt-get install xboxdrv
sudo xboxdrv --silent &

mkdir bin && ./compile-pi <br>
./run-pi

Currently needs a Xserver to run so need to ssh into pi with option -X.

To access pi without router give the pi a static ip add 
ip=192.168.0.200:::255.255.255.0 to cmdline.txt in the boot section of the 
sd card.

The usb ports need to be rw so they can be accessed - 
sudo chmod 777 -R /dev/bus/usb #seems to fix it


