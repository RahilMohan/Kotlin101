# Design: Health Tracker (Project 12)

**Goal:** Master Wear OS Sensor integration and Health Services patterns.

## Architecture
- **Sensor:** `HeartRateSensor` (simulated).
- **Service:** `HealthManager` managing the sensor connection and permission states.
- **UI State:** `HeartRateState` (BPM, Accuracy, Connection status).

## Logic Decisions
- **Simulated Hardware:** Use a `ticker` Flow to emit random BPM values within a human range (60-160).
- **Listener Pattern:** Implement a clean interface for subscribing to heart rate updates.
- **Concurrency:** Ensure sensor readings are processed on a background thread.

## Features
1. **Real-time BPM:** Live updates from the "sensor."
2. **Accuracy Levels:** Simulating varying signal quality.
3. **Safety:** Logic to stop the sensor when the app is "invisible" (to save battery).
