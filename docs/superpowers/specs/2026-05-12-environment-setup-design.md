# Design Spec: Android Studio Performance & SDKs (Project 0, Task 1)

## Goal
Establish a high-performance development environment for Kotlin and Wear OS by verifying local tools and documenting Android Studio configurations.

## Requirements
- **JDK:** 17 or higher (Verified JDK 25 locally).
- **Kotlin Compiler:** 2.0 or higher (Verified Kotlin 2.3 locally).
- **Android SDK:** API 34+ (Mobile), API 33 (Wear OS 4).
- **Compiler:** K2 enabled for faster analysis and modern features.

## Architecture & Components

### 1. Environment Verification Script (`src/0-environment/sdk_check.sh`)
A Bash script to automate the validation of the local toolchain.
- **Logic:**
  - Check `java -version` and parse major version.
  - Check `kotlinc -version` and parse major version.
  - Check `adb version`.
  - Verify `$ANDROID_HOME` or `$PATH` for SDK components.
- **Feedback:** Clear pass/fail messages with color coding.

### 2. Decision Documentation (`docs/decision_log.md`)
Chronological log of environment choices.
- **Baseline:** JDK 25 and Kotlin 2.3 chosen as the initial stable environment.
- **Performance:** K2 compiler identified as a key requirement for the "Pro" workflow.

## Success Criteria
- `sdk_check.sh` runs and passes on the local machine.
- `decision_log.md` contains the ADR for the environment setup.
- All steps in the Task 1 checklist are marked complete.

## User Actions (Manual)
- Enable K2 Compiler in Android Studio Settings.
- Install specific SDK platforms via Android Studio SDK Manager.
