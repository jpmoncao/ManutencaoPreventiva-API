@echo off
cd /d "%~dp0"
echo Iniciando ManutControl API...
where mvn >nul 2>nul
if errorlevel 1 (
 echo Maven nao encontrado. Instale Maven e JDK 21 e abra este arquivo novamente.
 pause
 exit /b 1
)
mvn spring-boot:run
pause
