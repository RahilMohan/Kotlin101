import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class HandAngles(
    val hour: Float,
    val minute: Float,
    val second: Float
)

class WatchFaceRenderer {

    /**
     * Calculates angles in radians for clock hands based on time.
     * 0 radians is at 3 o'clock, so we subtract PI/2 to start at 12.
     */
    fun calculateAngles(hour: Int, minute: Int, second: Int): HandAngles {
        val s = (second * 6 * (PI / 180) - PI / 2).toFloat()
        val m = (minute * 6 * (PI / 180) - PI / 2).toFloat()
        val h = ((hour % 12 + minute / 60f) * 30 * (PI / 180) - PI / 2).toFloat()

        return HandAngles(h, m, s)
    }

    fun getHandOffset(angle: Float, length: Float): Pair<Float, Float> {
        val x = cos(angle.toDouble()).toFloat() * length
        val y = sin(angle.toDouble()).toFloat() * length
        return x to y
    }
}
