package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class BistableRelayPlate(contacts: List<RelayContact>, id: String) : RelayContactController(contacts, id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        TODO("Not yet implemented")
    }
}