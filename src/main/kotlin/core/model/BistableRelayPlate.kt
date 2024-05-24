package core.model

import core.model.visitor.ComponentVisitor

class BistableRelayPlate(contacts: List<Contact>, id: String) : ContactController(contacts, id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        TODO("Not yet implemented")
    }
}