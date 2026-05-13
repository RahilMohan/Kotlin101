import kotlin.collections.ArrayDeque

class RpnCalculator {
    private val stack = ArrayDeque<Double>()

    fun push(value: Double) {
        stack.addLast(value)
    }

    fun pop(): Double? {
        return if (stack.isNotEmpty()) stack.removeLast() else null
    }

    fun getStack(): List<Double> = stack.toList()

    fun clear() {
        stack.clear()
    }

    fun operate(symbol: String): String? {
        if (stack.size < 2) return "Error: Stack too small"

        val b = stack.removeLast()
        val a = stack.removeLast()

        val result = when (symbol) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> {
                if (b != 0.0) {
                    a / b
                } else {
                    stack.addLast(a)
                    stack.addLast(b)
                    return "Error: Division by zero"
                }
            }
            else -> {
                stack.addLast(a)
                stack.addLast(b)
                return "Error: Unknown operator"
            }
        }

        stack.addLast(result)
        return null
    }
}
