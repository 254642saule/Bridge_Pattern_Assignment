@echo off
setlocal
cd /d "%~dp0"
if not exist out mkdir out
javac --release 17 -Xlint:all -Werror -d out src\bridge\*.java test\bridge\*.java
if errorlevel 1 exit /b 1
java -cp out bridge.BridgePatternTest
