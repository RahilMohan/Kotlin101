import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RpnCalculatorTest {
    @Test
    fun testBasicAddition() {
        val calc = RpnCalculator()
        calc.push(5.0)
        calc.push(10.0)
        val error = calc.operate("+")
        
        assertEquals(null, error)
        assertEquals(listOf(15.0), calc.getStack())
    }

    @Test
    fun testDivisionByZero() {
        val calc = RpnCalculator()
        calc.push(10.0)
        calc.push(0.0)
        val error = calc.operate("/")
        
        assertEquals("Error: Division by zero", error)
        assertEquals(listOf(10.0, 0.0), calc.getStack())
    }

    @Test
    fun testEmptyStack() {
        val calc = RpnCalculator()
        calc.push(5.0)
        val error = calc.operate("*")
        assertTrue(error?.contains("Stack too small") == true)
    }
}
