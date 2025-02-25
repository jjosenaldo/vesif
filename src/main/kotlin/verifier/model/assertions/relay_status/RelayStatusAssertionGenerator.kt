package verifier.model.assertions.relay_status

import core.model.Circuit
import ui.screens.main.sub_screens.assertions.model.AssertionData
import ui.screens.main.sub_screens.assertions.model.RelayStatusAssertionData
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionGenerator

class RelayStatusAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition> {
        return if (data is RelayStatusAssertionData) listOf(RelayStatusAssertion(data)) else listOf()
    }
}