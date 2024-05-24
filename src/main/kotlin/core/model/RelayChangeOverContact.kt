package core.model

import core.model.visitor.ComponentVisitor

class RelayChangeOverContact(controller: ContactController, id: String) : Contact(controller, id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        TODO("Not yet implemented")
    }
}
