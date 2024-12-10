package core.model

import core.model.visitor.ComponentVisitor

class MonostableRelay(
    var leftNeighbor: Component = Component.DEFAULT,
    var rightNeighbor: Component = Component.DEFAULT,
    contacts: List<Contact> = listOf(),
    name: String,
) :
    ContactController(contacts, name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitMonostableRelay(this)
    }
}