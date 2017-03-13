REM @echo off

@title sign JuggleMasterPro.jar...

if exist "..\applet\sJuggleMasterPro.jar" @del /F /Q "..\applet\sJuggleMasterPro.jar" >nul 2>&1
if exist "JuggleMasterProStore" @del /F /Q "JuggleMasterProStore" >nul 2>&1

@echo Usual passphrase for keystore: JuggleMaster Pro
@"C:\Program Files\Java\jdk\bin\keytool.exe" -genkey -alias JuggleMasterProSignature -keystore "JuggleMasterPro.keyStore"

@cd "..\applet"
@copy "JuggleMasterPro.jar" "sJuggleMasterPro.jar" >nul
@"C:\Program Files\Java\jdk\bin\jarsigner.exe" -keystore "..\tools\JuggleMasterPro.keyStore" -verbose -signedjar "JuggleMasterPro.jar" "sJuggleMasterPro.jar" JuggleMasterProSignature
@"C:\Program Files\Java\jdk\bin\jar.exe" -i "JuggleMasterPro.jar"
if exist "sJuggleMasterPro.jar" @del /F /Q "sJuggleMasterPro.jar" >nul 2>&1
@cd "..\tools"
@pause

