# Blackjack

---
## Runtime
Ensure that you have java 11 installed and accessible as well as maven. 
You will find the versions that were used below.
### Java
```
openjdk 11.0.29 2025-10-21
OpenJDK Runtime Environment (build 11.0.29+7-post-Ubuntu-1ubuntu122.04)
OpenJDK 64-Bit Server VM (build 11.0.29+7-post-Ubuntu-1ubuntu122.04, mixed mode, sharing)
```
### Maven
```
Apache Maven 3.6.3
Maven home: /usr/share/maven
Java version: 11.0.29, vendor: Ubuntu, runtime: /usr/lib/jvm/java-11-openjdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "6.6.87.2-microsoft-standard-wsl2", arch: "amd64", family: "unix"
```
---
## Build
Navigate to the root of the git repository and run the following command
```
mvn clean package
```
---
## Run
From the root of the git repository run the following command
```
java -jar ./target/blackjack-1.0.0.jar
```
## Features
- Core Rules - Dealing cards, Hit/Stand, Ace Value, Winning/Losing to dealer
- Multiplayer - Up to four players
- Betting - In increments of 5 up to the pot value
- Splitting - Splitting into and resolving multiple hands
- Doubling Down - Dealing one more card and resolving with double the bet
- Continuous Play - Play can be continued with updated pot values