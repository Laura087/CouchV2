rm -r bin
mkdir bin
javac -d bin -sourcepath src -cp bin;lib/Controller/jinput.jar;lib/Usb/commons-lang3-3.1.jar;lib/Usb/usb4java-1.0.0.jar;lib/Usb/usb-api-1.0.2.jar src/Main.java


