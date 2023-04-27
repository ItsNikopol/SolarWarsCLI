# Solarwars (CLI)

SolarwarsCLI is an updated version of [Solarwars game for Palm computers](https://archive.org/details/tucows_57639_SolarWars) which is an updated version of "DrugWars."
Moving away from the original drug theme (deemed offensive by some), the object of SolarWars is to warp around the solar system, making money buying and selling cargo.

## Requirements:

- Java 17 (JDK to build)
    - Arch: `pacman -Sy jdk17-openjdk` or just JRE `pacman -Sy jre17-openjdk`
    - Debian-based: `apt update && apt install openjdk-17-jdk` or just JRE `apt update && apt install openjdk-17-jre`
## Building:
### Windows

- Open `Build.bat`.

You will find JAR in `build/libs/SolarWars.jar`.

### Linux

- Open terminal in folder
- Make gradlew runnable: `chmod +x gradlew`
- Clean build directory: `./gradlew clean`
- Build project: `./gradlew build`

You will find JAR in `build/libs/SolarWars.jar`.

If you want to run game from sources without jar, execute `./gradlew run` instead of `./gradlew build`.

### IntelliJ IDEA

- Open project in IntelliJ IDEA.
- Go to Build > Build Project

You will find JAR in `build/libs/SolarWars.jar`.

## Running
### Windows

- Start the game, using downloaded JRE/JDK: `java -jar SolarWars.jar`

### Linux

- Run the game: `java -jar SolarWars.jar`

