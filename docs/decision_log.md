# Decision Log

## [2026-05-12] Environment Baseline (Project 0, Task 1)

**Decision:**
Standardize on JDK 25 and Kotlin 2.3 for the initial phase of the Mastery Path.

**Rationale:**
- JDK 25 is currently installed and exceeds the JDK 17 requirement for modern Android development.
- Kotlin 2.3 provides the latest stability and K2 compiler benefits.
- ADB is confirmed for Wear OS device/emulator bridging.

**Consequences:**
- All code must be compatible with Kotlin 2.x.
- Developer must manually enable K2 in Android Studio for consistent analysis.
