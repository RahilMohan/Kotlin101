# Design: Mordant RPN Calculator (Project 1)

**Goal:** Create a simple, aesthetically pleasing Reverse Polish Notation (RPN) calculator to master core Kotlin fundamentals.

## Architecture
- **Terminal UI:** `Mordant` library for colored output, tables, and styled prompts.
- **Data Structure:** `ArrayDeque<Double>` to act as the calculation stack.
- **Loop:** A simple `while(true)` loop that reads user input from `stdin`.

## Data Flow
1. User enters a string (number, operator, or command).
2. **Numbers:** Pushed onto the stack.
3. **Operators (`+`, `-`, `*`, `/`):** Pop the top two elements, perform operation, push result.
4. **Commands (`c`, `q`):** Clear stack or Quit.
5. **Rendering:** After each input, Mordant clears the screen (or prints a separator) and renders the current stack in a styled table.

## Logic Decisions
- **Null Safety:** Use `toDoubleOrNull()` to handle invalid number inputs without crashing.
- **Stack Safety:** Check if stack size >= 2 before performing operations. Use Kotlin's `if` or `when` for this check.
- **Operator Selection:** Use a `when` expression for the most idiomatic Kotlin approach.

## Testing Strategy
- **Unit Tests:** Verify the `calculate` logic independently of the CLI loop.
- **Cases:** Basic addition, division by zero, empty stack handling, invalid input parsing.
