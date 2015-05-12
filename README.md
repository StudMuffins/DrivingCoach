# READ ME

#Simulator

Mac Version/Terminal

To connect to the ports you need to write these codes in terminal:

/Users/hari/Library/Android/sdk/platform-tools/adb forward tcp:8251 tcp:8251 *Enter*

/Users/hari/Library/Android/sdk/platform-tools/adb forward tcp:9898 tcp:9898 *Enter*

/Users/hari/Library/Android/sdk/platform-tools/adb forward tcp:9899 tcp:9899 *Enter*

Then to run the simulator you need to path to the folder containing simulator-fx-1.0.jar or simulator-fx-1.1.jar file then enter this code:
java -jar simulator-fx-1.0.jar or java -jar simulator-fx-1.1.jar

Note: simulator-fx-1.1.jar may have included the word "SNAPSHOT"
