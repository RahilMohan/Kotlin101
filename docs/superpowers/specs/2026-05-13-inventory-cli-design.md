# Design: Inventory CLI (Project 2)

**Goal:** Master Kotlin Data Classes and Null Safety through a professional inventory management tool.

## Architecture
- **Data Model:** `InventoryItem` data class.
- **Logic:** `InventoryManager` handling a `MutableList`.
- **UI:** Mordant-powered table view and interactive prompts.

## Logic Decisions
- **Data Classes:** Use `data class` for automatic `toString()`, `equals()`, and `copy()`.
- **Null Safety:** Use `find { ... }` which returns a nullable type (`Item?`), then handle it using the Elvis operator (`?:`) or safe calls (`?.`).
- **Immutability:** Demonstrate updating an item by using `.copy()` to create a new instance with changed values, rather than mutating the object directly.

## Features
1. **Add Item:** Validating name and numeric inputs.
2. **List Items:** Styled table output.
3. **Search:** Case-insensitive search by name.
4. **Update Quantity:** Finding by ID and using `.copy()`.
