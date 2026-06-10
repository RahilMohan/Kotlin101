# Design: Async File Downloader (Project 4)

**Goal:** Master Kotlin Coroutines by building a non-blocking file downloader simulation with progress tracking.

## Architecture
- **Downloader:** Uses `suspend` functions and `delay()` to simulate network latency.
- **Concurrency:** Uses `launch` and `async` to handle multiple downloads in parallel.
- **Context:** Uses `Dispatchers.IO` for simulation and `Dispatchers.Default` for progress calculation.

## Logic Decisions
- **Structured Concurrency:** Wrap downloads in a `coroutineScope` to ensure all children finish or cancel together.
- **Progress Handling:** Use a callback or a simple println for progress updates to keep the "pure Kotlin" focus before we reach Flow.
- **Exception Handling:** Use `try-catch` within coroutines to handle simulated network failures.

## Features
1. **Parallel Downloads:** Download multiple "files" simultaneously.
2. **Progress Updates:** Periodic console updates on download percentage.
3. **Cancellation:** Demonstrate how to cancel a pending download.
