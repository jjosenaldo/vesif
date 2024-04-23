package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class MonostableRelay(
    val leftNeighbor: Component,
    val rightNeighbor: Component, contacts: List<RelayContact>, id: String,
) :
    RelayContactController(contacts, id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitMonostableRelay(this)
    }
}