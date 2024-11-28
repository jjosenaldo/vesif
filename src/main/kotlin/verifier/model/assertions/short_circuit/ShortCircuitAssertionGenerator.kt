package verifier.model.assertions.short_circuit

import core.model.Circuit
import ui.screens.main.sub_screens.assertions.model.AssertionData
import verifier.model.common.AssertionGenerator
import verifier.model.common.AssertionDefinition

class ShortCircuitAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition> {
        return listOf(ShortCircuitAssertion())
    }
}