package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

/**
 * Starts unpressed
 */
class Button(val leftNeighbor: Component, val rightNeighbor: Component, id: String) : Component(id) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitButton(this)
    }

    fun copyWith(leftNeighbor: Component? = null, rightNeighbor: Component? = null): Button {
        return Button(
            id = id,
            leftNeighbor = leftNeighbor ?: this.leftNeighbor,
            rightNeighbor = rightNeighbor ?: this.rightNeighbor
        )
    }
}