package input

import core.model.Component

abstract class XmlCircuitComponent(
    val component: Component,
    private val attributes: Map<String, String>,
    val name: String = attributes["Name"] ?: "",
    val reference: String? = attributes["Reference"],
    val connections: MutableMap<Int, XmlCircuitComponent> = mutableMapOf()
) {
    abstract fun setComponentConnections()
}