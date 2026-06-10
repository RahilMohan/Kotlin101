# Design: 'Vibe' Counter (Project 6)

**Goal:** Master Jetpack Compose State and Recomposition.

## Architecture
- **UI:** A single screen with a counter and "Vibe" controls.
- **State:** `mutableStateOf` to track the count and vibe level.
- **Components:** `Column`, `Text`, `Button`, `Row`.

## Logic Decisions
- **UDF (Unidirectional Data Flow):** Events go up (button clicks), State goes down (recomposition).
- **State Hoisting:** Move state out of the composable to make it testable.
- **Material 3:** Use modern Material Design components.

## Features
1. **Increment/Decrement:** Basic state updates.
2. **Vibe Level:** Dynamic text/color changes based on the counter value.
3. **Reset:** Resetting multiple state variables at once.
