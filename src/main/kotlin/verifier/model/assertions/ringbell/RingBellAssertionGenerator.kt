package verifier.model.assertions.ringbell

import core.model.*
import csp_generator.util.endpoint
import csp_generator.util.endpoint1
import verifier.model.common.AssertionGenerator

class RingBellAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit): List<RingBellAssertion> {
        if (circuit.buttons.isEmpty()) {
            return circuit.relayContacts.map {
                buildAssertionForContactAndButtonConfiguration(
                    it,
                    getContactEndpoint(it),
                    listOf(),
                    listOf()
                )
            }
        }

        val combinations = generateBooleanCombinations(circuit.buttons.size)

        return circuit.relayContacts.map { contact ->
            combinations.map { combination ->
                buildAssertionForContactAndButtonConfiguration(
                    contact,
                    getContactEndpoint(contact),
                    combination,
                    circuit.buttons
                )
            }
        }.flatten()
    }

    private fun buildAssertionForContactAndButtonConfiguration(
        contact: Contact,
        endpoint: Component,
        configuration: List<Boolean>,
        buttons: List<Button>
    ): RingBellAssertion {
        return RingBellAssertion(contact, endpoint, buttons.zip(configuration).toMap())
    }

    // Generates the 2^n possible combinations
    private fun generateBooleanCombinations(n: Int): List<List<Boolean>> {
        return (0 until (1 shl n)).map { i ->
            (0 until n).map { j -> ((i shr j) and 1) == 1 }
        }
    }

    private fun getContactEndpoint(contact: Contact): Component {
        return when (contact) {
            is RelayRegularContact -> contact.endpoint
            is RelayChangeoverContact -> contact.endpoint1
            else -> Component.DEFAULT
        }
    }

}