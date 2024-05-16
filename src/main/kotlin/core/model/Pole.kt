package core.model

import core.model.visitor.ComponentVisitor

class Pole(
    val isPositive: Boolean,
    override var neighbor: Component = DEFAULT,
    name: String
) : Component(name),
    PowerSource {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitPole(this)
    }

    override val isPositiveSource = isPositive
}