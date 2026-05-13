## Task
Project 0: Pro Environment Setup

## Current status
DONE

## What changed
- `src/0-environment/sdk_check.sh`: New automated verification script.
- `src/0-environment/debugger_shortcuts.md`: New guide for AS debugger and Wear OS pairing.
- `docs/setup_guide.md`: New persistent manual setup instructions.
- `docs/decision_log.md`: Standardized on JDK 25 / Kotlin 2.3.
- `src/1-calculator/`: Initial project scaffold with Gradle.
- `.gitignore`: Configured for Kotlin/Android.

## Decisions made
- Standardized on **JDK 25** and **Kotlin 2.3** to leverage K2 compiler and latest stability.
- Mandated **Android SDK 34** and **Wear OS 4 (API 33)** for wearable proficiency.

## Open questions
- None.

## Risks
- Ensure the user manually enables **K2 Mode** in Android Studio as it cannot be automated via script.

## Next recommended step
**Project 1: Smart Terminal Calculator.** Focus on core Kotlin syntax (variables, null safety, and control flow).

## Files to inspect next
- `src/1-calculator/src/main/kotlin/Main.kt`
