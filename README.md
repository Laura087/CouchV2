Couch
=====

Mecanum Couch Project Version 2 includes comms with motor controllers

###NOTE
--It runs fine on Windows, you should get a screen with an output feild and information about each motor. 

--If you run it with no gamepad connected it will output the message "ERROR: no controllers found Controller Initialisation Error". 

--It is configured to work with a microsoft wired USB Xbox controller

--With the default control scheme, holding the right trigger and moving the thumbsticks should change the motor values

--Currently checking status and faults is disabled


###The CONFIG file

The config file uses JSON to change settings for the couch in one place. However, you should not need to do anything with it to get things working. 

###To RUN


####WINDOWS - definitely working

javac -d bin -sourcepath src -cp bin;lib/Controller/jinput.jar;lib/Usb/commons-lang3-3.1.jar;lib/Usb/usb4java-1.0.0.jar;lib/Usb/usb-api-1.0.2.jar src/Main.java

java -Djava.library.path=lib/Controller/natives -cp bin;lib;lib/Controller/jinput.jar;lib/Usb/commons-lang3-3.1.jar;lib/Usb/usb4java-1.0.0.jar;lib/Usb/usb-api.1.0.2.jar Main CONFIG-windows


####LINUX - working, controller requires manual configuration (follow prompts as it runs)

javac -d bin -sourcepath src -cp bin;lib/Controller/jinput.jar;lib/Usb/commons-lang3-3.1.jar;lib/Usb/usb4java-1.0.0.jar;lib/Usb/usb-api-1.0.2.jar src/Main.java

java -Djava.library.path=lib/Controller/natives -Djinput.plugins=net.java.games.input.LinuxEnvironmentPlugin -cp bin:lib:lib/Controller/jinput.jar:lib/Usb/commons-lang3-3.1.jar:lib/Usb/usb4java-1.0.0.jar:lib/Usb/usb-api-1.0.2.jar Main CONFIG-linux

####LINUX ARM (Rasperry Pi) - stripped version working, but no communication with motor controllers

USE STIPPED VERSION

javac -d bin -sourcepath src -cp bin:lib/Controller/jinput.jar: src/Main.java

java -Djava.library.path=lib/Controller/natives -Djinput.plugins=net.java.games.input.LinuxEnvironmentPlugin -cp bin:lib:lib/Controller/jinput.jar: Main CONFIG-linux


