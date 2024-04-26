package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

abstract class Component(val id: String) {
    abstract fun acceptVisitor(visitor: ComponentVisitor)

    companion object {
        val DEFAULT = object : Component("") {
            override fun acceptVisitor(visitor: ComponentVisitor) {}
        }

    }
}