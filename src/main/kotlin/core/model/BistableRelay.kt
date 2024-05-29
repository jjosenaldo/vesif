package core.model

import core.model.visitor.ComponentVisitor

class BistableRelay(
    name: String,
    var leftUpNeighbor: Component = Component.DEFAULT,
    var leftDownNeighbor: Component = Component.DEFAULT,
    var rightUpNeighbor: Component = Component.DEFAULT,
    var rightDownNeighbor: Component = Component.DEFAULT,
    contacts: List<Contact> = listOf()
) : ContactController(contacts, name) {
    val neighbors get() = listOf(leftUpNeighbor, leftDownNeighbor, rightUpNeighbor, rightDownNeighbor)

    companion object {
        val DEFAULT = BistableRelay(name = "")
    }

    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitBistableRelay(this)
    }
}