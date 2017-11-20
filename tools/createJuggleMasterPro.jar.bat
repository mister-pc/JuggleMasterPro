@echo off


:CREATING
if exist "C:\Program Files\Java\jdk\bin\jar.exe" (
	@title Creating JuggleMasterPro.jar...
	@echo.
	@echo Creating JuggleMasterPro.jar...
	@echo Archive date : %DATE%, %TIME:~,5% >.\compilation\javac.dat
	if exist "..\JuggleMasterPro.jar" @del /F /Q "..\JuggleMasterPro.jar"
	@"C:\Program Files\Java\jdk\bin\jar.exe" cfmv "..\JuggleMasterPro.jar" ".\compilation\JuggleMasterPro.mf" -C "..\class" "jugglemasterpro" -C ".." "lib" -C ".." "data" -C ".." "img" -C ".." "snd"
) else (
	@title Compilator missing...
	@echo.
	@echo Compilator missing...
)
if not exist "..\JuggleMasterPro.jar" goto ENDING


:GENERATING
@title Generating key store...
@echo.
@echo Generating key store...
if exist ".\compilation\JuggleMasterPro.key" (
	@del /f /q ".\compilation\JuggleMasterPro.key" 2>nul
)
@"C:\Program Files\Java\jdk\bin\keytool.exe" -genkeypair -keystore ".\compilation\JuggleMasterPro.key" -storepass "JuggleMaster Pro" -keypass "JuggleMaster Pro" -keysize "1024" -dname "CN = Arnaud BeLO., OU = JuggleMaster Pro, O = http://www.jugglemaster.fr, L = Saillans, ST = France, C = fr" -alias "JuggleMasterProSignature" -validity "730" -v


:SIGNING
@title Signing JuggleMasterPro.jar...
@echo.
@echo Signing JuggleMasterPro.jar...
@"C:\Program Files\Java\jdk\bin\jarsigner.exe" -keystore ".\compilation\JuggleMasterPro.key" -storepass "JuggleMaster Pro" -keypass "JuggleMaster Pro" -sigfile "SHA-256" -verbose "..\JuggleMasterPro.jar" "JuggleMasterProSignature"


:INDEXING
@title Indexing JuggleMasterPro.jar...
@echo.
@echo Indexing JuggleMasterPro.jar...
@cd ".."
@"C:\Program Files\Java\jdk\bin\jar.exe" -i "JuggleMasterPro.jar"
@cd ".\tools\"


:ENDING
@pause
@title Invite de Commandes
