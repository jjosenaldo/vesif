package ui.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import core.model.PositionDouble
import kotlin.math.pow
import kotlin.math.sqrt

fun middlePoint(positions: List<PositionDouble>): PositionDouble {
    val sumX = positions.fold(0.0) { acc, position -> position.x + acc }
    val sumY = positions.fold(0.0) { acc, position -> position.y + acc }

    return PositionDouble(sumX / positions.size, sumY / positions.size)
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