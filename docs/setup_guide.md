# Environment Setup Guide

This guide outlines the manual steps required to prepare the development environment for the Kotlin & Wear OS Mastery curriculum.

## 1. JDK and Kotlin
Ensure you have the following versions installed:
- **JDK:** 25+ (Exceeds the JDK 17 minimum requirement)
- **Kotlin:** 2.3+

## 2. Android Studio Configuration
We recommend using **Android Studio Ladybug** or newer.

### Enable Kotlin K2 Compiler
For optimal performance and to align with our codebase standards:
1. Open Android Studio **Settings** (or **Preferences** on macOS).
2. Navigate to **Languages & Frameworks** > **Kotlin**.
3. Enable the **K2 Mode** checkbox.
4. Restart Android Studio if prompted.

## 3. SDK Manager Installations
Open the Android Studio **SDK Manager** and ensure the following are installed:

### SDK Platforms
- **Android 14.0 ("UpsideDownCake")** (API Level 34)
- **Android 13.0 ("Tiramisu")** (API Level 33) - required for Wear OS 4

### SDK Tools
- **Android SDK Build-Tools 34+**
- **Android SDK Platform-Tools**
- **Android Emulator**

### Wear OS System Images
- **Wear OS 4** (under Android 13.0 / API 33)
  - `Wear OS 4 Large Image`
  - `Wear OS 4 Small Image`

## 4. Verification
Run the following script to verify your environment:
```bash
bash src/0-environment/sdk_check.sh
```
