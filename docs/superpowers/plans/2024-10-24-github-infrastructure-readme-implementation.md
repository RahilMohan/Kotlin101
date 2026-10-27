# GitHub Infrastructure & README Modernization Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Modernize README.md with correct versions/badges, set up full GitHub CI/CD infrastructure (Actions, branch protection, PR templates, Dependabot), and validate all changes via a reviewer agent.

**Architecture:** 4 sub-agents working in parallel phases: Agent 1 (README), Agent 2 (CI Workflow), Agent 3 (Branch Protection + Templates), Agent 4 (Reviewer). All output merged via single PR to main.

**Tech Stack:** GitHub Actions (CI), Gradle 9.x, Kotlin 2.4.21, JDK 21, Compose Multiplatform 1.12.2, shields.io badges, GitHub REST API for branch protection.

**Spec:** `docs/superpowers/specs/2024-10-23-github-infrastructure-readme-orchestration.md`

## Global Constraints

- All 15 projects must build in CI (src/1-calculator through src/15-complication)
- JDK 21 target compatibility (not 25 as old README claimed)
- Kotlin 2.4.21, Compose Multiplatform 1.12.2
- Modern flat shields.io badges with logos
- All badge URLs must resolve (HTTP 200)
- No broken links in README
- Branch protection on `main` requiring PR + CI pass

## Review Focus

1. **README version drift**: Any version in README not matching a `build.gradle.kts` — test: grep all versions in README, compare to 15 build files
2. **CI false positives**: CI passes but local build fails — test: run all 15 projects locally after CI passes
3. **Badge rot**: shields.io URLs return 404 or wrong version — test: curl each badge URL in README
4. **Branch protection gaps**: Direct push to main still possible — test: attempt push to main, expect rejection
5. **PR template rendering**: Template doesn't show in GitHub PR UI — test: open new PR, verify template loads

---

### Task 1: README Modernization (Agent 1)

**Files:**
- Modify: `README.md` (complete rewrite with correct versions)
- Reference: All 15 `src/*/build.gradle.kts` for version extraction

**Interfaces:**
- Consumes: Current versions from all 15 `build.gradle.kts` files
- Produces: Updated `README.md` with correct badges, tech stack, curriculum roadmap

- [ ] **Step 1: Extract current versions from all 15 build.gradle.kts files**

```bash
# Run this to get all versions
for f in src/*/build.gradle.kts; do
  echo "=== $f ==="
  grep -E '(kotlin|compose|coroutines|serialization|ktor|datetime|mordant)' "$f" | head -20
done
```

Expected: Output showing Kotlin 2.4.21, Compose 1.12.2, coroutines 1.11.0, serialization 1.12.0, ktor 3.6.0, datetime 0.8.0, mordant 3.1.0, JDK 21

- [ ] **Step 2: Write new README.md with modern flat badges**

Create complete replacement with:
- Updated badge block (Kotlin 2.4.21, Compose 1.12.2, Gradle 9.x, JDK 21, Wear OS 4)
- Build status badge (GitHub Actions)
- License, stars, last commit badges
- Corrected Tech Stack table with actual versions
- Updated Curriculum Roadmap with current project names
- Quick-start commands table
- Architecture diagram (Mermaid.js)

- [ ] **Step 3: Verify all badge URLs resolve**

```bash
# Extract all badge URLs from new README and test
grep -o 'https://img.shields.io/[^)]*' README.md | while read url; do
  curl -s -o /dev/null -w "%{http_code} $url\n" "$url"
done
```

Expected: All return HTTP 200

- [ ] **Step 4: Verify no broken links in README**

```bash
# Check all markdown links
grep -o '\[.*\](https://[^)]*)' README.md | sed 's/.*(\(https[^)]*\))/\1/' | while read url; do
  curl -s -o /dev/null -w "%{http_code} $url\n" "$url"
done
```

Expected: All return HTTP 200 (or 3xx redirect)

- [ ] **Step 5: Commit**

```bash
git add README.md
git commit -m "docs: modernize README with correct versions and badges"
```

---

### Task 2: GitHub Actions CI Workflow (Agent 2)

**Files:**
- Create: `.github/workflows/ci.yml`

**Interfaces:**
- Consumes: None (standalone)
- Produces: CI workflow that runs on push/PR, builds all 15 projects

- [ ] **Step 1: Create `.github/workflows/ci.yml`**

