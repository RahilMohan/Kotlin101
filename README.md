# Kotlin101

A progressive 16-project curriculum for mastering Kotlin and Wear OS development.
Designed for engineering students with JavaScript/Python experience who want to
learn Kotlin from the ground up — from CLI fundamentals to Wear OS apps.

## Prerequisites

- **JDK 25** (or later)
- **Kotlin 2.3.21**
- **Gradle 9.5**
- **Android Studio Ladybug+** (required for Projects 6–15)
- **Android SDK 34 / Wear OS 4 (API 33)** (Projects 6+)

## Curriculum

| Project | Topic |
|---------|-------|
| 0 | Environment Setup |
| 1 | Calculator — variables, expressions, basic I/O |
| 2–5 | Core Kotlin: control flow, functions, null safety, collections, OOP |
| 6–10 | Android fundamentals with Jetpack Compose |
| 11–15 | Wear OS specialization (sensors, Tiles, Complications) |

## Getting Started

```bash
# Build and run the current project (calculator)
cd src/1-calculator
./gradlew run
```

## Guiding Principles

- **TDD First** — tests before implementation
- **Clean Architecture** — Domain / Data / UI separation from Project 10 onward
- **Jetpack Compose Only** — no XML layouts
- **K2 Compiler** — modern Kotlin compiler enabled

## Project Structure

```
docs/               — briefs, architecture, decision log, handoff docs
src/
  0-environment/    — SDK verification, debugger guides
  1-calculator/     — first Kotlin project (Gradle, JVM)
  ...
tests/              — shared test resources
```

## License

Apache 2.0 — see [LICENSE](LICENSE).
