package core.model

import core.model.visitor.ComponentVisitor

class LeverContact(
    name: String,
    var isLeftOpen: Boolean,
    lever: Lever = Lever.DEFAULT,
    var leftNeighbor: Component = DEFAULT,
    var rightNeighbor: Component = DEFAULT
) : Contact(name = name, controller = lever) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitLeverContact(this)
    }
}