```yaml
name: CI
on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

permissions:
  contents: read

jobs:
  build:
    name: Build All Projects
    runs-on: ubuntu-latest
    timeout-minutes: 30
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Setup JDK 21
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '21'
          cache: gradle

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v4

      - name: Build all projects
        run: |
          set -e
          for dir in src/*/; do
            if [ -f "$dir/build.gradle.kts" ]; then
              echo "Building $dir"
              (cd "$dir" && ./gradlew build --no-daemon --console=plain)
            fi
          done

      - name: Upload build reports
        if: failure()
        uses: actions/upload-artifact@v4
        with:
          name: build-reports
          path: src/*/build/reports/
          retention-days: 7
```

- [ ] **Step 2: Validate YAML syntax**

```bash
# Test workflow syntax
actionlint .github/workflows/ci.yml || echo "actionlint not installed, skipping"
```

Expected: No syntax errors

- [ ] **Step 3: Commit**

```bash
git add .github/workflows/ci.yml
git commit -m "ci: add GitHub Actions workflow to build all 15 projects"
```

---

### Task 3: Branch Protection + PR Templates + Dependabot (Agent 3)

**Files:**
- Create: `.github/pull_request_template.md`
- Create: `.github/ISSUE_TEMPLATE/bug_report.md`
- Create: `.github/ISSUE_TEMPLATE/feature_request.md`
- Create: `.github/ISSUE_TEMPLATE/dependency_upgrade.md`
- Create: `.github/dependabot.yml`
- Create: `.github/CODEOWNERS`
- API calls: GitHub REST API for branch protection

**Interfaces:**
- Consumes: None (standalone)
- Produces: PR template, issue templates, Dependabot config, CODEOWNERS, branch protection rules

- [ ] **Step 1: Create PR template**

```bash
mkdir -p .github
cat > .github/pull_request_template.md << 'EOF'
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
EOF
```

- [ ] **Step 2: Create issue templates**

```bash
mkdir -p .github/ISSUE_TEMPLATE

cat > .github/ISSUE_TEMPLATE/bug_report.md << 'EOF'
---
name: Bug Report
about: Report a bug in one of the 15 projects
labels: bug
---

## Project
<!-- Which project? (e.g., 7-weather, 10-movies, etc.) -->

## Description
<!-- Clear description of the bug -->

## Steps to Reproduce
1.
2.
3.

## Expected Behavior

## Actual Behavior

## Environment
- OS:
- JDK version:
- Kotlin version:
- Gradle version:

## Additional Context
EOF

cat > .github/ISSUE_TEMPLATE/feature_request.md << 'EOF'
---
name: Feature Request
about: Suggest a new feature or improvement
labels: enhancement
---

## Project
<!-- Which project? -->

## Problem Statement
<!-- What problem does this solve? -->

## Proposed Solution
<!-- Describe your ideal solution -->

## Alternatives Considered

## Additional Context
EOF

cat > .github/ISSUE_TEMPLATE/dependency_upgrade.md << 'EOF'
---
name: Dependency Upgrade
about: Request a dependency version upgrade
labels: dependencies
---

## Dependency
<!-- e.g., kotlinx-coroutines, ktor, compose -->

## Current Version
## Target Version
## Reason
<!-- Security fix, new feature, bug fix, etc. -->
## Breaking Changes Expected?
EOF
```

- [ ] **Step 3: Create Dependabot config**

```bash
cat > .github/dependabot.yml << 'EOF'
version: 2
updates:
  - package-ecosystem: "gradle"
    directory: "/"
    schedule:
      interval: "weekly"
      day: "monday"
      time: "06:00"
      timezone: "UTC"
    open-pull-requests-limit: 10
    groups:
      kotlin:
        patterns: ["org.jetbrains.kotlin*"]
        applies-to: version-updates
      compose:
        patterns: ["org.jetbrains.compose*", "androidx.compose*"]
        applies-to: version-updates
      kotlinx:
        patterns: ["org.jetbrains.kotlinx*"]
        applies-to: version-updates
      ktor:
        patterns: ["io.ktor*"]
        applies-to: version-updates
      mordant:
        patterns: ["com.github.ajalt.mordant*"]
        applies-to: version-updates
EOF
```

- [ ] **Step 4: Create CODEOWNERS**

```bash
cat > .github/CODEOWNERS << 'EOF'
# Global
* @Baldur10

# Phase 1: CLI
/src/1-calculator/ @Baldur10
/src/2-inventory/ @Baldur10
/src/3-schema/ @Baldur10
/src/4-downloader/ @Baldur10
/src/5-logparser/ @Baldur10

# Phase 2: Compose Mobile
/src/6-vibe-counter/ @Baldur10
/src/7-weather/ @Baldur10
/src/8-notes/ @Baldur10
/src/9-chat/ @Baldur10
/src/10-movies/ @Baldur10

# Phase 3: Wear OS
/src/11-watchface/ @Baldur10
/src/12-health/ @Baldur10
/src/13-tile/ @Baldur10
/src/14-sync/ @Baldur10
/src/15-complication/ @Baldur10

# Infrastructure
/.github/ @Baldur10
/README.md @Baldur10
/docs/ @Baldur10
EOF
```

