# Design: Clean Architecture Movie App (Project 10)

**Goal:** Master Clean Architecture and Modular Design.

## Architecture
- **Domain Layer:** `Movie` entity, `GetMoviesUseCase` interface.
- **Data Layer:** `MovieRepositoryImpl`, `MovieDto` (serializable).
- **UI Layer:** `MovieViewModel`.

## Logic Decisions
- **Independence:** The Domain layer must have zero dependencies on Data or UI.
- **Mapping:** Use mapper functions to convert `MovieDto` (network) to `Movie` (domain).
- **Use Cases:** Encapsulate business logic into single-purpose classes.

## Features
1. **Browse Movies:** Using a UseCase to fetch data.
2. **Search Movies:** Domain-level filtering logic.
3. **Details View:** Handling navigation state in the ViewModel.
