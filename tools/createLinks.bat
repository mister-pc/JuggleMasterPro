REM @echo off
@cls
@cd ..
@set ROOT_FOLDER=%CD%
echo.
echo Creating file %ROOT_FOLDER%\scripts\menus\links.php... 
@"%ROOT_FOLDER%\tools\compilation\awk.exe" -f "%ROOT_FOLDER%\tools\compilation\createLinks.awk" -v strPdataDirectory="/applet/data/fr/" -v strPimagesDirectory="/images/" "%ROOT_FOLDER%\applet\data\fr\linksMenu.cfg" >"%ROOT_FOLDER%\scripts\menus\links.php"
echo File %ROOT_FOLDER%\scripts\menus\links.php created. 
@cd ".\tools"
pause>nul
