package core.model

import core.model.visitor.ComponentVisitor

class BistableRelay(
    name: String,
    var leftUpNeighbor: Component = DEFAULT,
    var leftDownNeighbor: Component = DEFAULT,
    var rightUpNeighbor: Component = DEFAULT,
    var rightDownNeighbor: Component = DEFAULT,
    contacts: List<Contact> = listOf()
) : ContactController(contacts, name) {
    val neighbors get() = listOf(leftUpNeighbor, leftDownNeighbor, rightUpNeighbor, rightDownNeighbor)

    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitBistableRelay(this)
    }
}