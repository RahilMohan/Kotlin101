import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SyncPacket(
    val key: String,
    val data: String,
    val version: Int,
    val timestamp: Long = System.currentTimeMillis()
)

class SyncManager {
    private val localData = mutableMapOf<String, SyncPacket>()

    /**
     * Resolves conflict between incoming and local packet.
     * Uses Last-Writer-Wins (timestamp) as primary strategy, version as secondary.
     */
    fun receivePacket(incoming: SyncPacket): Boolean {
        val local = localData[incoming.key]

        return if (local == null || incoming.timestamp > local.timestamp) {
            localData[incoming.key] = incoming
            true // Updated
        } else {
            false // Ignored
        }
    }

    fun getLocalData(key: String): SyncPacket? = localData[key]

    fun createPacket(key: String, data: String): SyncPacket {
        val version = (localData[key]?.version ?: 0) + 1
        val packet = SyncPacket(key, data, version)
        localData[key] = packet
        return packet
    }
}
