package core.model

import core.model.visitor.ComponentVisitor

class Pole(
    val isPositive: Boolean,
    name: String,
    override var neighbor: Component = Component.DEFAULT
) : Component(name),
    PowerSource {
    companion object {
        val DEFAULT = Pole(isPositive = true, name = "")
    }

    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitPole(this)
    }

    override val isPositiveSource = isPositive
}