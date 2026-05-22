# Implementation Plan: Inventory CLI

Track ID: `inventory-cli_20260522`
Created: 2026-05-22
Status: in-progress

## Phase 1: Data Model & CRUD Logic

### Tasks
- [x] **Task 1.1**: Define `InventoryItem` data class `17dbb50`
- [x] **Task 1.2**: Implement `InventoryManager` with `addItem` and `getAllItems` `17dbb50`
- [x] **Task 1.3**: Implement `findItemById` and `findItemsByName` (Null-Safe) `17dbb50`
- [x] **Task 1.4**: Implement `updateQuantity` (.copy()) and `removeItem` `17dbb50`
- [x] **Task 1.5**: Verify CRUD logic with unit tests `17dbb50`

### Verification
- [x] **Verify 1.1**: Unit tests pass for all CRUD operations

## Phase 2: Interactive TUI

### Tasks
- [x] **Task 2.1**: Scaffold Gradle for Project 2 with Mordant `17dbb50`
- [x] **Task 2.2**: Implement TUI loop with styled table output `17dbb50`
- [x] **Task 2.3**: Add interactive commands (add, update, remove, search) `17dbb50`
- [x] **Task 2.4**: Final verification and manual testing `17dbb50`

### Verification
- [x] **Verify 2.1**: App runs and handles all commands correctly

## Checkpoints
| Phase | Checkpoint SHA | Date | Status |
| ----- | -------------- | ---- | ------ |
| Phase 1 | 17dbb50 | 2026-05-22 | verified |
| Phase 2 | 17dbb50 | 2026-05-22 | verified |
