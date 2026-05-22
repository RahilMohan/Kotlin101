# Project 2: Inventory CLI

## Overview
Master Kotlin Data Classes and Null Safety by building a professional inventory management tool with a Mordant-powered TUI.

## Functional Requirements
- **FR-1: Add Item**: Add an item with name, quantity, price, and optional category.
- **FR-2: List Items**: Display all items in a styled table.
- **FR-3: Search**: Search for items by name (case-insensitive).
- **FR-4: Update Quantity**: Update the quantity of an existing item using its ID.
- **FR-5: Remove Item**: Remove an item by its ID.

## Non-Functional Requirements
- **NFR-1: Null Safety**: Use Kotlin's null safety features (Elvis, safe calls) correctly.
- **NFR-2: Immutability**: Use `.copy()` for updates to demonstrate immutability-aware logic.
- **NFR-3: Professional UI**: Use Mordant for table rendering and color-coded feedback.

## Acceptance Criteria
- [ ] Items can be added, updated, searched, and removed.
- [ ] Table output uses color to highlight low stock (qty < 10).
- [ ] Search is case-insensitive.
- [ ] Updates to quantity create a new data class instance via `.copy()`.
- [ ] Optional category is handled safely using Kotlin nullability.
