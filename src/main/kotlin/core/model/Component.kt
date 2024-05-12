package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

abstract class Component(val name: String) {
    val id = "${name}_id"
    abstract fun acceptVisitor(visitor: ComponentVisitor)

    companion object {
        val DEFAULT = object : Component("") {
            override fun acceptVisitor(visitor: ComponentVisitor) {}
        }

    }
}