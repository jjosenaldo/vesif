package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class RelayRegularContact(
    val isNormallyOpen: Boolean,
    val leftNeighbor: Component,
    val rightNeighbor: Component, controller: RelayContactController, id: String,
) : RelayContact(controller, id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitRelayRegularContact(this)
    }
}
