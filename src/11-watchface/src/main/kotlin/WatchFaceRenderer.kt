import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class Point(val x: Float, val y: Float)

class WatchFaceRenderer(private val centerX: Float, private val centerY: Float) {

    fun calculateHandEndpoint(angleDegrees: Float, length: Float): Point {
        val angleRadians = (angleDegrees - 90) * (PI / 180).toFloat()
        val x = centerX + length * cos(angleRadians.toDouble()).toFloat()
        val y = centerY + length * sin(angleRadians.toDouble()).toFloat()
        return Point(x, y)
    }

    fun getSecondHandAngle(seconds: Int): Float = seconds * 6f
    fun getMinuteHandAngle(minutes: Int, seconds: Int): Float = minutes * 6f + (seconds / 10f)
    fun getHourHandAngle(hours: Int, minutes: Int): Float = (hours % 12) * 30f + (minutes / 2f)

    fun render(hours: Int, minutes: Int, seconds: Int, isAmbient: Boolean): String {
        val hAngle = getHourHandAngle(hours, minutes)
        val mAngle = getMinuteHandAngle(minutes, seconds)
        val sAngle = getSecondHandAngle(seconds)

        val builder = StringBuilder()
        builder.append("Rendering Watch Face (Ambient: $isAmbient)\n")
        builder.append("Time: %02d:%02d:%02d\n".format(hours, minutes, seconds))
        builder.append("Hour Hand Angle: $hAngle°\n")
        builder.append("Minute Hand Angle: $mAngle°\n")
        if (!isAmbient) {
            builder.append("Second Hand Angle: $sAngle°\n")
        } else {
            builder.append("Second Hand Hidden (Power Save)\n")
        }
        return builder.toString()
    }
}
