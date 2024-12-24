package verifier.model.assertions.output_status

import core.model.Circuit
import ui.screens.main.sub_screens.assertions.model.AssertionData
import ui.screens.main.sub_screens.assertions.model.OutputStatusAssertionData
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionGenerator

class OutputStatusAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition> {
        return if (data is OutputStatusAssertionData) listOf(OutputStatusAssertion(data)) else listOf()
    }
}