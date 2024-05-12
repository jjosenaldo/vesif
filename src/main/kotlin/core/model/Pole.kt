package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class Pole(val isPositive: Boolean, var neighbor: Component, val isLeft: Boolean, name: String) : Component(name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitPole(this)
    }
}