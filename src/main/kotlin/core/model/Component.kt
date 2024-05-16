package core.model

import core.model.visitor.ComponentVisitor

abstract class Component(val name: String) {
    val id = "${name}_id"
    abstract fun acceptVisitor(visitor: ComponentVisitor)

    companion object {
        val DEFAULT = object : Component("") {
            override fun acceptVisitor(visitor: ComponentVisitor) {}
        }

        fun getNameFromId(id: String): String {
            return id.substring(0, id.length - 3)
        }
    }
}