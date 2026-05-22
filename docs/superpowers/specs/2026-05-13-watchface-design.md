# Design: Canvas Watch Face (Project 11)

**Goal:** Master low-level drawing and the Watch Face Service architecture.

## Architecture
- **Service:** `CustomWatchFaceService` (Wear OS entry point).
- **Renderer:** `WatchFaceRenderer` handling the Canvas drawing logic.
- **Engine:** Handles the draw loop and lifecycle (Ambient mode, etc.).

## Logic Decisions
- **Canvas API:** Use `drawCircle`, `drawLine`, and `drawText` for high-performance rendering.
- **Ambient Mode:** Implement logic to reduce power consumption (fewer colors, no animations) when the watch is inactive.
- **Math:** Use trigonometry (`cos`, `sin`) to calculate clock hand positions.

## Features
1. **Analog Clock:** Hands for hours, minutes, and seconds.
2. **Ambient Support:** Simplified "Always-on" display logic.
3. **Smooth Seconds:** Demonstrating high-frequency redraws.
