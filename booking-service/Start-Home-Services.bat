@echo off
setlocal EnableExtensions
title Home Services — start microservices

REM =============================================================================
REM Double-click this file to:
REM   1) Open User Service   (Spring Boot) on port 8081
REM   2) Open Offer Service   (Spring Boot) on port 8082
REM   3) Build booking-service WAR and optionally deploy to WildFly
REM
REM Booking-service is a Jakarta EE WAR — it needs WildFly / Payara / GlassFish.
REM Optional: set WILDFLY_HOME to your WildFly folder, e.g.:
REM   set WILDFLY_HOME=C:\wildfly-35.0.0.Final
REM Then this script will copy the WAR to standalone\deployments\
REM (start WildFly yourself first, or run bin\standalone.bat in another window).
REM =============================================================================

set "BOOKING_DIR=%~dp0"
set "PLATFORM_ROOT=%BOOKING_DIR%.."

echo.
echo [1/3] Starting User Service on http://localhost:8081 ...
start "User Service :8081" cmd /k "cd /d \"%PLATFORM_ROOT%\user-service\" && gradlew.bat bootRun"

timeout /t 2 /nobreak >nul

echo [2/3] Starting Offer Service on http://localhost:8082 ...
start "Offer Service :8082" cmd /k "cd /d \"%PLATFORM_ROOT%\offer-service\" && gradlew.bat bootRun"

echo [3/3] Building booking-service WAR ...
cd /d "%BOOKING_DIR%"
call mvnw.cmd package -DskipTests
if errorlevel 1 (
    echo.
    echo Maven build failed. Fix errors above, then run this script again.
    pause
    exit /b 1
)

set "WAR_FILE=%BOOKING_DIR%target\booking-service-1.0-SNAPSHOT.war"
if not exist "%WAR_FILE%" (
    echo Expected WAR not found: "%WAR_FILE%"
    pause
    exit /b 1
)

if defined WILDFLY_HOME (
    echo.
    echo WILDFLY_HOME is set — copying WAR to WildFly deployments ...
    if not exist "%WILDFLY_HOME%\standalone\deployments" (
        echo Folder not found: "%WILDFLY_HOME%\standalone\deployments"
        echo Check WILDFLY_HOME path.
    ) else (
        copy /Y "%WAR_FILE%" "%WILDFLY_HOME%\standalone\deployments\booking-service.war"
        echo Deployed as booking-service.war ^(hot-deploy if WildFly is already running^).
    )
) else (
    echo.
    echo WILDFLY_HOME is not set — deploy manually:
    echo   %WAR_FILE%
    echo Install WildFly, start standalone.bat, then copy the WAR into standalone\deployments\
)

echo.
echo Spring services are running in separate windows. Close those windows to stop them.
echo.
pause
endlocal
