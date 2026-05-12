# Project 0: Pro Environment Setup Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Configure Android Studio and the underlying toolchain for professional Kotlin and Wear OS development, including AI agent integration.

**Architecture:** Environment configuration and verification scripts.

**Tech Stack:** Android Studio Ladybug+, Kotlin 2.0+, ADB, Gemini for Android Studio.

---

### Task 1: Android Studio Performance & SDKs

**Files:**
- Create: `src/0-environment/sdk_check.sh`
- Modify: `docs/decision_log.md`

- [ ] **Step 1: Verify Java 17 and Kotlin Compiler**
Check local Java version and ensure it matches Android Studio's requirements.
Run: `java -version`
Expected: `openjdk version "17.0.x"` or higher.

- [ ] **Step 2: Install required SDKs**
Ensure Android SDK 34+, Wear OS 4 (API 33), and Build Tools are installed via AS SDK Manager.
*Action:* Open Android Studio > Settings > Languages & Frameworks > Android SDK.

- [ ] **Step 3: Enable K2 Compiler**
In Android Studio Settings > Languages & Frameworks > Kotlin, enable the K2 compiler for faster analysis.

- [ ] **Step 4: Commit**
```bash
git add docs/decision_log.md
git commit -m "env: initial sdk and compiler setup"
```

### Task 2: Debugger & AI Agent Integration

**Files:**
- Create: `src/0-environment/debugger_shortcuts.md`

- [ ] **Step 1: Configure Gemini in Android Studio**
Enable Gemini for Android Studio (View > Tool Windows > Gemini). Log in and provide project context for better suggestions.

- [ ] **Step 2: Map Debugger Hotkeys**
Document essential debugger shortcuts (Step Over, Step Into, Resume, Evaluate Expression) to ensure consistent usage.

- [ ] **Step 3: Set up ADB over Wi-Fi**
Configure ADB to connect to your Wear OS device/emulator via Wi-Fi for untethered development.
Run: `adb pair <ip_address>:<port>` (for physical watches).

### Task 3: Verification & Initial Scaffold

**Files:**
- Create: `src/1-calculator/build.gradle.kts`
- Create: `src/1-calculator/src/main/kotlin/Main.kt`

- [ ] **Step 1: Create Minimal Kotlin Project**
Scaffold a tiny CLI project to verify that `kotlinc` or Gradle can build a "Hello World" successfully.

- [ ] **Step 2: Run Verification Build**
Run: `./gradlew run` (once Gradle is set up).
Expected: `Hello Kotlin` in the terminal.

- [ ] **Step 3: Commit**
```bash
git add src/1-calculator/
git commit -m "env: environment verified with hello world"
```
