# Decision Log

## [2026-05-12] Environment Baseline (Project 0, Task 1)

**Decision:**
Standardize on JDK 25 and Kotlin 2.3 for the initial phase of the Mastery Path. Require Android SDK 34 and Wear OS 4 (API 33).

**Rationale:**
- JDK 25 is current and stable for modern development.
- Kotlin 2.3 provides the latest stability and K2 compiler benefits.
- Android 34 and API 33 are targets for Wear OS 4 proficiency.

**Consequences:**
- All code must be compatible with Kotlin 2.x.
- Developer must manually enable K2 in Android Studio for consistent analysis.
- Verification via `src/0-environment/sdk_check.sh` is mandatory for all contributors.
