package core.model

import core.model.visitor.ComponentVisitor

class Lamp(
    name: String,
    var leftNeighbor: Component = DEFAULT,
    var rightNeighbor: Component = DEFAULT
) : BinaryOutput, Component(name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitLamp(this)
    }
}