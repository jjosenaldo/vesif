package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class BistableRelay(id: String, val leftPlate: BistableRelayPlate, val rightPlate: BistableRelayPlate) : Component(id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        TODO("Not yet implemented")
    }
}