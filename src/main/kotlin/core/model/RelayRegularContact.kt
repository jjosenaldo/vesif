package core.model

import core.model.visitor.ComponentVisitor

class RelayRegularContact(
    val isNormallyOpen: Boolean,
    var leftNeighbor: Component = DEFAULT,
    var rightNeighbor: Component = DEFAULT,
    controller: ContactController = ContactController.DEFAULT,
    name: String,
) : Contact(controller, true, name) {
    val endpoint = object : Component(name = "${name}_ENDPOINT") {
        override fun acceptVisitor(visitor: ComponentVisitor) {}
    }

    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitRelayRegularContact(this)
    }
}
