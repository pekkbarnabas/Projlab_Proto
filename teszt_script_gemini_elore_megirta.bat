@echo off
setlocal enabledelayedexpansion

echo =========================================
echo Szoftvercraft - Automatikus Tesztfuttato
echo =========================================

:: Mappak ellenorzese es letrehozasa
if not exist "tests\outputs" mkdir "tests\outputs"
if not exist "tests\logs" mkdir "tests\logs"

:: Log fajl elokeszitese
set LOGFILE=tests\logs\summary.txt
echo Tesztelesi jelentes - %date% %time% > %LOGFILE%
echo ------------------------------------------------ >> %LOGFILE%

set PASS_COUNT=0
set TOTAL_COUNT=0

:: Iteralas az osszes in_*.txt fajlon az inputs mappaban
for %%f in (tests\inputs\in_*.txt) do (
    set /a TOTAL_COUNT+=1
    
    :: Fajlnev kinyerese (pl. in_T01) es a teszt azonositojanak (T01) levagasa
    set "filename=%%~nf"
    set "testid=!filename:in_=!"
    
    set "input=%%f"
    set "expected=tests\expected\exp_!testid!.txt"
    set "output=tests\outputs\out_!testid!.txt"
    
    echo.
    echo Futtatas: !testid! teszteset...
    
    :: Java program futtatasa a bemeneti fajl atiranyitasaval
    :: (Feltetelezzuk, hogy a leforditott Main.class a gyokermappaban van)
    java Main < "!input!" > NUL 2>&1
    
    :: Kimeneti es elvart fajl osszehasonlitasa az 'fc' (File Compare) paranccsal
    fc "!output!" "!expected!" > NUL 2>&1
    
    :: Hibaellenorzes
    if !errorlevel! equ 0 (
        echo [PASS] !testid! - A kimenet tokeletesen megegyezik.
        echo [PASS] !testid! >> %LOGFILE%
        set /a PASS_COUNT+=1
    ) else (
        echo [FAIL] !testid! - ELTERES!
        echo [FAIL] !testid! >> %LOGFILE%
        :: Elteresek kimentese a log fajlba
        echo --- Elteresek kezdete (!testid!) --- >> %LOGFILE%
        fc "!output!" "!expected!" >> %LOGFILE%
        echo --- Elteresek vege --- >> %LOGFILE%
        echo. >> %LOGFILE%
    )
)

:: Vegeredmeny kiirasa
echo.
echo =========================================
echo Teszteles befejezve!
echo Sikeres tesztek: !PASS_COUNT! / !TOTAL_COUNT!
echo A reszletes eredmenyeket a tests\logs\summary.txt fajlban talalod.
echo =========================================
echo Eredmeny: !PASS_COUNT! / !TOTAL_COUNT! sikeres >> %LOGFILE%

pause