package verifier.assertion_generator

import core.model.Circuit
import verifier.model.AssertionDefinition
import verifier.model.DeterminismDefinition

class DeterminismAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit): List<AssertionDefinition> {
        return listOf(DeterminismDefinition())
    }
}