package input

import core.model.Component

abstract class XmlCircuitComponent(
    val component: Component,
    private val attributes: List<Pair<String, String>>,
    val name: String = readAttribute(attributes, "Name") ?: "",
    val reference: String? = readAttribute(attributes, "Reference"),
    val connections: MutableMap<Int, XmlCircuitComponent> = mutableMapOf()
) {
    abstract fun setComponentConnections()

    companion object {
        private fun readAttribute(attributes: List<Pair<String, String>>, attribute: String): String? {
            return attributes.firstOrNull { it.first == attribute }?.second
        }
    }

}