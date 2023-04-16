@Echo off
Echo "Building......"
javac -d out\ src\*.java
copy src\META-INF\MANIFEST.MF out\
cd out
copy src\META-INF\MANIFEST.MF out\
del *.class
del *.class
Echo "Done."