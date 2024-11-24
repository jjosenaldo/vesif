package verifier.model.common

import core.model.Circuit
import verifier.model.assertions.AssertionData

interface AssertionGenerator {
    fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition>
}