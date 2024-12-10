package core.model

import core.model.visitor.ComponentVisitor

class Lever(name: String, leverContacts: List<LeverContact> = listOf()) : BinaryInput,
    ContactController(id = name, contacts = leverContacts) {
    companion object {
        val DEFAULT = Lever(name = "")
    }

    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitLever(this)
    }
}