package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

abstract class RelayContactController(var contacts: List<RelayContact>, id: String) : Component(id) {
    companion object {
        val DEFAULT = object : RelayContactController(listOf(), "") {
            override fun acceptVisitor(visitor: ComponentVisitor) {}
        }
    }
}