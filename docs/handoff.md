## Task
Project 1: Mordant RPN Calculator

## Current status
DONE

## What changed
- `src/1-calculator/src/main/kotlin/RpnCalculator.kt`: Core logic with stack recovery.
- `src/1-calculator/src/test/kotlin/RpnCalculatorTest.kt`: Unit tests for math logic.
- `src/1-calculator/src/main/kotlin/Main.kt`: Interactive TUI loop with Mordant tables.
- `src/1-calculator/build.gradle.kts`: Added Mordant dependency.

## Decisions made
- Used **ArrayDeque** for the stack to learn about mutable Kotlin collections.
- Used **Mordant** instead of Mosaic to keep the focus on pure Kotlin fundamentals while maintaining a 'Charm Bracelet' aesthetic.
- Implemented **Stack Recovery** (restoring numbers on error) to demonstrate robust logic design.

## Open questions
- None.

## Risks
- None.

## Next recommended step
**Project 2: Inventory CLI.** Focus on **Data Classes** and **Advanced Null Safety** (Moving beyond simple `toDoubleOrNull`).

## Files to inspect next
- `src/1-calculator/src/main/kotlin/RpnCalculator.kt`
