@echo off
@mode con: cols=99 lines=9999
@title JuggleMaster Pro
if exist "javac.dat" (
	@type "javac.dat" 2>>nul
) else (
	@echo Date de compilation inconnue.
)
@cd "..\applet"
@java -jar ".\JuggleMasterPro.jar"
@cd "..\tools"
@pause