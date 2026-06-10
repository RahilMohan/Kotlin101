import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class RpnCalculatorTest {
    private val calc = RpnCalculator()

    @Test
    fun testPushAndPop() {
        calc.push(42.0)
        assertEquals(listOf(42.0), calc.getStack())
        assertEquals(42.0, calc.pop())
        assertNull(calc.pop())
    }

    @Test
    fun testPushMultipleAndGetStack() {
        calc.push(1.0)
        calc.push(2.0)
        calc.push(3.0)
        assertEquals(listOf(1.0, 2.0, 3.0), calc.getStack())
    }

    @Test
    fun testClear() {
        calc.push(1.0)
        calc.push(2.0)
        calc.clear()
        assertTrue(calc.getStack().isEmpty())
        assertNull(calc.pop())
    }

    @Test
    fun testBasicAddition() {
        calc.push(5.0)
        calc.push(10.0)
        val error = calc.operate("+")
        assertNull(error)
        assertEquals(listOf(15.0), calc.getStack())
    }

    @Test
    fun testSubtraction() {
        calc.push(10.0)
        calc.push(3.0)
        val error = calc.operate("-")
        assertNull(error)
        assertEquals(listOf(7.0), calc.getStack())
    }

    @Test
    fun testMultiplication() {
        calc.push(6.0)
        calc.push(7.0)
        val error = calc.operate("*")
        assertNull(error)
        assertEquals(listOf(42.0), calc.getStack())
    }

    @Test
    fun testDivision() {
        calc.push(15.0)
        calc.push(3.0)
        val error = calc.operate("/")
        assertNull(error)
        assertEquals(listOf(5.0), calc.getStack())
    }

    @Test
    fun testDivisionByZero() {
        calc.push(10.0)
        calc.push(0.0)
        val error = calc.operate("/")
        assertEquals("Error: Division by zero", error)
        assertEquals(listOf(10.0, 0.0), calc.getStack())
    }

    @Test
    fun testEmptyStackError() {
        calc.push(5.0)
        val error = calc.operate("*")
        assertTrue(error?.contains("Stack too small") == true)
        assertEquals(listOf(5.0), calc.getStack())
    }

    @Test
    fun testStackSizeOneError() {
        calc.push(7.0)
        val error = calc.operate("+")
        assertEquals("Error: Stack too small", error)
        assertEquals(listOf(7.0), calc.getStack())
    }

    @Test
    fun testUnknownOperatorError() {
        calc.push(1.0)
        calc.push(2.0)
        val error = calc.operate("^")
        assertEquals("Error: Unknown operator", error)
        assertEquals(listOf(1.0, 2.0), calc.getStack())
    }

    @Test
    fun testChainedOperations() {
        calc.push(2.0)
        calc.push(3.0)
        calc.operate("*") // 6
        calc.push(4.0)
        calc.operate("+") // 10
        calc.push(2.0)
        calc.operate("/") // 5
        assertEquals(listOf(5.0), calc.getStack())
    }

    @Test
    fun testNegativeNumbers() {
        calc.push(-5.0)
        calc.push(-3.0)
        calc.operate("+")
        assertEquals(listOf(-8.0), calc.getStack())
    }

    @Test
    fun testFloatingPointPrecision() {
        calc.push(0.1)
        calc.push(0.2)
        calc.operate("+")
        val result = calc.getStack().first()
        assertTrue(result in 0.29..0.31, "Expected ~0.3 but got $result")
    }
}
