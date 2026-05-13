sealed class TileElement {
    data class Text(val content: String, val color: String = "white") : TileElement()
    data class Column(val children: List<TileElement>) : TileElement()
    data class CircularProgress(val progress: Float) : TileElement()
}

data class WorkoutMetrics(
    val duration: String,
    val calories: Int,
    val progress: Float
)

class TileLayoutFactory {

    fun buildWorkoutTile(metrics: WorkoutMetrics): TileElement {
        return TileElement.Column(
            listOf(
                TileElement.Text("Workout Active", color = "green"),
                TileElement.CircularProgress(metrics.progress),
                TileElement.Text(metrics.duration),
                TileElement.Text("${metrics.calories} kcal", color = "yellow")
            )
        )
    }

    // A simple DSL helper
    fun tileLayout(block: MutableList<TileElement>.() -> Unit): TileElement {
        val list = mutableListOf<TileElement>()
        list.block()
        return TileElement.Column(list)
    }
}
