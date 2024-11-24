package verifier.model.assertions.ringbell

import core.model.*
import csp_generator.util.endpoint
import csp_generator.util.endpoint1
import verifier.model.assertions.AssertionData
import verifier.model.common.AssertionGenerator

class RingBellAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<RingBellAssertion> {
        val inputs = circuit.components.filterIsInstance<BinaryInput>()
        if (inputs.isEmpty()) {
            return circuit.relayContacts.map {
                buildAssertionForContactAndInputConfiguration(
                    it,
                    getContactEndpoint(it),
                    listOf(),
                    inputs
                )
            }
        }

        val combinations = generateBooleanCombinations(inputs.size)

        return circuit.relayContacts.map { contact ->
            combinations.map { combination ->
                buildAssertionForContactAndInputConfiguration(
                    contact,
                    getContactEndpoint(contact),
                    combination,
                    inputs
                )
            }
        }.flatten()
    }

    private fun buildAssertionForContactAndInputConfiguration(
        contact: Contact,
        endpoint: Component,
        configuration: List<Boolean>,
        inputs: List<BinaryInput>
    ): RingBellAssertion {
        return RingBellAssertion(contact, endpoint, inputs.zip(configuration).toMap())
    }

    // Generates the 2^n possible combinations
    private fun generateBooleanCombinations(n: Int): List<List<Boolean>> {
        return (0 until (1 shl n)).map { i ->
            (0 until n).map { j -> ((i shr j) and 1) == 1 }
        }
    }

    private fun getContactEndpoint(contact: Contact): Component {
        return when (contact) {
            is MonostableSimpleContact -> contact.endpoint
            is RelayChangeoverContact -> contact.endpoint1
            else -> Component.DEFAULT
        }
    }

}