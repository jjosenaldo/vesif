package core.model

import core.model.visitor.ComponentVisitor

class ActivationBlock(id: String) : Component(id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        TODO("Not yet implemented")
    }
}