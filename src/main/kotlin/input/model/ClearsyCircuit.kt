package input.model

import core.model.Circuit
import core.model.Identifiable

data class ClearsyCircuit(val name: String, val circuitImagePath: String, val components: List<ClearsyComponent>) {
    val circuit = Circuit(components.map(ClearsyComponent::component))

    fun findComponentById(id: String): ClearsyComponent? {
        return components.firstOrNull { it.id == id }
    }

    companion object {
        val DEFAULT = ClearsyCircuit("", "", listOf())
    }
}

fun Identifiable.toClearsyComponent(circuit: ClearsyCircuit): ClearsyComponent? {
    return circuit.findComponentById(id)
}

fun List<Identifiable>.toClearsyComponents(circuit: ClearsyCircuit): List<ClearsyComponent> {
    return mapNotNull { it.toClearsyComponent(circuit) }
}