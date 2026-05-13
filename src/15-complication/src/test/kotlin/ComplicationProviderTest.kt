import kotlin.test.*

class ComplicationProviderTest {
    private val provider = ComplicationProvider()

    @Test
    fun testInitialState() {
        val data = provider.onUpdate()
        assertEquals("OFF", data.shortText)
        assertEquals("ic_light_off", data.iconId)
    }

    @Test
    fun testToggleLogic() {
        // Tap 1
        provider.onUserTap()
        val data1 = provider.onUpdate()
        assertEquals("ON", data1.shortText)
        assertTrue(provider.getStatus())

        // Tap 2
        provider.onUserTap()
        val data2 = provider.onUpdate()
        assertEquals("OFF", data2.shortText)
        assertFalse(provider.getStatus())
    }
}
