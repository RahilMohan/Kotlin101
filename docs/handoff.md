# Handoff

> Compact brief for the next session or agent. Update at every meaningful
> checkpoint. Do not paste chat transcripts.

## Task

Keep Kotlin101's CI, dependency versions, and `main` protection healthy after
the GitHub-infrastructure modernization.

## Current status

Done. All dependency and CI work is merged; `main` is green at `0048a34`
with no open PRs.

## What changed

- `.github/workflows/ci.yml`: actions bumped to checkout v7.0.1, setup-java
  v6.1.0, setup-gradle v6.4.0, upload-artifact v7.0.2 (all SHA-pinned);
  runner pinned to `ubuntu-24.04`.
- `.github/dependabot.yml`: scans `directories: ["/src/*"]` (each project is a
  separate Gradle build) plus a `github-actions` ecosystem for SHA pins.
- `README.md`: flat-square badges with logos, duplicated License block and a
  stray `# CI Trigger` fragment removed.
- `.gitignore`: build output untracked (2,915 files removed from the index).
- Removed a dead Maven host (`maven.pkg.jetbrains.tech`) from 10 Compose
  builds, replaced with `maven.pkg.jetbrains.space/public/p/compose/dev`.

## Decisions made

- Pins target **latest majors**, not the oldest working version — Dependabot
  would otherwise keep proposing the same bumps.
- Branch protection keeps 0 required approvals so the solo maintainer is not
  locked out; the required status check carries the safety instead.
- The CI job is a single job named `Build All Projects`; branch protection
  references that exact string, so renaming it breaks merges.

## Open questions

- Should `src/0-environment` (shell scripts, no Gradle build) be covered by CI?
- Do the Wear OS projects (11–15) need an Android SDK step, or is the
  compile-only build sufficient as a curriculum gate?

## Risks

- Major action bumps were validated only by these workflow runs; checkout v7
  and upload-artifact v7 changed defaults (newer Node runtime, artifact
  behaviour). Watch the first few real runs.
- `current_state.md` had stale toolchain values (Kotlin 2.3.21, Compose 1.7.3,
  Ktor 3.1.1, Gradle 9.5, JDK 26) versus the actual toolchain; drift is
  corrected there.

## Next recommended step

Launch the Compose desktop projects (6-10) and eyeball the UI: `material3`
moved alpha03 -> beta01 and only compilation was verified.

## Files to inspect next

- `.github/workflows/ci.yml`
- `docs/current_state.md`
- `docs/next_steps.md`
