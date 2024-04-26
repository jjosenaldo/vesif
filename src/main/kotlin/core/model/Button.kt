package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

/**
 * Starts unpressed
 */
class Button(var leftNeighbor: Component, var rightNeighbor: Component, id: String) : Component(id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitButton(this)
    }
}