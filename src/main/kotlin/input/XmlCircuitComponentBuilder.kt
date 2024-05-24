package input

import core.model.*
import input.model.XmlComponentAttributeException
import input.model.XmlComponentFieldException

abstract class XmlCircuitComponentBuilder(val className: String) {
    abstract fun buildXmlComponent(fields: List<Pair<String, String>>): XmlCircuitComponent

    fun readMandatoryAttributeAt(fields: List<Pair<String, String>>, index: Int): String {
        val attributes = fields.filter { it.first == "Attribute" }

        if (attributes.indices.contains(index).not()) {
            throw XmlComponentAttributeException(
                className = className,
                index = index,
                data = null
            )
        }

        return attributes[index].second
    }

    fun readMandatoryField(fields: List<Pair<String, String>>, field: String): String {
        return fields.firstOrNull { it.first == field }?.second ?: throw XmlComponentFieldException(
            className = className,
            name = field,
            data = null
        )
    }

    fun readName(fields: List<Pair<String, String>>) = readMandatoryField(fields, "Name")
}

class XmlButtonBuilder : XmlCircuitComponentBuilder("Button") {
    override fun buildXmlComponent(fields: List<Pair<String, String>>): XmlCircuitComponent {
        val button = Button(name = readName(fields))

        return object : XmlCircuitComponent(button, fields) {
            override fun setComponentConnections() {
                connections[0]?.let { button.leftNeighbor = it.component }
                connections[1]?.let { button.rightNeighbor = it.component }
            }
        }
    }
}

class XmlPoleBuilder(private val isPositive: Boolean) : XmlCircuitComponentBuilder("Pole") {
    override fun buildXmlComponent(fields: List<Pair<String, String>>): XmlCircuitComponent {
        val pole = Pole(name = readName(fields), isPositive = isPositive)

        return object : XmlCircuitComponent(pole, fields) {
            override fun setComponentConnections() {
                connections[0]?.let { pole.neighbor = it.component }
            }
        }
    }

}

class XmlMonostableRelayBuilder : XmlCircuitComponentBuilder("Monostable Relay") {
    override fun buildXmlComponent(fields: List<Pair<String, String>>): XmlCircuitComponent {
        val relay = MonostableRelay(name = readName(fields))

        return object : XmlCircuitComponent(relay, fields) {
            override fun setComponentConnections() {
                connections[0]?.let { relay.leftNeighbor = it.component }
                connections[1]?.let { relay.rightNeighbor = it.component }
            }
        }
    }
}

class XmlRelayRegularContactBuilder(private val isNormallyOpen: Boolean) : XmlCircuitComponentBuilder("Relay contact") {
    override fun buildXmlComponent(fields: List<Pair<String, String>>): XmlCircuitComponent {
        val contact = RelayRegularContact(name = readName(fields), isNormallyOpen = isNormallyOpen)

        return object : XmlCircuitComponent(contact, fields) {
            override fun setComponentConnections() {
                connections[0]?.let { contact.leftNeighbor = it.component }
                connections[1]?.let { contact.rightNeighbor = it.component }
            }
        }
    }
}

class XmlJunctionBuilder : XmlCircuitComponentBuilder("Junction") {
    override fun buildXmlComponent(fields: List<Pair<String, String>>): XmlCircuitComponent {
        val junction = Junction(name = readName(fields))

        return object : XmlCircuitComponent(junction, fields) {
            override fun setComponentConnections() {
                connections[0]?.let { junction.leftNeighbor = it.component }
                connections[1]?.let { junction.upNeighbor = it.component }
                connections[2]?.let { junction.downNeighbor = it.component }
            }
        }
    }
}

class XmlLeverBuilder : XmlCircuitComponentBuilder("Lever") {
    override fun buildXmlComponent(fields: List<Pair<String, String>>): XmlCircuitComponent {
        val lever = Lever(name = readName(fields))

        return object : XmlCircuitComponent(lever, fields) {
            override fun setComponentConnections() {}
        }
    }
}

class XmlLeverContactBuilder : XmlCircuitComponentBuilder("Lever contact") {
    private val openSideAttributeName = "Open side"
    private val openSideAttributeIndex = 0

    override fun buildXmlComponent(fields: List<Pair<String, String>>): XmlCircuitComponent {
        val openSide = parseOpenSide(readMandatoryAttributeAt(fields, openSideAttributeIndex))
        val contact = LeverContact(name = readName(fields), isLeftOpen = openSide)

        return object : XmlCircuitComponent(contact, fields) {
            override fun setComponentConnections() {
                connections[0]?.let { contact.leftNeighbor = it.component }
                connections[1]?.let { contact.rightNeighbor = it.component }
            }
        }
    }

    private fun parseOpenSide(openSide: String): Boolean {
        return when (openSide.lowercase()) {
            "left" -> true
            "right" -> false
            else -> throw XmlComponentFieldException(
                name = openSideAttributeName,
                className = className,
                data = openSide
            )
        }
    }
}


