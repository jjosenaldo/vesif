package presentation.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import core.model.Component
import core.model.PositionDouble
import input.model.ClearsyComponent
import presentation.distanceBetween
import presentation.middlePoint
import presentation.toOffset

data class UiComponent(val positions: List<PositionDouble>, val component: Component) {
    val name = component.name

    fun circle(canvasSize: Size): UiCircleInfo {
        val center: Offset
        val radius: Float

        when (positions.size) {
            1 -> {
                center = positions.first().toOffset(canvasSize)
                radius = 50f
            }

            2 -> {
                center = middlePoint(positions[0], positions[1]).toOffset(canvasSize)
                radius = distanceBetween(positions[0].toOffset(canvasSize), positions[1].toOffset(canvasSize)) / 2
            }

            else -> TODO()
        }

        return UiCircleInfo(center, radius)
    }

    companion object {
        fun fromClearsyComponent(component: ClearsyComponent): UiComponent {
            return UiComponent(component.positions, component.component)
        }
    }
}