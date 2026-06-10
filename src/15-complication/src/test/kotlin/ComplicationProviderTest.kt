import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertNotNull

class ComplicationProviderTest {
    private val provider = SmartHomeComplicationProvider()

    @Test
    fun testInitialState() {
        val data = provider.getComplicationData()
        assertEquals("Lights", data.title)
        assertEquals("💡", data.icon)
        assertFalse(data.isActive)
    }

    @Test
    fun testToggle() {
        assertFalse(provider.getComplicationData().isActive)
        provider.toggleState()
        assertTrue(provider.getComplicationData().isActive)
    }

    @Test
    fun testMultipleToggles() {
        assertFalse(provider.getComplicationData().isActive)

        provider.toggleState()
        assertTrue(provider.getComplicationData().isActive)

        provider.toggleState()
        assertFalse(provider.getComplicationData().isActive)

        provider.toggleState()
        assertTrue(provider.getComplicationData().isActive)
    }

    @Test
    fun testToggleReturnsNewState() {
        val result = provider.toggleState()
        assertTrue(result.isActive)
        assertEquals("Lights", result.title)
        assertEquals("💡", result.icon)
    }

    @Test
    fun testUpdateText() {
        val msg = provider.onUpdateRequested()
        assertTrue(msg.contains("OFF"))
        assertTrue(msg.contains("SHORT_TEXT"))

        provider.toggleState()
        val msg2 = provider.onUpdateRequested()
        assertTrue(msg2.contains("ON"))
    }

    @Test
    fun testUpdateTextAfterMultipleToggles() {
        // Start OFF
        val msg1 = provider.onUpdateRequested()
        assertTrue(msg1.contains("OFF"))

        // Toggle to ON
        provider.toggleState()
        val msg2 = provider.onUpdateRequested()
        assertTrue(msg2.contains("ON"))

        // Toggle back to OFF
        provider.toggleState()
        val msg3 = provider.onUpdateRequested()
        assertTrue(msg3.contains("OFF"))
    }

    @Test
    fun testImmutabilityOfComplicationState() {
        val state1 = provider.getComplicationData()
        provider.toggleState()
        val state2 = provider.getComplicationData()

        // Original state should be unchanged
        assertFalse(state1.isActive)
        assertTrue(state2.isActive)
    }

    @Test
    fun testComplicationStateDataClass() {
        val state = ComplicationState("Test", "!!", true)
        assertEquals("Test", state.title)
        assertEquals("!!", state.icon)
        assertTrue(state.isActive)
    }

    @Test
    fun testUpdateRequestFormat() {
        val msg = provider.onUpdateRequested()
        assertTrue(msg.startsWith("Complication Type:"))
        assertTrue(msg.contains("Value:"))
    }
}