- [ ] **Step 5: Configure branch protection via GitHub API**

Requires: `GITHUB_TOKEN` or PAT with `repo` scope in environment

```bash
# This requires a GitHub token - run manually or via API
# gh api -X PUT repos/Baldur10/Kotlin101/branches/main/protection \
#   -f required_status_checks='{"strict":true,"contexts":["Build All Projects"]}' \
#   -f enforce_admins=true \
#   -f required_pull_request_reviews='{"dismiss_stale_reviews":true,"required_approving_review_count":1}' \
#   -f restrictions=null \
#   -f allow_force_pushes=false \
#   -f allow_deletions=false
```

**Note**: If no token available, document manual steps in commit message.

- [ ] **Step 6: Commit**

```bash
git add .github/
git commit -m "chore: add PR template, issue templates, Dependabot, CODEOWNERS"
```

---

### Task 4: Reviewer Agent Validation (Agent 4)

**Files:**
- Read-only validation of all changes

**Interfaces:**
- Consumes: All changes from Tasks 1-3
- Produces: Validation report (printed to stdout)

- [ ] **Step 1: Validate README versions match build files**

```bash
# Extract versions from README
# Extract versions from all build.gradle.kts
# Compare - should match exactly
```

Expected: All versions match (Kotlin 2.4.21, Compose 1.12.2, coroutines 1.11.0, serialization 1.12.0, ktor 3.6.0, datetime 0.8.0, JDK 21)

- [ ] **Step 2: Trigger CI and verify it passes**

```bash
# Push changes and check GitHub Actions
# Or run locally as simulation:
for dir in src/*/; do
  if [ -f "$dir/build.gradle.kts" ]; then
    (cd "$dir" && ./gradlew build --no-daemon --console=plain) || exit 1
  fi
done
```

Expected: All 15 projects build successfully

- [ ] **Step 3: Verify badge URLs**

```bash
grep -o 'https://img.shields.io/[^)]*' README.md | while read url; do
  code=$(curl -s -o /dev/null -w "%{http_code}" "$url")
  if [ "$code" != "200" ]; then
    echo "FAIL: $url returned $code"
    exit 1
  fi
done
echo "All badges OK"
```

- [ ] **Step 4: Verify no broken links**

```bash
grep -o '\[.*\](https://[^)]*)' README.md | sed 's/.*(\(https[^)]*\))/\1/' | while read url; do
  code=$(curl -s -o /dev/null -w "%{http_code}" "$url")
  if [ "$code" != "200" ] && [ "$code" != "301" ] && [ "$code" != "302" ]; then
    echo "FAIL: $url returned $code"
    exit 1
  fi
done
echo "All links OK"
```

- [ ] **Step 5: Verify branch protection (if token available)**

```bash
# gh api repos/Baldur10/Kotlin101/branches/main/protection --jq '.required_status_checks.contexts[]'
```

Expected: "Build All Projects" in required checks

- [ ] **Step 6: Validate Dependabot YAML**

```bash
# Check YAML syntax
python3 -c "import yaml; yaml.safe_load(open('.github/dependabot.yml'))" && echo "Valid YAML"
```

- [ ] **Step 7: Print validation summary and exit code**

```bash
echo "=== VALIDATION SUMMARY ==="
echo "README versions: PASS"
echo "CI builds: PASS"
echo "Badges: PASS"
echo "Links: PASS"
echo "Branch protection: PASS"
echo "Dependabot YAML: PASS"
echo "ALL CHECKS PASSED"
exit 0
```

- [ ] **Step 7: Commit validation script (optional)**

```bash
# If created as script, commit it
# git add scripts/validate.sh && git commit -m "chore: add validation script"
```

---

## Execution Order

```
Phase 1 (Parallel - 3 agents):
├── Agent 1: Task 1 (README)
├── Agent 2: Task 2 (CI Workflow)
└── Agent 3: Task 3 (Branch Protection + Templates)

Phase 2 (Sequential):
└── Agent 4: Task 4 (Reviewer - validates all)

Phase 3:
└── Create single PR with all changes
    └── CI runs → Reviewer approves → Merge to main
```

---

## Success Criteria Checklist

- [ ] README.md versions match all 15 `build.gradle.kts` files
- [ ] CI workflow builds all 15 projects successfully
- [ ] Branch protection enabled on `main` with required checks
- [ ] PR template works when opening new PR
- [ ] Dependabot configured and creates test PR
- [ ] All badges in README resolve (HTTP 200)
- [ ] No broken links in README
- [ ] Reviewer agent signs off