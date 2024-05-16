package core.model

import core.model.visitor.ComponentVisitor

class RelayRegularContact(
    val isNormallyOpen: Boolean,
    var leftNeighbor: Component = DEFAULT,
    var rightNeighbor: Component = DEFAULT,
    controller: RelayContactController = RelayContactController.DEFAULT,
    name: String,
) : RelayContact(controller, name) {
    val endpointName = "${name}_ENDPOINT"
    val endpointId = "${endpointName}_id"

    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitRelayRegularContact(this)
    }
}
