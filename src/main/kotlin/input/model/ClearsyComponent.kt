package input.model

import core.model.Component
import core.model.PositionDouble

data class ClearsyComponent(val component: Component, val positions: List<PositionDouble>) {
    val id get() = component.id
}