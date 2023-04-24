# Solarwars (CLI)

SolarwarsCLI is an updated version of [Solarwars game for Palm computers](https://archive.org/details/tucows_57639_SolarWars) which is an updated version of "DrugWars."
Moving away from the original drug theme (deemed offensive by some), the object of SolarWars is to warp around the solar system, making money buying and selling cargo.

## Requirements:

- Java 17 (JDK to build)
    - Arch: `pacman -Sy jdk17-openjdk` or just JRE `pacman -Sy jre17-openjdk`
    - Debian-based: `apt update && apt install openjdk-17-jdk` or just JRE `apt update && apt install openjdk-17-jre`
- UTF-8 Terminal
  - If you use Windows 8 or earlier, you can use [ConEmu](https://conemu.github.io/).
## Building:
### Windows

- Open `Build.bat`.

You will find JAR in `out/SolarWars.jar`.

### Linux

- Open terminal in folder
- Make file executable. `chmod +x Build.sh`
- Run script. `./Build.sh`

You will find JAR in `out/SolarWars.jar`.

### IntelliJ IDEA

- Open project in IntelliJ IDEA.
- Go to Build > Build Artifacts... > Build.

You will find JAR in `out/artifacts/SolarWars_jar/SolarWars.jar`.

## Running
### Windows

You can use [this batch](https://raw.githubusercontent.com/ItsNikopol/SolarWarsCLI/main/batches/Play.bat) to play the game.

- Set codepage to UTF-8: `chcp 65001`
- Start the game, using downloaded JRE/JDK: `java -jar SolarWars.jar`

### Linux

Run the game using TTY or terminal emulator: `java -jar SolarWars.jar`