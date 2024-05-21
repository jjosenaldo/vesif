package verifier.model.common

import core.model.Circuit

interface AssertionGenerator {
    fun generateAssertions(circuit: Circuit): List<AssertionDefinition>
}