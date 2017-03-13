REM @echo off


:CREATING
if exist "C:\Program Files\Java\jdk\bin\jar.exe" (
	@title Creating JuggleMasterPro.jar...
	@echo.
	@echo Creating JuggleMasterPro.jar...
	@echo Archive date : %DATE%, %TIME:~,5% >.\compilation\javac.dat
	if exist "..\applet\JuggleMasterPro.jar" @del /F /Q "..\applet\JuggleMasterPro.jar"
	@"C:\Program Files\Java\jdk\bin\jar.exe" cfmv "..\applet\JuggleMasterPro.jar" ".\compilation\JuggleMasterPro.mf" -C "..\applet\classes" "jugglemasterpro" -C "..\applet" "lib" -C "..\applet" "data" -C "..\applet" "images" -C "..\applet" "sounds"
) else (
	@title Compilator missing...
	@echo.
	@echo Compilator missing...
)
if not exist "..\applet\JuggleMasterPro.jar" goto ENDING


:GENERATING
@title Generating key store...
@echo.
@echo Generating key store...
if exist ".\compilation\JuggleMasterPro.keyStore" (
	@del /f /q ".\compilation\JuggleMasterPro.keyStore" 2>nul
)
@"C:\Program Files\Java\jdk\bin\keytool.exe" -genkeypair -keystore ".\compilation\JuggleMasterPro.keyStore" -storepass "JuggleMaster Pro" -keypass "JuggleMaster Pro" -keysize "1024" -dname "CN = Arnaud BeLO., OU = JuggleMaster Pro, O = JuggleMasterPro, L = Saillans, ST = France, C = fr" -alias "JuggleMasterProSignature" -validity "730" -v


:SIGNING
@title Signing JuggleMasterPro.jar...
@echo.
@echo Signing JuggleMasterPro.jar...
@"C:\Program Files\Java\jdk\bin\jarsigner.exe" -keystore ".\compilation\JuggleMasterPro.keyStore" -storepass "JuggleMaster Pro" -keypass "JuggleMaster Pro" -sigfile "SHA-256" -verbose "..\applet\JuggleMasterPro.jar" "JuggleMasterProSignature"


:INDEXING
@title Indexing JuggleMasterPro.jar...
@echo.
@echo Indexing JuggleMasterPro.jar...
@cd "..\applet\"
@"C:\Program Files\Java\jdk\bin\jar.exe" -i "JuggleMasterPro.jar"
@cd "..\tools\"


:ENDING
@pause
@title Invite de Commandes
