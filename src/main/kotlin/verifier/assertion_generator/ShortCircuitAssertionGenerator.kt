package verifier.assertion_generator

import core.model.Circuit
import verifier.model.ShortCircuitAssertion
import verifier.model.AssertionDefinition

class ShortCircuitAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit): List<AssertionDefinition> {
        return listOf(ShortCircuitAssertion())
    }
}