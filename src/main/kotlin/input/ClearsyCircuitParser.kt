package input

import core.files.FileManager
import core.model.*
import input.model.XmlNodeTerminal
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.pathString


class ClearsyCircuitParser {
    private val objectsFileExtension = "cdedatamodel"
    private val xmlComponentBuilders = mapOf(
        "C_BUTTON" to XmlButtonBuilder(),
        "C_POS_POLE" to XmlPoleBuilder(isPositive = true),
        "C_NEG_POLE" to XmlPoleBuilder(isPositive = false),
        "C_CONTACT_NORMALLY_OPEN" to XmlRelayRegularContactBuilder(isNormallyOpen = true),
        "C_RELAY_MONOSTABLE" to XmlMonostableRelayBuilder(),
        "C_CONTACT_NORMALLY_CLOSED" to XmlRelayRegularContactBuilder(isNormallyOpen = false),
        "C_JUNCTION" to XmlJunctionBuilder(),
        "C_LEVER" to XmlLeverBuilder(),
        "C_LEVER_CONTACT" to XmlLeverContactBuilder(),
        "C_LAMP" to XmlLampBuilder(),
        "C_CHANGEOVER_CONTACT" to XmlRelayChangeOverContactBuilder(),
        "C_RESISTOR" to XmlResistorBuilder(),
        "C_CAPACITOR" to XmlCapacitorBuilder()
    )

    suspend fun parseCircuitXml(projectPath: String, circuitPath: String): Circuit {
        val allObjects = getObjects(getObjectsPath(projectPath))
        val circuitObjectsNames = mutableSetOf<String>()
        val components = FileManager.readXml(circuitPath)
            .documentElement
            .childrenByName("Leg")
            .fold(allObjects) { currentObjects, leg ->
                val (terminals, newObjects) = parseLeg(currentObjects, leg)
                circuitObjectsNames.addAll(terminals.map { it.component.name })
                newObjects
            }
            .map { it.apply { setComponentConnections() }.component }
            .filter { circuitObjectsNames.contains(it.name) }

        return Circuit(components = components)
    }

    private fun getObjectsPath(projectPath: String): String {
        return Files.list(Paths.get(projectPath))
            .filter { Files.isRegularFile(it) && it.fileName.toString().endsWith(".$objectsFileExtension") }
            .map { it.pathString }
            .findFirst().get()
    }

    private suspend fun getObjects(objectsPath: String): List<XmlCircuitComponent> {
        val document = FileManager.readXml(objectsPath)
        return getObjectsFromDocument(document)
    }

    private fun getObjectsFromDocument(document: Document): List<XmlCircuitComponent> {
        return document.documentElement.childrenByName("Object").filterIsInstance<Element>().mapNotNull {
            val category = it.attributeChild("Category") ?: return@mapNotNull null
            if (category == "C_BEND") return@mapNotNull null
            val builder = xmlComponentBuilders[category] ?: return@mapNotNull null
            val attributes = it.nodeChildren().map { node -> Pair(node.nodeName ?: "", node.textContent ?: "") }
            builder.buildXmlComponent(attributes)
        }.also {
            connectReferences(it)
        }
    }

    private fun parseLeg(
        objects: List<XmlCircuitComponent>,
        node: Node
    ): Pair<List<XmlNodeTerminal>, List<XmlCircuitComponent>> {
        if (node !is Element) return Pair(listOf(), listOf())
        val terminals = node
            .childrenByName("Terminal")
            .mapNotNull {
                if (it !is Element) null
                else XmlNodeTerminal.fromElementAndObjects(it, objects)
            }

        connectNeighbors(terminals)

        return Pair(terminals, objects)
    }

    private fun connectNeighbors(terminals: List<XmlNodeTerminal>) {
        if (terminals.size < 2) return

        terminals.forEachIndexed { i, terminal ->
            val nextTerminal = if (i < terminals.size - 1) terminals[i + 1] else null
            val previousTerminal = if (i > 0) terminals[i - 1] else null

            nextTerminal?.let {
                connectTerminal(terminal, it)
            }

            previousTerminal?.let {
                connectTerminal(terminal, it)
            }
        }
    }

    private fun connectTerminal(terminal1: XmlNodeTerminal, terminal2: XmlNodeTerminal) {
        terminal1.component.connections[terminal1.index] = terminal2.component
    }

    private fun connectReferences(components: List<XmlCircuitComponent>) {
        connectContactReferences<RelayRegularContact, MonostableRelay>(components)
        connectContactReferences<RelayChangeOverContact, MonostableRelay>(components)
        connectContactReferences<LeverContact, Lever>(components)
    }

    private inline fun <reified T1 : Contact, reified T2 : ContactController> connectContactReferences(components: List<XmlCircuitComponent>) {
        val contacts = mutableListOf<T1>()

        components.forEach {
            if (it.component is T1) {
                contacts.add(it.component)
                val controller = components.first { component -> component.component.name == it.reference }.component
                if (controller is T2) {
                    it.component.controller = controller
                }
            }
        }

        components.forEach {
            val component = it.component
            if (component is T2) {
                component.contacts += contacts.filter { contact -> contact.controller.name == component.name }
            }
        }
    }
}