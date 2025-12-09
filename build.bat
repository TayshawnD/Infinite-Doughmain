@echo off
echo Building Pizza Ordering System...
echo.

echo Compiling Java files...
javac *.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo Creating JAR file...
jar cvfe PizzaOrderingSystem.jar PizzaOrderingSystem *.class

if %ERRORLEVEL% NEQ 0 (
    echo JAR creation failed!
    pause
    exit /b 1
)

echo.
echo Build successful!
echo Run with: java -jar PizzaOrderingSystem.jar
echo.
pause

