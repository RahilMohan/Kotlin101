# Debugger & AI Agent Integration

This guide covers the essential tools and shortcuts for a professional Wear OS development workflow, focusing on the Android Studio Debugger and Gemini AI integration.

## 1. Android Studio Debugger Shortcuts

Mastering these hotkeys is essential for efficient "inner loop" development.

| Action | macOS | Windows / Linux | Description |
| :--- | :--- | :--- | :--- |
| **Step Over** | `F8` | `F8` | Execute the current line and move to the next. |
| **Step Into** | `F7` | `F7` | Enter the function/method on the current line. |
| **Force Step Into** | `⌥` + `⇧` + `F7` | `Alt` + `Shift` + `F7` | Step into code even if it's a library or excluded. |
| **Step Out** | `⇧` + `F8` | `Shift` + `F8` | Finish the current function and return to the caller. |
| **Resume Program** | `⌥` + `⌘` + `R` | `F9` | Continue execution until the next breakpoint. |
| **Evaluate Expression** | `⌥` + `F8` | `Alt` + `F8` | Open a dialog to execute code in the current context. |
| **Toggle Breakpoint** | `⌘` + `F8` | `Ctrl` + `F8` | Set or remove a breakpoint on the current line. |
| **View Breakpoints** | `⇧` + `⌘` + `F8` | `Ctrl` + `Shift` + `F8` | Open the Breakpoints management window. |

---

## 2. ADB over Wi-Fi (Wear OS)

Wear OS devices are often untethered. Use Wireless Debugging for a more comfortable development experience.

### Prerequisites
- Watch and Workstation must be on the **same Wi-Fi network**.
- Developer Options enabled on the watch (Settings > System > About > Versions > Tap **Build Number** 7 times).

### Step-by-Step Connection
1. **Enable Debugging**: On the watch, go to **Settings > Developer Options**.
2. **Enable ADB Debugging** and **Wireless Debugging**.
3. **Pairing (First Time Only)**:
   - Tap **Wireless Debugging > Pair new device**.
   - Note the **IP address & Port** (Pairing Server) and the **Pairing Code**.
   - In your terminal, run:
     ```bash
     adb pair <IP_ADDRESS>:<PORT>
     ```
   - Enter the pairing code when prompted.
4. **Connecting**:
   - Go back to the main **Wireless Debugging** screen.
   - Note the **IP address & Port** (this port is usually different from the pairing port).
   - In your terminal, run:
     ```bash
     adb connect <IP_ADDRESS>:<PORT>
     ```
5. **Verify**: Run `adb devices` to ensure your watch appears in the list.

---

## 3. Gemini in Android Studio

Gemini is your AI-powered coding assistant integrated directly into the IDE.

### Setup
- Open Gemini via **View > Tool Windows > Gemini**.
- Log in with your Google account.
- Ensure "Context Awareness" is enabled for better project-specific suggestions.

### Effective Usage Patterns
- **Code Explanation**: Highlight a complex Kotlin function, right-click, and select **Gemini > Explain Code**.
- **Unit Test Generation**: Use Gemini to scaffold tests for your logic: "Generate a JUnit test for this ViewModel."
- **Refactoring**: Ask Gemini to optimize a block of code: "Refactor this to use Kotlin Coroutines/Flow."
- **Bug Diagnosis**: Paste stack traces into the Gemini window to get potential root causes and fixes.

---

## 4. Pro Tip: Logcat Filtering
When debugging Wear OS, the logs can be noisy. Use the `package:mine` filter and the `level:error` or `tag:YOUR_TAG` to focus on what matters.
