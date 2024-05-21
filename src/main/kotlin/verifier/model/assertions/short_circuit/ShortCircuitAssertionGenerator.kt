package verifier.model.assertions.short_circuit

import core.model.Circuit
import verifier.model.common.AssertionGenerator
import verifier.model.common.AssertionDefinition

class ShortCircuitAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit): List<AssertionDefinition> {
        return listOf(ShortCircuitAssertion())
    }
}