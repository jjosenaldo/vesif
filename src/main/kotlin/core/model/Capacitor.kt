package core.model

import core.model.visitor.ComponentVisitor

class Capacitor(
    name: String,
    val maxCharge: Int,
    val initialCharge: Int,
    var leftNeighbor: Component = DEFAULT,
    var rightNeighbor: Component = DEFAULT
) : Component(name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitCapacitor(this)
    }
}