package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class MonostableRelay(
    var leftNeighbor: Component,
    var rightNeighbor: Component, contacts: List<RelayContact>, name: String,
) :
    RelayContactController(contacts, name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitMonostableRelay(this)
    }
}