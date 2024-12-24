package verifier.model.assertions.component_status

import core.model.Circuit
import ui.screens.main.sub_screens.assertions.model.AssertionData
import ui.screens.main.sub_screens.assertions.model.ComponentStatusAssertionData
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionGenerator

class ComponentStatusAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition> {
        return if (data is ComponentStatusAssertionData) listOf(ComponentStatusAssertion(data)) else listOf()
    }
}