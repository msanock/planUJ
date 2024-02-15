# PlantUJ

PlanUJ is an app that can help you with organising your team,
managing different tasks and many more!

## Technologies
 - Java
 - JavaFX
 - Gradle
 - JUnit
 - PostgreSQL
 - Mockito

## How to run
by default application and server runs locally.

to change that, you need to overwrite field `HOST` in `edu/planuj/clientConnection/ConnectionSettings.java` to the IP address of designated server.

to run server:
```
$ ./run_server.sh
```
to run client:
```
$ ./run_client.sh
```
