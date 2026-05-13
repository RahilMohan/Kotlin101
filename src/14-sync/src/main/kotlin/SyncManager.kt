import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class SyncSettings(
    val theme: String = "dark",
    val notificationsEnabled: Boolean = true,
    val targetSteps: Int = 10000
)

@Serializable
data class SyncPacket(
    val type: String,
    val payload: String,
    val sequenceNumber: Long
)

class SyncManager {
    private var lastSyncedSettings: SyncSettings? = null
    private var sequence = 0L

    fun createSyncPacket(newSettings: SyncSettings): SyncPacket? {
        // Simple diffing logic
        if (newSettings == lastSyncedSettings) return null

        val payload = Json.encodeToString(newSettings)
        val packet = SyncPacket("SETTINGS_UPDATE", payload, sequence++)
        
        lastSyncedSettings = newSettings
        return packet
    }

    fun packetToBytes(packet: SyncPacket): ByteArray {
        return Json.encodeToString(packet).toByteArray(Charsets.UTF_8)
    }

    fun bytesToPacket(bytes: ByteArray): SyncPacket {
        val json = String(bytes, Charsets.UTF_8)
        return Json.decodeFromString(json)
    }

    fun applyPacket(packet: SyncPacket): SyncSettings? {
        if (packet.type == "SETTINGS_UPDATE") {
            return Json.decodeFromString<SyncSettings>(packet.payload)
        }
        return null
    }
}
