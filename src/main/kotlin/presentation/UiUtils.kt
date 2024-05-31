package presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import core.model.PositionDouble
import kotlin.math.pow
import kotlin.math.sqrt

fun middlePoint(position1: PositionDouble, position2: PositionDouble): PositionDouble {
    return PositionDouble(x = (position1.x + position2.x) / 2, y = (position1.y + position2.y) / 2)
}

fun distanceBetween(offset1: Offset, offset2: Offset): Float {
    return sqrt((offset1.x - offset2.x).pow(2) + (offset1.y - offset2.y).pow(2))
}

fun PositionDouble.toOffset(canvasSize: Size): Offset {
    return Offset(
        x = (canvasSize.width * x).toFloat(),
        y = (canvasSize.height * y).toFloat()
    )
}