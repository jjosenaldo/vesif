package core.model

import core.model.visitor.ComponentVisitor

class RelayChangeOverContact(
    controller: ContactController = ContactController.DEFAULT,
    name: String,
    val isNormallyUp: Boolean,
    var leftNeighbor: Component = DEFAULT,
    var upNeighbor: Component = DEFAULT,
    var downNeighbor: Component = DEFAULT
) : Contact(controller, true, name) {
    val endpointUp = object : Component(name = "${name}_ENDPOINT_UP") {
        override fun acceptVisitor(visitor: ComponentVisitor) {}
    }
    val endpointDown = object : Component(name = "${name}_ENDPOINT_DOWN") {
        override fun acceptVisitor(visitor: ComponentVisitor) {}
    }

    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitRelayChangeOverContact(this)
    }
}
