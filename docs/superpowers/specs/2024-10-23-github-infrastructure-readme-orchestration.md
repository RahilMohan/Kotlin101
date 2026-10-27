# Orchestration Design: GitHub Infrastructure & README Modernization

**Date**: 2024-10-23
**Project**: Kotlin101
**Status**: Design Phase - Awaiting Approval

---

## Executive Summary

Create a complete orchestration plan with 4 sub-agents:
- **Agent 1**: README Modernization (Tasks 1 & 3 combined)
- **Agent 2**: GitHub Actions CI Pipeline
- **Agent 3**: Branch Protection & PR Templates
- **Agent 4**: Reviewer Agent (validates all changes)

---

## Task 1 & 3: README Modernization (Combined)

### Current State vs Target

| Aspect | Current | Target |
|--------|---------|--------|
| Kotlin version badge | 2.3.21 | **2.4.21** |
| Compose Multiplatform | 1.7.3 | **1.12.2** |
| kotlinx.coroutines | 1.10.1 | **1.11.0** |
| kotlinx.serialization | 1.8.0 | **1.12.0** |
| Ktor | 3.1.1 | **3.6.0** |
| JDK target | 25+ | **21** |
| Badge style | Basic flat | **Modern flat with logos** |

### Design: Modern Flat Badges (shields.io)

```markdown
# Kotlin & Wear OS Mastery Curriculum

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.21-7F52FF?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.12.2-4285F4?style=flat&logo=jetbrains&logoColor=white)](https://github.com/JetBrains/compose-multiplatform)
[![Gradle](https://img.shields.io/badge/Gradle-9.5-02303A?style=flat&logo=gradle&logoColor=white)](https://gradle.org)
[![JDK](https://img.shields.io/badge/JDK-21-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://openjdk.org)
[![Wear OS](https://img.shields.io/badge/Wear%20OS-4%20(API%2033)-4CAF50?style=flat&logo=android&logoColor=white)](https://developer.android.com/wear)

[![Build Status](https://img.shields.io/github/actions/workflow/status/Baldur10/Kotlin101/ci.yml?branch=main&style=flat&logo=githubactions&logoColor=white)](https://github.com/Baldur10/Kotlin101/actions)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue?style=flat&logo=apache&logoColor=white)](LICENSE)
[![Stars](https://img.shields.io/github/stars/Baldur10/Kotlin101?style=flat&logo=github&logoColor=white)](https://github.com/Baldur10/Kotlin101/stargazers)
[![Last Commit](https://img.shields.io/github/last-commit/Baldur10/Kotlin101?style=flat&logo=git&logoColor=white)](https://github.com/Baldur10/Kotlin101/commits/main)
```

### Additional README Improvements

1. **Add project screenshots/GIFs** for Compose projects (6-15)
2. **Add quick-start table** with one-liner commands
3. **Add architecture diagram** (Mermaid.js)
4. **Add contributing guidelines link**
4. **Fix Tech Stack table** to match actual versions

---

## Task 2: Full GitHub Infrastructure Setup

### Component 1: GitHub Actions CI Workflow (`.github/workflows/ci.yml`)

**Use Case**: Automatically build and test all 15 projects on every push/PR to catch regressions early.

**Why Best Practice**: 
- Catches breaking changes before merge
- Provides fast feedback to contributors
- Documents build process as code
- Enables required status checks for branch protection

**Simple Example**:
```yaml
name: CI
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '21'
          cache: gradle
      - name: Build all projects
        run: |
          for dir in src/*/; do
            if [ -f "$dir/build.gradle.kts" ]; then
              echo "Building $dir"
              (cd "$dir" && ./gradlew build --no-daemon)
            fi
          done
```

### Component 2: Branch Protection Rules (via GitHub Settings API or manual)

**Use Case**: Prevent direct pushes to `main`, require PR reviews and CI passing.

**Why Best Practice**:
- Enforces code review process
- Prevents broken code from reaching main
- Creates audit trail of changes
- Required for compliance in many organizations

**Settings to Enable**:
- Require PR before merging
- Require status checks (CI workflow)
- Require up-to-date branches
- Dismiss stale reviews on new commits
- Require linear history (optional)

### Component 3: PR Template (`.github/pull_request_template.md`)

**Use Case**: Standardize PR descriptions, ensure reviewers have context.

