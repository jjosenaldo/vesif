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

    fun copyWith(
        leftNeighbor: Component? = null,
        rightNeighbor: Component? = null,
        contacts: List<RelayContact>? = null
    ): MonostableRelay {
        return MonostableRelay(
            id = id,
            contacts = contacts ?: this.contacts,
            leftNeighbor = leftNeighbor ?: this.leftNeighbor,
            rightNeighbor = rightNeighbor ?: this.rightNeighbor
        )
    }
}