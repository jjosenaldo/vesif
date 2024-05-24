package input.model

import input.XmlCircuitComponent
import input.attributeChild
import org.w3c.dom.Element

data class XmlNodeTerminal(val component: XmlCircuitComponent, val index: Int) {
    companion object {
        fun fromElementAndObjects(element: Element, objects: List<XmlCircuitComponent>): XmlNodeTerminal? {
            val name = element.attributeChild("Object") ?: return null
            val index = element.attributeChild("Index")?.toInt() ?: return null
            val component = objects.firstOrNull { obj -> obj.name == name } ?: return null

            return XmlNodeTerminal(component = component, index = index)
        }
    }
}