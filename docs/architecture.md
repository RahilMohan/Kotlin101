# Architecture: Kotlin Mastery Curriculum

## System Shape
The repository is organized by "Projects" within the `src/` directory. Each project represents a discrete learning milestone.

## Module Boundaries
- `src/0-environment`: Configuration scripts and setup logs.
- `src/1-5-core-kotlin`: Pure Kotlin CLI tools.
- `src/6-10-android-fundamentals`: Jetpack Compose mobile apps.
- `src/11-15-wearos-specialization`: Wear OS specific services and components.

## Data Flow
- **CLI Projects:** Input/Output via terminal.
- **Android Projects:** Unidirectional Data Flow (UDF) using ViewModels and State.
- **Wear OS Projects:** Event-driven via Sensors and System Providers (Tiles/Complications).

## Design Decisions
1. **TDD First:** Every project must have unit or instrumented tests before implementation.
2. **Clean Architecture:** Use Domain/Data/UI separation once we reach Project 10.
3. **Jetpack Compose Only:** No XML layouts; exclusively use modern declarative UI.
