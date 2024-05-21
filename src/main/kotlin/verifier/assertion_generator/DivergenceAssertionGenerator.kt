package verifier.assertion_generator

import core.model.Circuit
import verifier.model.AssertionDefinition
import verifier.model.DivergenceAssertion

class DivergenceAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit): List<AssertionDefinition> {
        return listOf(DivergenceAssertion())
    }
}