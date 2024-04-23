package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

class Capacitor(id: String) : Component(id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitCapacitor(this)
    }
}