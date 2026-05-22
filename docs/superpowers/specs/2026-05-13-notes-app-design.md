# Design: Offline-First Notes (Project 8)

**Goal:** Master Dependency Injection and the Repository Pattern.

## Architecture
- **Model:** `Note` data class.
- **Data Source:** `NoteLocalDataSource` interface (simulating Room).
- **Repository:** `NoteRepository` that mediates between the UI and data sources.
- **DI:** Manual constructor-based injection (simulating Hilt).

## Logic Decisions
- **Interfaces:** Define a `NoteDataSource` interface to show how DI allows swapping implementations.
- **In-Memory Store:** Use a `MutableMap` in the implementation for speed and simplicity.
- **Reactive Repository:** Return `Flow<List<Note>>` from the repository to show real-time updates.

## Features
1. **Save Note:** Persisting a new note.
2. **List Notes:** Reactive Flow of all notes.
3. **Delete Note:** Removing by ID.
