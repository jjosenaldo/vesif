package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class RelayChangeOverContact(controller: RelayContactController, id: String) : RelayContact(controller, id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        TODO("Not yet implemented")
    }
}
