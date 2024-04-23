package org.example.core.model

import org.example.core.model.visitor.ComponentVisitor

abstract class Component(val id: String) {
    abstract fun acceptVisitor(visitor: ComponentVisitor)
}