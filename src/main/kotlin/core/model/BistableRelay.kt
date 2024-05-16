package core.model

import core.model.visitor.ComponentVisitor

class BistableRelay(id: String, val leftPlate: BistableRelayPlate, val rightPlate: BistableRelayPlate) : Component(id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        TODO("Not yet implemented")
    }
}