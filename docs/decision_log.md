# Decision Log

## [2026-10-28] CI: pin actions to SHAs, bump to latest majors, enforce admins

**Decision:**
- All workflow actions are pinned to full commit SHAs with a version comment, and kept at their **latest major release** (checkout v7.0.1, setup-java v6.1.0, setup-gradle v6.4.0, upload-artifact v7.0.2).
- Runner image pinned to `ubuntu-24.04` rather than `ubuntu-latest`.
- `main` protection: required check `Build All Projects` (strict), **0** required approvals, linear history, no force-push, no deletion, admins enforced.

**Rationale:**
- Action tags are mutable; a compromised action repo could repoint `v5` at malicious code running with the workflow token.
- `ubuntu-latest` migrates to Ubuntu 26 on 2026-10-19; pinning makes upgrades deliberate and testable.
- A solo maintainer with 1 required approval would be unable to merge (deadlock), so approvals stay at 0 and the status check carries the safety.
- Linear history keeps `git log` bisectable and readable.

**Consequences:**
- Merge must use `--rebase` or `--squash`; `gh pr merge` defaults to a merge commit and is rejected.
- Direct pushes to `main` are rejected for everyone, including the owner (`enforce_admins`).
- SHA pins go stale without automation, so Dependabot's `github-actions` ecosystem is enabled to propose bumps.

## [2026-05-12] Environment Baseline (Project 0, Task 1)

**Decision:**
Standardize on JDK 25 and Kotlin 2.3 for the initial phase of the Mastery Path. Require Android SDK 34 and Wear OS 4 (API 33).

**Rationale:**
- JDK 25 is current and stable for modern development.
- Kotlin 2.3 provides the latest stability and K2 compiler benefits.
- Android 34 and API 33 are targets for Wear OS 4 proficiency.

**Consequences:**
- All code must be compatible with Kotlin 2.x.
- Developer must manually enable K2 in Android Studio for consistent analysis.
- Verification via `src/0-environment/sdk_check.sh` is mandatory for all contributors.
