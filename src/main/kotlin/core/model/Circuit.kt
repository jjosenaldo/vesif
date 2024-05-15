package org.example.core.model

class Circuit(val components: List<Component>) {
    val regularContacts = components.filterIsInstance<RelayRegularContact>()
    val buttons = components.filterIsInstance<Button>()

    companion object {
        val DEFAULT = Circuit(listOf())
    }
}