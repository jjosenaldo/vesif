package verifier.assertion_generator

import core.model.Button
import core.model.Circuit
import core.model.RelayRegularContact
import verifier.model.RingBellAssertion

// TODO: only generates for regular contacts
class RingBellAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit): List<RingBellAssertion> {
        if (circuit.buttons.isEmpty()) {
            return circuit.regularContacts.map {
                buildAssertionForContactAndButtonConfiguration(
                    it,
                    listOf(),
                    listOf()
                )
            }
        }

        val combinations = generateBooleanCombinations(circuit.buttons.size)

        return circuit.regularContacts.map { contact ->
            combinations.map { combination ->
                buildAssertionForContactAndButtonConfiguration(
                    contact,
                    combination,
                    circuit.buttons
                )
            }
        }.flatten()
    }

    private fun buildAssertionForContactAndButtonConfiguration(
        contact: RelayRegularContact,
        configuration: List<Boolean>,
        buttons: List<Button>
    ): RingBellAssertion {
        return RingBellAssertion(contact, buttons.zip(configuration).toMap())
    }

    // Generates the 2^n possible combinations
    private fun generateBooleanCombinations(n: Int): List<List<Boolean>> {
        return (0 until (1 shl n)).map { i ->
            (0 until n).map { j -> ((i shr j) and 1) == 1 }
        }
    }

}