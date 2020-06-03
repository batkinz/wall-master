DEL program.jar
javac hu/bme/iit/szlab4/continuity/*.java
jar cfm program.jar MANIFEST.mf hu/bme/iit/szlab4/continuity/*.class
DEL /Q /F /S "*.class" 
