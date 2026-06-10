data class TileMetrics(
    val title: String,
    val value: String,
    val description: String,
    val colorHex: String
)

class TileLayoutFactory {

    /**
     * Simulates the creation of a ProtoLayout structure.
     * In a real Wear OS app, this would return a Layout object.
     */
    fun createWorkoutTile(metrics: TileMetrics): String {
        return """
            Layout(
                Column(
                    Text("${metrics.title}", size=Small),
                    Text("${metrics.value}", size=Large, color="${metrics.colorHex}"),
                    Text("${metrics.description}", size=Caption)
                )
            )
        """.trimIndent()
    }
}
