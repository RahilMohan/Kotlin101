# Project 5: Multi-threaded Log Parser Implementation Plan

**Goal:** Build a log parser to master Flow and Channels.

**Tech Stack:** Kotlin 2.3, `kotlinx-coroutines-core`.

---

### Task 1: Reactive Pipeline & Aggregation

**Files:**
- Create: `src/5-logparser/src/main/kotlin/LogParser.kt`
- Create: `src/5-logparser/src/test/kotlin/LogParserTest.kt`
- Create: `src/5-logparser/build.gradle.kts`

- [ ] **Step 1: Scaffold Gradle with Coroutines**
- [ ] **Step 2: Implement `LogEntry` data class**
- [ ] **Step 3: Build Flow-based parser with `filter` and `map`**
- [ ] **Step 4: Use a Channel to collect stats concurrently**
- [ ] **Step 5: Write tests for aggregation accuracy**
- [ ] **Step 6: Commit**
- [ ] **Step 7: Build CLI for Log Analysis**
  - Accept log file input.
  - Display summary table using Mordant.
- [ ] **Step 8: Generate `learning-guide.html`**
  - Explain Regex and Channel-based concurrency.
