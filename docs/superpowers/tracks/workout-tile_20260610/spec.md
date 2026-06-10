# Design: Active Workout Tile (Project 13)

**Goal:** Master Wear OS Tile Service and Layout DSL logic.

## Architecture
- **Service:** `WorkoutTileService` (Remote-bound UI provider).
- **Layout Builder:** `TileLayoutFactory` producing Wear OS compatible layout descriptions.
- **Refresh logic:** Logic to trigger tile updates when data changes.

## Logic Decisions
- **Declarative Layouts:** Use a Kotlin DSL pattern to represent the Wear OS Tile layout.
- **Data Binding:** Pass simple primitives (distance, calories, time) to the layout builder.
- **Glanceability:** Design for high contrast and large text suitable for a small watch face.

## Features
1. **Dynamic Progress:** Circular progress indicator simulation.
2. **Metric Display:** Showing calories and duration.
3. **Quick Action:** Button logic to "Start/Stop" workout from the tile.
4. **Auto-Refresh:** Logic to simulate periodic tile updates.
