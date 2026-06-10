# Design: Multi-threaded Log Parser (Project 5)

**Goal:** Master Kotlin Flow and Channels by building a high-performance log processing pipeline.

## Architecture
- **Source:** A Flow that emits raw log lines.
- **Pipeline:** Filters and transforms lines into `LogEntry` objects using Flow operators (`map`, `filter`).
- **Aggregation:** A Channel that collects statistics (error counts, top IPs) from multiple processing coroutines.
- **Output:** A terminal dashboard updated via Flow.

## Logic Decisions
- **Flow:** Use `asFlow()` and `flowOn(Dispatchers.IO)` for efficient stream processing.
- **Channels:** Use a `Channel<LogStats>` to aggregate data from concurrent processing branches.
- **Backpressure:** Demonstrate how Flow handles slow consumers.

## Features
1. **Real-time Filtering:** Filter by log level (INFO, ERROR).
2. **Concurrent Aggregation:** Multiple coroutines processing chunks of logs.
3. **Reactive Dashboard:** Flow-driven console output updates.
