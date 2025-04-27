package input.model

import core.model.PositionDouble
import input.XmlBendBuilder
import input.XmlCircuitComponent
import input.attributeChild
import org.w3c.dom.Element

data class XmlNodeTerminal(val component: XmlCircuitComponent, val index: Int, val positionDouble: PositionDouble) {
    companion object {
        fun fromElementAndObjects(element: Element, objects: List<XmlCircuitComponent>): XmlNodeTerminal? {
            val name = element.attributeChild("Object") ?: return null
            val index = element.attributeChild("Index")?.toInt() ?: return null
            val component = objects.firstOrNull { obj -> obj.name == name } ?: return null
            val x = element.attributeChild("OffsetX")?.toDouble() ?: return null
            val y = element.attributeChild("OffsetY")?.toDouble() ?: return null

            return XmlNodeTerminal(component = component, index = index, positionDouble = PositionDouble(x, y))
        }

        fun fromKnee(element: Element, name: String): Pair<XmlCircuitComponent, XmlNodeTerminal> {
            val x = element.attributeChild("OffsetX")?.toDouble() ?: 0.0
            val y = element.attributeChild("OffsetY")?.toDouble() ?: 0.0
            val component = XmlBendBuilder().buildXmlComponent(listOf(Pair("Name", name)))

            return Pair(
                component,
                XmlNodeTerminal(component = component, index = 0, positionDouble = PositionDouble(x, y))
            )
        }
    }

    fun updatePositions() {
        component.positions.add(positionDouble)
    }
}