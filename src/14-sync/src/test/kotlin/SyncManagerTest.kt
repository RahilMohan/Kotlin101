import kotlin.test.*

class SyncManagerTest {
    private val manager = SyncManager()

    @Test
    fun testDiffingLogic() {
        val settings = SyncSettings(theme = "light")
        val p1 = manager.createSyncPacket(settings)
        assertNotNull(p1)
        
        // Sending same settings again should return null (no sync needed)
        val p2 = manager.createSyncPacket(settings)
        assertNull(p2)
        
        val newSettings = settings.copy(targetSteps = 5000)
        val p3 = manager.createSyncPacket(newSettings)
        assertNotNull(p3)
    }

    @Test
    fun testBinaryRoundTrip() {
        val packet = SyncPacket("TEST", "{}", 123)
        val bytes = manager.packetToBytes(packet)
        val decoded = manager.bytesToPacket(bytes)
        
        assertEquals(packet, decoded)
    }

    @Test
    fun testApplyUpdate() {
        val settings = SyncSettings(targetSteps = 8000)
        val packet = manager.createSyncPacket(settings)!!
        
        val result = manager.applyPacket(packet)
        assertEquals(8000, result?.targetSteps)
    }
}
