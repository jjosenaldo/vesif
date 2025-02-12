package core.model

import core.model.visitor.ComponentVisitor

abstract class ContactController(open var contacts: List<Contact>, id: String) : Component(id) {
    companion object {
        val DEFAULT = object : ContactController(listOf(), "") {
            override fun acceptVisitor(visitor: ComponentVisitor) {}
        }
    }
}