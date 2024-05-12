package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class RelayRegularContact(
    val isNormallyOpen: Boolean,
    var leftNeighbor: Component,
    var rightNeighbor: Component, controller: RelayContactController, name: String,
) : RelayContact(controller, name) {
    val endpointName = "${name}_ENDPOINT"
    val endpointId = "${endpointName}_id"

    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitRelayRegularContact(this)
    }
}
