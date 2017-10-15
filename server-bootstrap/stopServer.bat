
REM Import variable definitions
call setEnv.bat

REM Issue commands to gfsh to start locator and launch a server
echo Stopping server and locator...
gfsh run --file=serverStop.gf

