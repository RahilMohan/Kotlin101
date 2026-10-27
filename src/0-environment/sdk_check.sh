#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

pass() { echo -e "${GREEN}PASS:${NC} $1"; }
fail() { echo -e "${RED}FAIL:${NC} $1"; exit 1; }
warn() { echo -e "${YELLOW}WARN:${NC} $1"; }

echo "--- Kotlin & Wear OS Environment Check ---"

# Check Java
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
if [[ "$JAVA_VERSION" -ge 21 ]]; then
    pass "Java $JAVA_VERSION detected (Min: 21)"
else
    fail "Java $JAVA_VERSION detected (Min: 21 required)"
fi

# Check Kotlin
KOTLIN_VERSION=$(kotlinc -version 2>&1 | awk '{print $3}' | awk -F '-' '{print $1}')
KOTLIN_MAJOR=$(echo $KOTLIN_VERSION | awk -F '.' '{print $1}')
KOTLIN_MINOR=$(echo $KOTLIN_VERSION | awk -F '.' '{print $2}')
if [[ "$KOTLIN_MAJOR" -gt 2 ]] || [[ "$KOTLIN_MAJOR" -eq 2 && "$KOTLIN_MINOR" -ge 4 ]]; then
    pass "Kotlin $KOTLIN_VERSION detected (Min: 2.4)"
else
    fail "Kotlin $KOTLIN_VERSION detected (Min: 2.4 required)"
fi

# Check ADB
if command -v adb &> /dev/null; then
    ADB_V=$(adb version | head -n 1)
    pass "$ADB_V detected"
else
    fail "adb not found"
fi

# Check Android SDK
SDK_ROOT=${ANDROID_HOME:-$ANDROID_SDK_ROOT}
if [[ -z "$SDK_ROOT" ]]; then
    fail "ANDROID_HOME or ANDROID_SDK_ROOT is not set"
else
    pass "Android SDK found at $SDK_ROOT"
    
    # Check Platform 33
    if [[ -d "$SDK_ROOT/platforms/android-33" ]]; then
        pass "Platform android-33 found"
    else
        fail "Platform android-33 (Wear OS 4 requirement) not found in SDK"
    fi
    
    # Check Platform 34
    if [[ -d "$SDK_ROOT/platforms/android-34" ]]; then
        pass "Platform android-34 found"
    else
        fail "Platform android-34 not found in SDK"
    fi
fi

echo "--- Check Complete ---"

echo ""
echo "=== Manual Setup Checklist ==="
echo "Please confirm the following manual steps have been completed in Android Studio:"
echo "1. Enabled Kotlin K2 Mode (Settings -> Languages & Frameworks -> Kotlin)"
