# CI Hygiene + Verified Production Branch Protection

## Objective

Follow-up to the GitHub infrastructure work: bring the remaining CI gaps to zero
and prove that the applied `main` branch protection actually blocks bad states,
rather than merely reporting as configured.

## Findings from the last run (40541194106, `cfa6078`, success)

| Item | State | Action |
|---|---|---|
| `actions/checkout@v4`, `actions/setup-java@v4` | v4; GitHub deprecation notice for Node 20 runtime | Bump to v5 |
| `ubuntu-latest` | Migrating to Ubuntu 26 on 2026-10-19; image warning in logs | Pin to `ubuntu-24.04` |
| Actions pinned by tag | Supply-chain risk: tags are mutable | Pin to full commit SHAs with version comments |
| Build output | Post-`chore` push contains no `build/` artifacts | Fixed by 3dc0980; verify only |
| Branch protection | Configured (see below) but **never tested** | Run enforcement tests |

## Applied branch protection (verified via API)

```
enforce_admins          = true
required_status_checks  = ["Build All Projects"], strict (up-to-date branch)
required_approving_reviews = 0          # solo maintainer: no deadlock
dismiss_stale_reviews   = true
required_linear_history = true
required_conversation_resolution = true
allow_force_pushes      = false
allow_deletions         = false
```

## Task 1 — CI workflow hardening

**Files:** `.github/workflows/ci.yml`

1. Resolve current commit SHAs for `actions/checkout`, `actions/setup-java`,
   `actions/cache` (if used) via `gh api repos/<owner>/<repo>/git/ref/tags/<tag>`
   and pin as `@<sha> # v5`.
2. Change `runs-on: ubuntu-latest` → `ubuntu-24.04`.
3. Keep the job name `Build All Projects` unchanged — branch protection
   references it by name.
4. Validate with `actionlint` locally.

**Acceptance:** `actionlint` clean; no deprecation annotations in the run log;
job still reports as `Build All Projects`.

## Task 2 — Enforcement verification

Run each check against the live repo and record the actual rejection:

1. **Direct push blocked** — attempt `git push origin HEAD:main` with a trivial
   commit; expect `GH006` / "protected branch hook declined".
2. **Force push blocked** — `git push --force origin HEAD~1:main`; expect reject.
3. **Delete blocked** — `git push origin --delete main`; expect reject.
4. **PR path works** — open a branch + PR, confirm the `Build All Projects`
   check appears as a required check and merges once green.

Use a scratch clone or `--dry-run` where possible so the real history is not
disturbed. Record outputs verbatim in the completion report.

## Task 3 — Report

Summarize: what changed, the four enforcement results, current CI status, and
any residual risk (e.g. GitHub App bypass, admin override behaviour).

## Out of scope

- Android SDK setup, matrix builds, caching.
- Rewriting git history / squashing the applied commits.
