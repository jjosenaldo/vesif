package core.model

import core.model.visitor.ComponentVisitor

abstract class Component(override val name: String) : Identifiable {
    abstract fun acceptVisitor(visitor: ComponentVisitor)

    companion object {
        val DEFAULT = object : Component("") {
            override fun acceptVisitor(visitor: ComponentVisitor) {}
        }
    }
}