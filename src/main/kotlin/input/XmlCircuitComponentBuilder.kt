package input

import core.model.*

abstract class XmlCircuitComponentBuilder {
    abstract fun buildXmlComponent(attributes: Map<String, String>): XmlCircuitComponent
}

class XmlButtonBuilder : XmlCircuitComponentBuilder() {
    override fun buildXmlComponent(attributes: Map<String, String>): XmlCircuitComponent {
        val button = Button(name = attributes["Name"] ?: "")

        return object : XmlCircuitComponent(button, attributes) {
            override fun setComponentConnections() {
                connections[0]?.let { button.leftNeighbor = it.component }
                connections[1]?.let { button.rightNeighbor = it.component }
            }
        }
    }
}

class XmlPoleBuilder(private val isPositive: Boolean) : XmlCircuitComponentBuilder() {
    override fun buildXmlComponent(attributes: Map<String, String>): XmlCircuitComponent {
        val pole = Pole(name = attributes["Name"] ?: "", isPositive = isPositive)

        return object : XmlCircuitComponent(pole, attributes) {
            override fun setComponentConnections() {
                connections[0]?.let { pole.neighbor = it.component }
            }
        }
    }

}

class XmlMonostableRelayBuilder : XmlCircuitComponentBuilder() {
    override fun buildXmlComponent(attributes: Map<String, String>): XmlCircuitComponent {
        val relay = MonostableRelay(name = attributes["Name"] ?: "")

        return object : XmlCircuitComponent(relay, attributes) {
            override fun setComponentConnections() {
                connections[0]?.let { relay.leftNeighbor = it.component }
                connections[1]?.let { relay.rightNeighbor = it.component }
            }
        }
    }
}

class XmlRelayRegularContactBuilder(private val isNormallyOpen: Boolean) : XmlCircuitComponentBuilder() {
    override fun buildXmlComponent(attributes: Map<String, String>): XmlCircuitComponent {
        val contact = RelayRegularContact(name = attributes["Name"] ?: "", isNormallyOpen = isNormallyOpen)

        return object : XmlCircuitComponent(contact, attributes) {
            override fun setComponentConnections() {
                connections[0]?.let { contact.leftNeighbor = it.component }
                connections[1]?.let { contact.rightNeighbor = it.component }
            }
        }
    }
}

class XmlJunctionBuilder : XmlCircuitComponentBuilder() {
    override fun buildXmlComponent(attributes: Map<String, String>): XmlCircuitComponent {
        val junction = Junction(name = attributes["Name"] ?: "")

        return object : XmlCircuitComponent(junction, attributes) {
            override fun setComponentConnections() {
                connections[0]?.let { junction.leftNeighbor = it.component }
                connections[1]?.let { junction.upNeighbor = it.component }
                connections[2]?.let { junction.downNeighbor = it.component }
            }
        }
    }
}

