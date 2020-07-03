# Prova Finale Ingegneria del Software 2020

## Group AM26

- ### Bonalumi - Marco - 891457 - 10607992 - marco1.bonalumi@mail.polimi.it
- ### Ambrosini Barzaghi - Riccardo - 890072 - 10581009 - riccardo1.ambrosini@mail.polimi.it
- ### Cattaneo - Simone - 889870 - 10580991 - simone6.cattaneo@mail.polimi.it

## Project Functionalities Recap

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules |  [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Multiple games | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Persistence | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Advanced Gods | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Undo | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

## UPDATE on .jar files

Now there are three jar files in the deliverables, one for every OS supported, due to cross-platform issues with JavaFX.

[Client_linux.jar](/deliverables/JAR/Client_linux.jar) works on Linux and on Windows if you have JavaFX installed in Intellij and you are using the JRE that JavaFX has created.

[Client_windows.jar](/deliverables/JAR/Client_windows.jar) works on every PC running a JRE 9 or newer, or a JDK of the same version or higher and a modified path in environment variables.

[Client_macos.jar](/deliverables/JAR/Client_macos.jar) works on Windows with under the same requirements. We don't know what works on MacOS and what doesn't since that we don't own a Mac.

## How To Run .jar Files 

Be sure to have installed Java 9 or newer.

Download the files from [deliverables](/deliverables) folder.



### [Server.jar](/deliverables/JAR/Server.jar)
Run the file on the terminal by typing:
```
java -jar PATH_TO_Server.jar
```
By default the program will use the port `4000`.
You can specify the port on which open the socket by typing it after the file path, like this:
```
java -jar PATH_TO_Server.jar PORT_NUMBER
```
Keep in mind that in some systems like Linux it is forbidden to use ports until the 1024 for non-system tasks.



### [Client.jar](/deliverables/JAR/Client_linux.jar)
Run the file on the terminal by typing
```
java -jar PATH_TO_Client.jar
```
By default this will open the GUI and will use localhost (`127.0.0.1`) and default port (`4000`).

If you want to specify the interface type `cli` or `gui` after the file path, like this:
```
java -jar PATH_TO_Client.jar INTERFACE_MARK
```
e.g. `java -jar ./Client.jar cli`.

Be aware that the program is NOT case-sensitive, so `cli`, `CLI`, `cLi` are all accepted.

If you want to specify the socket parameters you can do it by typing them after the file path, like this:
```
java -jar PATH_TO_Client.jar IP PORT
```
e.g. `java -jar ./Client.jar 192.168.10.83 4234`.

Be aware that the program accepts the custom parameters only if you type them both.

You can obviously type both the socket parameters and the interface mark.

Lastly remember that you can type the parameters in any order you want and they will be accepted as long as they meet the specifications.
