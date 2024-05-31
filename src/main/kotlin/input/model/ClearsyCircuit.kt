package input.model

import core.model.Circuit
import core.model.Component

data class ClearsyCircuit(val circuitImagePath: String, val components: List<ClearsyComponent>) {
    val circuit = Circuit(components.map(ClearsyComponent::component))

    fun findComponentById(id: String): ClearsyComponent? {
        return components.firstOrNull { it.id == id }
    }

    companion object {
        val DEFAULT = ClearsyCircuit("", listOf())
    }
}