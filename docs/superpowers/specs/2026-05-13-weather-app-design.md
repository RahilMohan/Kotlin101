# Design: Adaptive Weather App (Project 7)

**Goal:** Master Compose Lifecycles and Side Effects (`LaunchedEffect`).

## Architecture
- **State:** `WeatherState` sealed class (Loading, Success, Error).
- **Logic:** `WeatherViewModel` using Coroutines to simulate network fetch.
- **UI:** Conditional rendering based on the sealed class state.

## Logic Decisions
- **Sealed Classes:** Perfect for representing distinct UI states.
- **LaunchedEffect:** Trigger the initial fetch when the screen "enters" the composition.
- **Error Handling:** Robustly handle simulated network failures.

## Features
1. **Initial Load:** Automatic fetch on startup.
2. **Refresh:** Manual button to re-trigger the coroutine.
3. **Sealed UI:** Distinct screens for Loading (CircularProgress), Success (Temperature/City), and Error (Retry button).
