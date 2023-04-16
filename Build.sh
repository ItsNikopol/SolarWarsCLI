#!/bin/bash
echo Building......
mkdir -p out
javac -d out/ src/*.java
cp src/META-INF/MANIFEST.MF out/
cd out
jar cfm SolarWars.jar MANIFEST.MF *.class
rm *.class
rm MANIFEST.MF
echo Done.
