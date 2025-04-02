@echo off
call mvn clean javafx:jlink
call target\app\bin\librarymanager
