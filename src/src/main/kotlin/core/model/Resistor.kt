package core.model

import core.model.visitor.ComponentVisitor

class Resistor(
    name: String,
    var leftNeighbor: Component = DEFAULT,
    var rightNeighbor: Component = DEFAULT
) :
    Component(name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitResistor(this)
    }
}