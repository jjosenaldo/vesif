package core.model

import core.model.visitor.ComponentVisitor

class Junction(
    var leftNeighbor: Component = DEFAULT,
    var upNeighbor: Component = DEFAULT,
    var downNeighbor: Component = DEFAULT,
    name: String
) : Component(name) {

    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitJunction(this)
    }
}