**Why Best Practice**:
- Reduces back-and-forth in reviews
- Ensures testing evidence is provided
- Links to related issues automatically
- Helps new contributors understand expectations

**Template Structure**:
```markdown
## Description
<!-- What does this PR do? -->

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Dependency upgrade
- [ ] Documentation update
- [ ] Refactoring

## Testing
- [ ] All 15 projects build locally (`./gradlew build` in each)
- [ ] Tests pass in affected projects
- [ ] Manual verification for Compose apps (if UI changes)

## Checklist
- [ ] Code follows project style
- [ ] Self-review completed
- [ ] Documentation updated (if needed)
- [ ] No breaking changes (or documented in PR)

## Related Issues
Closes #<issue-number>
```

### Component 4: Issue Templates (`.github/ISSUE_TEMPLATE/`)

**Use Case**: Guide users to provide useful bug reports and feature requests.

**Files to Create**:
- `bug_report.md` - Structured bug reporting
- `feature_request.md` - Feature proposal template
- `dependency_upgrade.md` - For dependency upgrade requests

### Component 5: Dependabot Configuration (`.github/dependabot.yml`)

**Use Case**: Automated dependency updates with PRs.

**Why Best Practice**:
- Keeps dependencies current automatically
- Groups related updates
- Runs CI on generated PRs
- Reduces security vulnerability window

```yaml
version: 2
updates:
  - package-ecosystem: "gradle"
    directory: "/"
    schedule:
      interval: "weekly"
      day: "monday"
      time: "06:00"
    groups:
      kotlin:
        patterns: ["org.jetbrains.kotlin*"]
      compose:
        patterns: ["org.jetbrains.compose*", "androidx.compose*"]
      kotlinx:
        patterns: ["org.jetbrains.kotlinx*"]
      ktor:
        patterns: ["io.ktor*"]
```

### Component 6: CODEOWNERS (`.github/CODEOWNERS`)

**Use Case**: Auto-assign reviewers based on file paths.

**Why Best Practice**:
- Ensures right people review changes
- Distributes review load
- Prevents PRs from stalling

```
# Global owners
* @Baldur10

# Compose projects
/src/6-vibe-counter/ @Baldur10
/src/7-weather/ @Baldur10
...
```

### Component 7: Release Workflow (`.github/workflows/release.yml`)

**Use Case**: Automated releases on tag push.

**Why Best Practice**:
- Consistent release process
- Generates changelogs automatically
- Publishes artifacts if needed

---

## Task 4: Reviewer Agent

**Role**: Validates all 3 agents' work before merge.

**Checks**:
1. README versions match `build.gradle.kts` files
2. CI workflow runs successfully on test PR
3. Branch protection is configured (verify via API)
4. PR template renders correctly
5. Dependabot config is valid YAML
6. All badges resolve (no 404s)
7. No broken links in README

---

## Agent Assignment

| Agent | Responsibility | Output |
|-------|---------------|--------|
| **Agent 1** | README Modernization | Updated `README.md` |
| **Agent 2** | GitHub Actions CI | `.github/workflows/ci.yml` |
| **Agent 3** | Branch Protection & Templates | `.github/` folder + API calls |
| **Agent 4** | Reviewer | Validation report + approval |

---

## Execution Order

```
Phase 1 (Parallel):
├── Agent 1: README Modernization
├── Agent 2: CI Workflow
└── Agent 3: Branch Protection + Templates

Phase 2:
└── Agent 4: Reviewer (validates all)

Phase 3:
└── Create PR with all changes
    └── CI runs → Reviewer approves → Merge to main
```

---

## Success Criteria

- [ ] README.md versions match all 15 `build.gradle.kts` files
- [ ] CI workflow builds all 15 projects successfully
- [ ] Branch protection enabled on `main` with required checks
- [ ] PR template works when opening new PR
- [ ] Dependabot configured and creates test PR
- [ ] All badges in README resolve (HTTP 200)
- [ ] No broken links in README
- [ ] Reviewer agent signs off

---

## Questions for Approval

1. **GitHub Token**: Do you have a `GITHUB_TOKEN` or PAT with `repo` scope for API calls (branch protection, creating workflows)?
2. **Default Branch**: Confirm `main` is the default branch (not `master`)
3. **Repository Visibility**: Is this repo public or private? (affects some settings)
4. **Team Reviewers**: Any specific GitHub usernames to add to CODEOWNERS beyond @Baldur10?