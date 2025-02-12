package verifier.model.assertions.deadlock

import core.model.Circuit
import ui.screens.main.sub_screens.assertions.model.AssertionData
import verifier.model.common.AssertionGenerator
import verifier.model.common.AssertionDefinition

class DeadlockAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition> {
        return listOf(DeadlockAssertion())
    }
}