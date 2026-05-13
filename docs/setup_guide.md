# Setup Guide: Kotlin & Wear OS Mastery

## 1. Automated Environment Check
Run the verification script to ensure your core toolchain is ready:
```bash
./src/0-environment/sdk_check.sh
```

## 2. Manual Android Studio Configuration
The following steps must be performed manually in Android Studio Ladybug+:

### Enable Kotlin K2 Compiler
- Go to `Settings` (or `Android Studio > Settings` on macOS).
- Navigate to `Languages & Frameworks > Kotlin`.
- Check the box for **Enable K2 Kotlin Mode**.
- Restart Android Studio if prompted.

### SDK Manager Installations
- Open the **SDK Manager** (Settings > Appearance & Behavior > System Settings > Android SDK).
- **SDK Platforms:**
    - Ensure `Android 14.0 ("UpsideDownCake")` (API 34) is installed.
    - Ensure `Android 13.0 ("Tiramisu")` (API 33) is installed (required for Wear OS 4).
- **SDK Tools:**
    - Ensure `Android SDK Build-Tools 34` or higher is installed.
    - Ensure `Android SDK Command-line Tools (latest)` is installed.

## 3. Wear OS Debugging
- Enable **Developer Options** on your watch (Settings > System > About > Versions > Tap 'Build number' 7 times).
- Go to `Settings > Developer options`.
- Enable **ADB Debugging** and **Debug over Wi-Fi**.
- Use ADB to connect: `adb connect <watch_ip>:5555`.
