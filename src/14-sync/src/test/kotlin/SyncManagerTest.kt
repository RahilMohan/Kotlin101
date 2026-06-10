import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SyncManagerTest {

    @Test
    fun testCreatePacket() {
        val manager = SyncManager()
        val packet = manager.createPacket("profile", "User Data")

        assertEquals("profile", packet.key)
        assertEquals("User Data", packet.data)
        assertEquals(1, packet.version)
        assertTrue(packet.timestamp > 0)
    }

    @Test
    fun testConflictResolutionNewerTimestampWins() {
        val manager = SyncManager()
        val key = "profile"

        val p1 = manager.createPacket(key, "User A")
        val pOld = SyncPacket(key, "Old Data", 1, timestamp = p1.timestamp - 100)
        assertFalse(manager.receivePacket(pOld))
        assertEquals("User A", manager.getLocalData(key)?.data)

        val pNew = SyncPacket(key, "User B", 2, timestamp = p1.timestamp + 100)
        assertTrue(manager.receivePacket(pNew))
        assertEquals("User B", manager.getLocalData(key)?.data)
    }

    @Test
    fun testVersionAutoIncrement() {
        val manager = SyncManager()
        val key = "doc"

        val p1 = manager.createPacket(key, "V1")
        assertEquals(1, p1.version)

        val p2 = manager.createPacket(key, "V2")
        assertEquals(2, p2.version)

        val p3 = manager.createPacket(key, "V3")
        assertEquals(3, p3.version)
    }

    @Test
    fun testGetLocalDataForMissingKey() {
        val manager = SyncManager()
        assertNull(manager.getLocalData("nonexistent"))
    }

    @Test
    fun testReceivePacketForNewKey() {
        val manager = SyncManager()
        val packet = SyncPacket("newKey", "New Data", 1, timestamp = 1000)

        assertTrue(manager.receivePacket(packet))
        val stored = manager.getLocalData("newKey")
        assertNotNull(stored)
        assertEquals("New Data", stored.data)
    }

    @Test
    fun testMultipleReceivesForSameKey() {
        val manager = SyncManager()
        val key = "multi"

        val p1 = SyncPacket(key, "First", 1, timestamp = 100)
        assertTrue(manager.receivePacket(p1))

        val p2 = SyncPacket(key, "Second", 2, timestamp = 200)
        assertTrue(manager.receivePacket(p2))

        val p3 = SyncPacket(key, "Old", 3, timestamp = 50)
        assertFalse(manager.receivePacket(p3))

        assertEquals("Second", manager.getLocalData(key)?.data)
    }

    @Test
    fun testMultipleIndependentKeys() {
        val manager = SyncManager()

        manager.createPacket("a", "Data A")
        manager.createPacket("b", "Data B")
        manager.createPacket("c", "Data C")

        assertEquals("Data A", manager.getLocalData("a")?.data)
        assertEquals("Data B", manager.getLocalData("b")?.data)
        assertEquals("Data C", manager.getLocalData("c")?.data)

        // Versions are independent per key
        assertEquals(1, manager.getLocalData("a")?.version)
        assertEquals(1, manager.getLocalData("b")?.version)
        assertEquals(1, manager.getLocalData("c")?.version)
    }

    @Test
    fun testSyncPacketSerialization() {
        val packet = SyncPacket("key", "value", 5, timestamp = 1234567890)
        assertEquals("key", packet.key)
        assertEquals("value", packet.data)
        assertEquals(5, packet.version)
        assertEquals(1234567890, packet.timestamp)
    }
}
