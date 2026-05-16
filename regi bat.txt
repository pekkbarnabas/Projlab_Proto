@echo off
setlocal enabledelayedexpansion

echo =========================================
echo Szoftvercraft - Automatikus Tesztfuttato
echo =========================================

REM --- UJ RESZ: Automatikus Java forditas (Compilation) ---
echo Java fajlok forditasa...
if not exist "bin" mkdir bin
javac -encoding UTF-8 -d bin src\*.java
if %errorlevel% neq 0 (
    echo.
    echo [KRITIKUS HIBA] Nem sikerult leforditani a Java kodot! 
    echo Kerlek ellenorizd a szintaktikai hibakat a Java fajlokban.
    pause
    exit /b
)
echo Forditas sikeres! Tesztek inditasa...
echo -----------------------------------------
REM --------------------------------------------------------

REM Mappak ellenorzese
if not exist "tests\outputs" mkdir "tests\outputs"
if not exist "tests\logs" mkdir "tests\logs"

REM Log fajl elokeszitese
set LOGFILE=tests\logs\summary.txt
echo Tesztelesi jelentes - %date% %time% > %LOGFILE%
echo ------------------------------------------------ >> %LOGFILE%

set PASS_COUNT=0
set TOTAL_COUNT=0

for %%f in (tests\inputs\in_*.txt) do (
    set /a TOTAL_COUNT+=1
    
    set "filename=%%~nf"
    set "testid=!filename:in_=!"
    
    set "input=%%f"
    set "expected=tests\expected\exp_!testid!.txt"
    set "output_state=tests\outputs\out_!testid!.txt"
    set "output_console=tests\outputs\console_!testid!.txt"
    
    echo Futtatas: [!testid!] teszteset...
    
    REM Java program futtatasa
    java -cp bin Main < "!input!" > "!output_console!" 2>&1
    
    REM Fájl áthelyezése
    if exist "out_!testid!.txt" (
        move "out_!testid!.txt" "!output_state!" > NUL
    )
    
    REM --- HIBRID KIERTÉKELÉS (Szűrővel) ---
    if exist "!output_state!" (
        set "file_to_compare=!output_state!"
    ) else (
        findstr /v /c:"> OK" "!output_console!" > "tests\outputs\filtered_!testid!.txt"
        set "file_to_compare=tests\outputs\filtered_!testid!.txt"
    )
    
    REM Összehasonlítás
    fc "!file_to_compare!" "!expected!" > NUL 2>&1
    
    if !errorlevel! equ 0 (
        echo   -^> [PASS]
        echo [PASS] !testid! >> %LOGFILE%
        set /a PASS_COUNT+=1
    ) else (
        echo   -^> [FAIL] Elteres talalhato!
        echo [FAIL] !testid! >> %LOGFILE%
        echo --- Elteresek kezdete [!testid!] --- >> %LOGFILE%
        fc "!file_to_compare!" "!expected!" >> %LOGFILE%
        echo --- Elteresek vege --- >> %LOGFILE%
        echo. >> %LOGFILE%
    )
)

echo.
echo =========================================
echo Teszteles befejezve!
echo Eredmeny: !PASS_COUNT! / !TOTAL_COUNT! sikeres
echo A reszletes logokat a tests\logs\summary.txt fajlban talalod.
echo =========================================
echo Eredmeny: !PASS_COUNT! / !TOTAL_COUNT! sikeres >> %LOGFILE%

pause