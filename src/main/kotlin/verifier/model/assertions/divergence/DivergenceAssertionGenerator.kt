package verifier.model.assertions.divergence

import core.model.Circuit
import verifier.model.assertions.AssertionData
import verifier.model.common.AssertionGenerator
import verifier.model.common.AssertionDefinition

class DivergenceAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition> {
        return listOf(DivergenceAssertion())
    }
}