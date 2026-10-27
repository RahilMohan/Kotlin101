# Current State

## Milestone
**Curriculum Completed!**

## Capabilities
- **Core Kotlin:** Solid foundation in Reflection, Generics, Regex, and Serialization.
- **Async & Reactive:** Coroutines and Flow implemented and tested.
- **UI & Architecture:** Compose Multiplatform, Clean Architecture, and Custom layouts.
- **Wear OS Specialization:** Deep implementations of Watch Faces, Tiles, Complications, Data Layer syncing, and sensors. Fully verified up to Project 15.
- **Educational Resources:** 16 learning guides present and verified.

## Runtime Stack
- Kotlin 2.4.21 (all 15 projects)
- Compose Multiplatform 1.12.2
- Ktor 3.6.0
- kotlinx.coroutines 1.11.0
- kotlinx.serialization 1.12.0
- Gradle 9.8.2 (wrapper, all 15 projects)
- JDK 21 (CI: Temurin 21; local dev may be newer)

## CI & Repository Protection
- Workflow: `.github/workflows/ci.yml`, single job `Build All Projects`,
  builds all 15 projects and reports every failure (not just the first).
- Runner: `ubuntu-24.04`. Actions pinned to commit SHAs at their latest majors.
- `main` is protected: required check `Build All Projects` (strict), linear
  history, no force-push/deletion, admins enforced, 0 required approvals.
- Merges must use `--rebase` or `--squash` (merge commits are rejected).
- Dependabot covers `src/*` Gradle builds and GitHub Actions.

## Known Gaps
- `docs/current_state.md` toolchain values were stale and are now corrected
  (previously listed Kotlin 2.3.21 / Compose 1.7.3 / JDK 26).
- `src/0-environment` is shell scripts only and is not exercised by CI.
- `project_brief.md` still exists; `docs/env` variables are documented in
  `.env.example` only where applicable — this is primarily a curriculum repo.

