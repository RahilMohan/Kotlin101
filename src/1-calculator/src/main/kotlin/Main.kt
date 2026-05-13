import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.table.table

fun main() {
    val t = Terminal()
    val calc = RpnCalculator()
    
    t.println(brightBlue("--- Mordant RPN Calculator ---"))
    t.println("Enter numbers, operators (+, -, *, /), 'c' to clear, or 'q' to quit.")

    while (true) {
        // Render Stack
        val currentStack = calc.getStack()
        if (currentStack.isNotEmpty()) {
            t.println(table {
                header { row(cyan("Position"), cyan("Value")) }
                body {
                    currentStack.asReversed().forEachIndexed { index, value ->
                        row(index.toString(), yellow("%.2f".format(value)))
                    }
                }
            })
        }

        print(magenta("> "))
        val input = readLine()?.trim()?.lowercase() ?: break

        when (input) {
            "q", "quit" -> {
                t.println(brightBlue("Goodbye!"))
                break
            }
            "c", "clear" -> {
                calc.clear()
                t.println(gray("Stack cleared."))
            }
            "+", "-", "*", "/" -> {
                val error = calc.operate(input)
                if (error != null) t.println(red(error))
            }
            else -> {
                val num = input.toDoubleOrNull()
                if (num != null) {
                    calc.push(num)
                } else {
                    t.println(red("Error: Invalid input '$input'"))
                }
            }
        }
    }
}
