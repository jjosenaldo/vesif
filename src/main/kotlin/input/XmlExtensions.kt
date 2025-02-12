package input

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList


fun Element.childrenByName(name: String): List<Node> {
    return getElementsByTagName(name).toList()
}

fun Element.attributeChild(name: String): String? {
    return childrenByName(name).firstOrNull()?.textContent
}

fun Node.nodeChildren(): List<Node> {
    return childNodes.toList()
}

fun NodeList.toList(): List<Node> {
    return (0..<length).mapNotNull { item(it) }
}