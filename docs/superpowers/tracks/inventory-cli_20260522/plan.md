# Implementation Plan: Inventory CLI

Track ID: `inventory-cli_20260522`
Created: 2026-05-22
Status: in-progress

## Phase 1: Data Model & CRUD Logic

### Tasks
- [~] **Task 1.1**: Define `InventoryItem` data class
- [ ] **Task 1.2**: Implement `InventoryManager` with `addItem` and `getAllItems`
- [ ] **Task 1.3**: Implement `findItemById` and `findItemsByName` (Null-Safe)
- [ ] **Task 1.4**: Implement `updateQuantity` (.copy()) and `removeItem`
- [ ] **Task 1.5**: Verify CRUD logic with unit tests

### Verification
- [ ] **Verify 1.1**: Unit tests pass for all CRUD operations

## Phase 2: Interactive TUI

### Tasks
- [ ] **Task 2.1**: Scaffold Gradle for Project 2 with Mordant
- [ ] **Task 2.2**: Implement TUI loop with styled table output
- [ ] **Task 2.3**: Add interactive commands (add, update, remove, search)
- [ ] **Task 2.4**: Final verification and manual testing

### Verification
- [ ] **Verify 2.1**: App runs and handles all commands correctly

## Checkpoints
| Phase | Checkpoint SHA | Date | Status |
| ----- | -------------- | ---- | ------ |
| Phase 1 | | | pending |
| Phase 2 | | | pending |
