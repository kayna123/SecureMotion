@echo off
setlocal

REM Auto-detect javac from JDK installations if available
set "JAVAC_EXE=javac"
set "JAVA_EXE=java"

if exist "C:\Program Files\Java\jdk-26.0.1\bin\javac.exe" (
    set "JAVAC_EXE=C:\Program Files\Java\jdk-26.0.1\bin\javac.exe"
    set "JAVA_EXE=C:\Program Files\Java\jdk-26.0.1\bin\java.exe"
) else if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\javac.exe" (
        set "JAVAC_EXE=%JAVA_HOME%\bin\javac.exe"
        set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
    )
)

echo [1/2] Compiling Smart Car System...
if not exist "bin" mkdir bin
(for /r src %%i in (*.java) do @echo %%i) > sources.txt
"%JAVAC_EXE%" -d bin @sources.txt

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b %ERRORLEVEL%
)

echo [2/2] Launching Smart Car Simulator...
"%JAVA_EXE%" -cp bin com.smartcar.main.Main
pause
