# Design: Smart Home Complication (Project 15)

**Goal:** Master Wear OS Complication Providers and System-level data binding.

## Architecture
- **Provider:** `SmartHomeComplicationService` (System entry point).
- **Data Model:** `ComplicationData` (Icon, Text, TapAction).
- **Home Engine:** Simulated home status manager.

## Logic Decisions
- **System Callbacks:** Implement `onComplicationUpdate` logic to refresh data.
- **Tap Actions:** Logic to toggle a light when the user taps the complication.
- **Formatting:** Use short, meaningful strings (e.g., "ON", "OFF") optimized for tiny slots.

## Features
1. **Live Status:** Showing "Lights ON/OFF" on the watch face.
2. **Toggle Logic:** Changing state via user interaction.
3. **Icons:** Mapping status to visual assets (simulated).
