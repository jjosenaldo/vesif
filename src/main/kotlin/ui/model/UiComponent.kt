package ui.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import core.model.Component
import core.model.Identifiable
import core.model.PositionDouble
import input.model.ClearsyCircuit
import input.model.ClearsyComponent
import input.model.toClearsyComponent
import input.model.toClearsyComponents
import ui.common.distanceBetween
import ui.common.middlePoint
import ui.common.toOffset
import kotlin.math.max

data class UiComponent(val positions: List<PositionDouble>, val component: Component) {
    val name = component.name

    fun center(canvasSize: Size): Offset {
        return middlePoint(positions).toOffset(canvasSize)
    }

    fun circle(canvasSize: Size): UiCircleInfo {
        val center = center(canvasSize)
        val radius = max(
            // Needed because the component may have only 1 point, and in this case
            // it would be the center itself, thus the distance would be 0
            10f,
            positions.maxOf { distanceBetween(it.toOffset(canvasSize), center) } * 1.5f
        )

        return UiCircleInfo(center, radius)
    }

    companion object {
        fun fromClearsyComponent(component: ClearsyComponent): UiComponent {
            return UiComponent(component.positions, component.component)
        }
    }
}

fun Identifiable.toUiComponent(circuit: ClearsyCircuit): UiComponent? {
    return toClearsyComponent(circuit)?.let { UiComponent.fromClearsyComponent(it) }
}

fun List<Identifiable>.toUiComponents(circuit: ClearsyCircuit): List<UiComponent> {
    return toClearsyComponents(circuit).map(UiComponent::fromClearsyComponent)
}