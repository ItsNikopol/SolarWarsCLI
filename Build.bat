@Echo off
Echo Building......
if not exist "out\" mkdir out
javac -d out\ src\*.java
copy src\META-INF\MANIFEST.MF out\
cd out
jar cfm SolarWars.jar MANIFEST.MF *.class
del *.class
del MANIFEST.MF
Echo Done.
pause
