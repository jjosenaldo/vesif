package core.model

import core.model.visitor.ComponentVisitor

class RelayChangeOverContact(
    controller: ContactController = ContactController.DEFAULT,
    name: String,
    val isNormallyUp: Boolean,
    var leftNeighbor: Component = DEFAULT,
    var upNeighbor: Component = DEFAULT,
    var downNeighbor: Component = DEFAULT
) : Contact(controller, name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitRelayChangeOverContact(this)
    }
}
