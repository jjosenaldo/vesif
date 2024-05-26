package core.model

import core.model.visitor.ComponentVisitor

class RelayChangeoverContact(
    controller: ContactController = ContactController.DEFAULT,
    name: String,
    val isNormallyUp: Boolean,
    var soloNeighbor: Component = DEFAULT,
    var pairNeighbor1: Component = DEFAULT,
    var pairNeighbor2: Component = DEFAULT
) : Contact(controller, name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitRelayChangeoverContact(this)
    }
}
