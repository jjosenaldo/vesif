package core.model

import core.model.visitor.ComponentVisitor

class BistableSimpleContact(
    val isCloseSideLeft: Boolean,
    val isInitiallyOpen: Boolean,
    var leftNeighbor: Component = DEFAULT,
    var rightNeighbor: Component = DEFAULT,
    controller: BistableRelay = BistableRelay.DEFAULT,
    name: String,
) : Contact(controller, name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitBistableSimpleContact(this)
    }
}
