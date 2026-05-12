# Decision Log

## [2026-05-12] Environment Baseline (Project 0, Task 1)

**Decision:**
Standardize on JDK 25, Kotlin 2.3, Android SDK 34, and Wear OS 4 (API 33) for the initial phase of the Mastery Path.

**Rationale:**
- JDK 25 is currently installed and exceeds the JDK 17 requirement for modern Android development.
- Kotlin 2.3 provides the latest stability and K2 compiler benefits.
- Android SDK 34 (UpsideDownCake) is the target for modern Jetpack Compose features.
- Wear OS 4 (API 33) ensures compatibility with the latest wearable UI guidelines and APIs.
- ADB is confirmed for Wear OS device/emulator bridging.

**Consequences:**
- All code must be compatible with Kotlin 2.x.
- Developer must manually install Android SDK 34 and Wear OS 4 components via the SDK Manager.
- Developer must manually enable K2 in Android Studio for consistent analysis.
