Couch
=====

Mecanum Couch Project Version 2 includes comms with motor controllers

###NOTE
--It runs fine on Windows, you should get a screen with an output feild and information about each motor. 

--If you run it with no gamepad connected it will output the message "ERROR: no controllers found Controller Initialisation Error". 

--It is configured to work with a microsoft wired USB Xbox controller

--With the default control scheme, holding the right trigger and moving the thumbsticks should change the motor values


###The CONFIG file

The config file uses JSON to change settings for the couch in one place. However, you should not need to do anything with it to get things working. 

###To RUN


####WINDOWS - definitely working

jjavac -d bin -sourcepath src -cp bin;lib/Controller/jinput.jar;lib/Usb/commons-lang3-3.1.jar;lib/Usb/usb4java-1.0.0.jar;lib/Usb/usb-api-1.0.2.jar src/Main.java

java -Djava.library.path=lib/Controller/natives -cp bin;lib;lib/Controller/jinput.jar;lib/Usb/commons-lang3-3.1.jar;lib/Usb/usb4java-1.0.0.jar;lib/Usb/usb-api.1.0.2.jar Main


####LINUX - untested

jjavac -d bin -sourcepath src -cp bin;lib/Controller/jinput.jar;lib/Usb/commons-lang3-3.1.jar;lib/Usb/usb4java-1.0.0.jar;lib/Usb/usb-api-1.0.2.jar src/Main.java

java -Djava.library.path=lib/Controller/natives -Djinput.plugins=net.java.games.input.LinuxEnvironmentPlugin -cp bin;lib;lib/Controller/jinput.jar;lib/Usb/commons-lang3-3.1.jar;lib/Usb/usb4java-1.0.0.jar;lib/Usb/usb-api.1.0.2.jar Main

####LINUX ARM (Rasperry Pi) - not supported yet

