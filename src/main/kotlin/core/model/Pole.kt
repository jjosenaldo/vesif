package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class Pole(val isPositive: Boolean, val neighbor: Component, val isLeft: Boolean, id: String) : Component(id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitPole(this)
    }

    fun copyWith(neighbor: Component? = null): Pole {
        return Pole(id = id, isPositive = isPositive, neighbor = neighbor ?: this.neighbor, isLeft = isLeft)
    }
}