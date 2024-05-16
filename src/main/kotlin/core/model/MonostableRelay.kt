package core.model

import core.model.visitor.ComponentVisitor

class MonostableRelay(
    var leftNeighbor: Component = Component.DEFAULT,
    var rightNeighbor: Component = Component.DEFAULT,
    contacts: List<RelayContact> = listOf(),
    name: String,
) :
    RelayContactController(contacts, name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitMonostableRelay(this)
    }
}