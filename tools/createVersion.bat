@echo off

rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------

echo.
echo createVersion M [m [p] ]
echo.
echo    M : Major version digit
echo    m : minor version digit
echo    p : patch version digit
echo.

set "INIT_FOLDER=%CD%"
cd ..
set "ROOT_FOLDER=%CD%"

rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------

:MAJOR
if "NO%1"=="NO" (
   pause >nul 2>nul
   goto FIN
)
set "MAJOR=%1"
set "MAJOR=%MAJOR:~,1%"
set "YEAR=%DATE:~6,4%"

:MINOR
if "NO%2"=="NO" (
   set "MINOR=0"
) else (
   set "MINOR=%2"
)
set "MINOR=%MINOR:~,1%"

:PATCH
if "NO%3"=="NO" (
   set "PATCH=0"
) else (
   set "PATCH=%3"
)
set "PATCH=%PATCH:~,1%"

rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------

title Versioning files...
@echo.
@echo Versioning files :

rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------

:TRAITEMENT_DEVELOPMENT

@echo.
for /R "%ROOT_FOLDER%\data\" %%p in (.) do (
   cd "%%p"
   for %%f in (development.cfg) do (
      if exist "%%f" (
         @echo - "%%p\%%f" :
         "%ROOT_FOLDER%\tools\compilation\sed.exe" -r -s --text -u "s/(JuggleMaster Pro )([0-9a-zA-Z][0-9a-zA-Z])\.([0-9a-zA-Z][0-9a-zA-Z])\.([0-9a-zA-Z][0-9a-zA-Z])/\1%MAJOR%.%MINOR%.%PATCH%/g" "%%f" >"%%fTmp"
      
         @del /F /Q "%%f"
         if exist "%%f" (
            @echo Unable to delete file "%%f"...
            @echo Unlock it !
            @pause
         )
         @del /F /Q "%%f"
         if exist "%%f" (
            @echo Unable to delete file "%%f" !
            @echo Delete it absolutely !!!
            @pause
         )
         @ren "%%fTmp" "%%f"
      )
   )
)
@cd "%INIT_FOLDER%"

rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------

:TRAITEMENT_CONSTANTS
@echo.
@echo - "%ROOT_FOLDER%\src\jugglemasterpro\util\Constants.java"

"%ROOT_FOLDER%\tools\compilation\sed.exe" -r -s --text -u -e "s/(COPYRIGHT_YEAR[ \t]*=[ \t]*)([0-9][0-9][0-9][0-9]);/\1%YEAR%;/gi" -e "s/(lngG_ENGINE_VERSION_NUMBER[ \t]*=[ \t]*)[0-9a-zA-Z][0-9a-zA-Z][0-9a-zA-Z]/\1%MAJOR%%MINOR%%PATCH%/gi" "%ROOT_FOLDER%\src\jugglemasterpro\util\Constants.java" >"%ROOT_FOLDER%\src\jugglemasterpro\util\Constants.javaTmp"

:TEST_CONSTANTS
@del /F /Q "%ROOT_FOLDER%\src\jugglemasterpro\util\Constants.java"
if exist "%ROOT_FOLDER%\src\jugglemasterpro\util\Constants.java" (
   @echo Unable to delete file "%ROOT_FOLDER%\src\jugglemasterpro\util\Constants.java"...
   @echo Unlock it !
   @pause
   goto TEST_CONSTANTS
)

@ren "%ROOT_FOLDER%\src\jugglemasterpro\util\Constants.javaTmp" "*.java" >nul 2>nul

rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------

:TRAITEMENT_JAVA

@echo.
@echo - "*.java" :
for /R "%ROOT_FOLDER%\src\jugglemasterpro\" %%f in (*.java) do (
   @echo    - "%%f"

   "%ROOT_FOLDER%\tools\compilation\sed.exe" -r -s --text -u -e "s/^( *\*  *\@\(\#\).*.java  *)([0-9a-zA-Z])\.([0-9a-zA-Z])\.([0-9a-zA-Z])$/\1%MAJOR%.%MINOR%.%PATCH%/gi" -e "s/^( *\*  *Copyleft  *\(c\) *)([0-9][0-9][0-9][0-9])( *Arnaud BeLO.)$/\1%YEAR%\3/gi" -e "s/^([ \t]*\* .version *)([0-9a-zA-Z])\.([0-9a-zA-Z])\.([0-9a-zA-Z])$/\1%MAJOR%.%MINOR%.%PATCH%/gi" "%%f" >"%%fTmp"

   del /F /Q "%%f" >nul 2>nul
   if exist "%%f" (
      @echo Unable to delete file "%%f"...
      @echo Unlock it !
      @pause
   )
   @del /F /Q "%%f" >nul 2>nul
   if exist "%%f" (
      @echo Unable to delete file "%%f" !!!
      @echo Delete it absolutely !!!
      @pause
   )
)

for /R "%ROOT_FOLDER%\src\jugglemasterpro\" %%f in (*.javaTmp) do (
   @ren "%%f" "*.java"
)

rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------

:TRAITEMENT_MANIFEST
@echo.
@echo - "%ROOT_FOLDER%\tools\compilation\JuggleMasterPro.mf"

"%ROOT_FOLDER%\tools\compilation\sed.exe" -r -s --text -u -e "s/(Specification-Version:.)([0-9a-zA-Z]\.[0-9a-zA-Z]\.[0-9a-zA-Z])$/\1%MAJOR%.%MINOR%.%PATCH%/gi" -e "s/(Implementation-Version:.)([0-9a-zA-Z]\.[0-9a-zA-Z]\.[0-9a-zA-Z])$/\1%MAJOR%.%MINOR%.%PATCH%/gi" -e "s/(Signature-Version:.)([0-9a-zA-Z]\.[0-9a-zA-Z]\.[0-9a-zA-Z])$/\1%MAJOR%.%MINOR%.%PATCH%/gi" "%ROOT_FOLDER%\tools\compilation\JuggleMasterPro.mf" >"%ROOT_FOLDER%\tools\compilation\JuggleMasterPro.mfTmp"

:TEST_MANIFEST
@del /F /Q "%ROOT_FOLDER%\tools\compilation\JuggleMasterPro.mf" >nul 2>nul
if exist "%ROOT_FOLDER%\tools\compilation\JuggleMasterPro.mf" (
   @echo Unable to delete file "%ROOT_FOLDER%\tools\compilation\JuggleMasterPro.mf"...
   @echo Unlock it !
   @pause
   goto TEST_MANIFEST
)

@ren       "%ROOT_FOLDER%\tools\compilation\JuggleMasterPro.mfTmp" "*.mf"

rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------
rem ------------------------------------------------------------------------------------------------

:FIN
@echo JuggleMaster Pro %MAJOR%.%MINOR%.%PATCH% >%ROOT_FOLDER%\version.txt
@cd %INIT_FOLDER%
@echo.
@title Invite de Commandes
