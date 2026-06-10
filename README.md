# Kotlin & Wear OS Mastery Curriculum

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.21-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-9.5-blue.svg?style=flat&logo=gradle)](https://gradle.org)
[![JDK](https://img.shields.io/badge/JDK-25%2B-orange.svg?style=flat)](https://openjdk.org)
[![WearOS](https://img.shields.io/badge/Wear%20OS-4%20(API%2033)-green.svg?style=flat&logo=android)](https://developer.android.com/wear)

Welcome to **Kotlin101**, a progressive 16-project curriculum designed to take engineers with prior JavaScript/Python experience to advanced Kotlin, Jetpack Compose, and Wear OS development.

---

## Table of Contents
1. [Core Features & Goals](#core-features--goals)
2. [Tech Stack & Tooling](#tech-stack--tooling)
3. [Curriculum Roadmap](#curriculum-roadmap)
4. [Getting Started](#getting-started)
   - [Environment Setup](#1-environment-setup)
   - [Running CLI Projects](#2-running-cli-projects)
   - [Running Android/Wear OS Projects](#3-running-android--wear-os-projects)
   - [Running Tests](#4-running-tests)
5. [Guiding Principles](#guiding-principles)
6. [Repository Structure](#repository-structure)
7. [License](#license)

---

## Core Features & Goals
- **Eliminate Type-System Friction:** Specifically designed to help developers from dynamically typed languages (JS/Python) master Kotlin’s static typing, null safety, and memory models.
- **TDD (Test-Driven Development) Focus:** Learn to write robust unit, integration, and UI tests before implementing business logic.
- **Declarative UI Mastery:** Zero legacy XML layouts. Every visual module is built using modern Jetpack Compose.
- **Specialized Wear OS Tracks:** Step-by-step guidance through low-level wearable APIs, including Canvas drawing, sensors, Wear OS Tiles, Data Client syncing, and Smart Home complications.
- **Interactive HTML Learning Guides:** Each project comes with its own locally generated interactive `learning-guide.html` covering essential concepts and debugger challenges.

---

## Tech Stack & Tooling

The curriculum utilizes a modern, optimized runtime stack:

### Language & Compiler
- **Kotlin 2.3.21** (Leveraging the high-performance **K2 Compiler**).
- **JDK 25** (or later) for JVM compatibility.

### Build System & Tooling
- **Gradle 9.5** with Kotlin DSL (`.gradle.kts`) for build script configuration.
- **Android Studio Ladybug+** (or newer) for Android and Wear OS development.
- **Android SDK 34 / Wear OS 4 (API 33)** for system integrations.

### Key Libraries & Frameworks
- **Asynchronous Flow:** `kotlinx.coroutines:1.10.1` and `kotlinx-coroutines-core`.
- **Reactive UI:** `Compose Multiplatform:1.7.3` and Android Jetpack Compose.
- **Networking:** `Ktor:3.1.1` client and engine.
- **Serialization:** `kotlinx.serialization:1.8.0` for JSON/binary schemas.
- **CLI Printing:** `Mordant` for rich terminal UI output.

---

## Curriculum Roadmap

The course is divided into three progressive phases:

### Phase 1: Kotlin CLI Fundamentals (Projects 0–5)
Focuses on pure Kotlin syntax, object-oriented concepts, functional programming, reflection, generics, and concurrent programming.

| Module | Project | Key Concepts | Directory |
| :--- | :--- | :--- | :--- |
| **0** | [Pro Environment Setup](src/0-environment) | ADB checks, JVM/Kotlin SDK check, Gradle setup | [`src/0-environment`](src/0-environment) |
| **1** | [Smart Terminal Calculator](src/1-calculator) | Variables, expression parsing, RPN evaluation, basic I/O | [`src/1-calculator`](src/1-calculator) |
| **2** | [Inventory CLI](src/2-inventory) | Control flow, functions, null safety, OOP paradigms | [`src/2-inventory`](src/2-inventory) |
| **3** | [Schema Generator Integration](src/3-schema) | Reflection, annotations, generics, custom serializers | [`src/3-schema`](src/3-schema) |
| **4** | [Async Downloader](src/4-downloader) | Coroutines, Flow, reactive progress tracking | [`src/4-downloader`](src/4-downloader) |
| **5** | [Log Parser](src/5-logparser) | Regular expressions, string parsing, memory-efficient streams | [`src/5-logparser`](src/5-logparser) |

### Phase 2: Android Jetpack Compose Mobile (Projects 6–10)
Transitions into mobile app engineering using Google's modern declarative UI framework, introducing state management and architecture boundaries.

| Module | Project | Key Concepts | Directory |
| :--- | :--- | :--- | :--- |
| **6** | [Vibe Counter](src/6-vibe-counter) | Jetpack Compose basic state, state hoisting, layout structures | [`src/6-vibe-counter`](src/6-vibe-counter) |
| **7** | [Weather App](src/7-weather) | REST API requests (Ktor), JSON parsing, UI state mapping | [`src/7-weather`](src/7-weather) |
| **8** | [Notes App](src/8-notes) | Local persistent storage, lists, simple single-activity navigation | [`src/8-notes`](src/8-notes) |
| **9** | [Chat App](src/9-chat) | WebSocket client connections, reactive UI flows, input handling | [`src/9-chat`](src/9-chat) |
| **10** | [Movie App](src/10-movies) | Clean Architecture (Domain/Data/UI), Compose Multiplatform | [`src/10-movies`](src/10-movies) |

### Phase 3: Wear OS Specialization (Projects 11–15)
Focuses on wearable form factors, covering Canvas rendering, hardware sensors, system integrations, and device communication.

| Module | Project | Key Concepts | Directory |
| :--- | :--- | :--- | :--- |
| **11** | [Watchface](src/11-watchface) | Canvas Watch Face Service, Ambient (Always-on) mode, custom drawings | [`src/11-watchface`](src/11-watchface) |
| **12** | [Health Tracker](src/12-health) | Wear OS Health Services, step counter, heart rate sensors | [`src/12-health`](src/12-health) |
| **13** | [Workout Tile](src/13-tile) | Custom layout Tiles, periodic background updates, glances | [`src/13-tile`](src/13-tile) |
| **14** | [Sync Manager](src/14-sync) | Wearable Data Layer API (Data Client, Message Client, Node Client) | [`src/14-sync`](src/14-sync) |
| **15** | [Smart Home Complication](src/15-complication) | Complication Provider Service, update cycles, smart home widgets | [`src/15-complication`](src/15-complication) |

---

## Getting Started

### 1. Environment Setup
Verify that your system meets the hardware and software prerequisites by running the automated check script:
```bash
chmod +x src/0-environment/sdk_check.sh
./src/0-environment/sdk_check.sh
```
Make sure to also enable Kotlin K2 Mode in Android Studio under **Settings -> Languages & Frameworks -> Kotlin**.

### 2. Running CLI Projects
For Phase 1 projects (0 to 5), you can build and run them directly from the command line. Navigate to the project's subdirectory and use Gradle:
```bash
cd src/1-calculator
./gradlew run
```

### 3. Running Android & Wear OS Projects
For Phase 2 and 3 projects (6 to 15):
1. Launch **Android Studio**.
2. Open the project folder (e.g. `src/10-movies` or `src/11-watchface`).
3. Set up an Android Virtual Device (AVD) or Wear OS Emulator in the device manager.
4. Click **Run** (`Ctrl+R` / `Cmd+R`).

### 4. Running Tests
The curriculum enforces a **TDD-First** approach. To execute unit and integration tests for a project, run the following Gradle task:
```bash
cd src/1-calculator
./gradlew test
```

---

## Guiding Principles

- **TDD (Test-Driven Development) first:** Always run/inspect the existing test cases in the `src/<project>/src/test` directory before making logic modifications.
- **Clean Architecture:** From Project 10 onward, maintain strict boundaries between layers:
  - **Domain:** Pure Kotlin models, UseCases, and Repository interfaces.
  - **Data:** Repository implementations, network requests (Ktor), and local databases.
  - **UI:** ViewModels, Compose screens, and State wrappers.
- **Aesthetics & UX:** UI modules should have rich modern aesthetics (glassmorphism, tailored HSL color palettes, responsive layouts).
- **Self-contained Learning:** Open `learning-guide.html` in your web browser inside any project folder to read detailed concept breakdowns and debugging exercises.

---

## Repository Structure

```
.
├── LICENSE
├── README.md                 # This guide
├── docs/                     # Course administration & design records
│   ├── architecture.md       # Architecture guidelines
│   ├── current_state.md      # Active repository state and runtime variables
│   ├── decision_log.md       # Chronological ADR-style logs
│   ├── handoff.md            # Actionable status records between sessions
│   ├── project_brief.md      # Curriculum constraints & user profiles
│   └── setup_guide.md        # Detailed IDE configurations
├── src/                      # Source projects (0 to 15)
│   ├── 0-environment/
│   ├── 1-calculator/
│   │   ├── src/              # App source code and tests
│   │   ├── build.gradle.kts  # Gradle configuration
│   │   └── learning-guide.html # Interactive HTML guide
│   └── ...
└── tests/                    # Shared testing utilities and resources
```

---

## License

This project is open-source software licensed under the **Apache License 2.0**. See the [LICENSE](LICENSE) file for details.
