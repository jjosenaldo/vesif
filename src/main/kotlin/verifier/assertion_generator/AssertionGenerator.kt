package verifier.assertion_generator

import core.model.Circuit
import verifier.model.AssertionDefinition

interface AssertionGenerator {
    fun generateAssertions(circuit: Circuit): List<AssertionDefinition>
}