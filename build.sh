#!/bin/bash

echo "Building Pizza Ordering System..."
echo ""

echo "Compiling Java files..."
javac *.java

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo ""
echo "Creating JAR file..."
jar cvfe PizzaOrderingSystem.jar PizzaOrderingSystem *.class

if [ $? -ne 0 ]; then
    echo "JAR creation failed!"
    exit 1
fi

echo ""
echo "Build successful!"
echo "Run with: java -jar PizzaOrderingSystem.jar"

