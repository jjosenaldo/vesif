package core.model

import core.model.visitor.ComponentVisitor

class Capacitor(id: String) : Component(id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitCapacitor(this)
    }
}