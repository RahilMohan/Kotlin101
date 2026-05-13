# Project 1: Mordant RPN Calculator Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a functional RPN calculator with a beautiful Mordant TUI.

**Architecture:** Separation of `RpnCalculator` logic and CLI rendering.

**Tech Stack:** Kotlin 2.3, Gradle, Mordant.

---

### Task 1: Dependencies & Mordant Hello World

**Files:**
- Modify: `src/1-calculator/build.gradle.kts`
- Modify: `src/1-calculator/src/main/kotlin/Main.kt`

- [ ] **Step 1: Add Mordant Dependency**
Update `build.gradle.kts` to include `implementation("com.github.ajalt.mordant:mordant:2.7.2")`.

- [ ] **Step 2: Simple Mordant Rendering**
Update `Main.kt` to print a styled "Calculator Ready" message using Mordant's `Terminal`.

- [ ] **Step 3: Commit**
`git commit -m "feat(calculator): add mordant dependency and init terminal"`

### Task 2: Calculator Core & Logic Tests

**Files:**
- Create: `src/1-calculator/src/main/kotlin/RpnCalculator.kt`
- Create: `src/1-calculator/src/test/kotlin/RpnCalculatorTest.kt`

- [ ] **Step 1: Implement RpnCalculator class**
Create the class using `ArrayDeque<Double>`. Implement `push(number)`, `pop()`, and `operate(symbol: String)`.
- [ ] **Step 2: Write Unit Tests**
Test basic math and edge cases (empty stack).
- [ ] **Step 3: Commit**
`git commit -m "feat(calculator): implement core RPN logic with tests"`

### Task 3: Interactive Loop & Table UI

**Files:**
- Modify: `src/1-calculator/src/main/kotlin/Main.kt`

- [ ] **Step 1: Build the Input Loop**
Handle `q` to quit, `c` to clear, and operators.
- [ ] **Step 2: Style with Mordant Tables**
Render the stack in a beautiful table after every input.
- [ ] **Step 3: Commit**
`git commit -m "feat(calculator): complete interactive CLI with table UI"`
