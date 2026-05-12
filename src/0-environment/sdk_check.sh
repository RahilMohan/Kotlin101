#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo "--- Kotlin & Wear OS Environment Check ---"

# Check Java
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
if [[ "$JAVA_VERSION" -ge 17 ]]; then
    echo -e "${GREEN}PASS:${NC} Java $JAVA_VERSION detected (Min: 17)"
else
    echo -e "${RED}FAIL:${NC} Java $JAVA_VERSION detected (Min: 17 required)"
    exit 1
fi

# Check Kotlin
KOTLIN_VERSION=$(kotlinc -version 2>&1 | awk '{print $3}' | awk -F '-' '{print $1}')
KOTLIN_MAJOR=$(echo $KOTLIN_VERSION | awk -F '.' '{print $1}')
if [[ "$KOTLIN_MAJOR" -ge 2 ]]; then
    echo -e "${GREEN}PASS:${NC} Kotlin $KOTLIN_VERSION detected (Min: 2.0)"
else
    echo -e "${RED}FAIL:${NC} Kotlin $KOTLIN_VERSION detected (Min: 2.0 required)"
    exit 1
fi

# Check ADB
if command -v adb &> /dev/null; then
    ADB_V=$(adb version | head -n 1)
    echo -e "${GREEN}PASS:${NC} $ADB_V detected"
else
    echo -e "${RED}FAIL:${NC} adb not found"
    exit 1
fi

echo "--- Check Complete ---"
