# Environment Setup Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Create a verification script and document environment decisions for Project 0 Task 1.

**Architecture:** A bash script will handle automated checks, while `decision_log.md` will store persistent environment facts.

**Tech Stack:** Bash, Kotlin 2.3+, JDK 17+.

---

### Task 1: Create SDK Check Script

**Files:**
- Create: `src/0-environment/sdk_check.sh`

- [ ] **Step 1: Write the script content**
Implement checks for Java, Kotlin, and ADB with version parsing.

```bash
#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo "--- Kotlin & Wear OS Environment Check ---"

# Check Java
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
if [[ "$JAVA_VERSION" -ge 17 ]]; then
    echo -e "${GREEN}PASS:${NC} Java $JAVA_VERSION detected (Min: 17)"
else
    echo -e "${RED}FAIL:${NC} Java $JAVA_VERSION detected (Min: 17 required)"
    exit 1
fi

# Check Kotlin
KOTLIN_VERSION=$(kotlinc -version 2>&1 | awk '{print $3}' | awk -F '-' '{print $1}')
KOTLIN_MAJOR=$(echo $KOTLIN_VERSION | awk -F '.' '{print $1}')
if [[ "$KOTLIN_MAJOR" -ge 2 ]]; then
    echo -e "${GREEN}PASS:${NC} Kotlin $KOTLIN_VERSION detected (Min: 2.0)"
else
    echo -e "${RED}FAIL:${NC} Kotlin $KOTLIN_VERSION detected (Min: 2.0 required)"
    exit 1
fi

# Check ADB
if command -v adb &> /dev/null; then
    ADB_V=$(adb version | head -n 1)
    echo -e "${GREEN}PASS:${NC} $ADB_V detected"
else
    echo -e "${RED}FAIL:${NC} adb not found"
    exit 1
fi

echo "--- Check Complete ---"
```

- [ ] **Step 2: Make script executable**
Run: `chmod +x src/0-environment/sdk_check.sh`

- [ ] **Step 3: Run the script to verify**
Run: `./src/0-environment/sdk_check.sh`
Expected: All PASS.

---

### Task 2: Update Decision Log

**Files:**
- Modify: `docs/decision_log.md`

- [ ] **Step 1: Add ADR for Environment Setup**
Document the tools and versions used.

```markdown
# Decision Log

## [2026-05-12] Environment Baseline (Project 0, Task 1)

**Decision:**
Standardize on JDK 25 and Kotlin 2.3 for the initial phase of the Mastery Path.

**Rationale:**
- JDK 25 is currently installed and exceeds the JDK 17 requirement for modern Android development.
- Kotlin 2.3 provides the latest stability and K2 compiler benefits.
- ADB is confirmed for Wear OS device/emulator bridging.

**Consequences:**
- All code must be compatible with Kotlin 2.x.
- Developer must manually enable K2 in Android Studio for consistent analysis.
```

---

### Task 3: Commit and Finalize

- [ ] **Step 1: Commit the changes**
Run: `git add . && git commit -m "env: initial sdk and compiler setup"`

- [ ] **Step 2: Final Verification**
Ensure all documentation reflects the actual state.
