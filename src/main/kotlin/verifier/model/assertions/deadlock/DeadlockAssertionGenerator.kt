package verifier.model.assertions.deadlock

import core.model.Circuit
import verifier.model.common.AssertionGenerator
import verifier.model.common.AssertionDefinition

class DeadlockAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit): List<AssertionDefinition> {
        return listOf(DeadlockAssertion())
    }
}