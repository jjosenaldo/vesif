package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class ActivationBlock(id: String) : Component(id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        TODO("Not yet implemented")
    }
}