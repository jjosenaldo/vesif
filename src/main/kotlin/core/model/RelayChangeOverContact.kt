package core.model

import core.model.visitor.ComponentVisitor

class RelayChangeOverContact(controller: RelayContactController, id: String) : RelayContact(controller, id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        TODO("Not yet implemented")
    }
}
