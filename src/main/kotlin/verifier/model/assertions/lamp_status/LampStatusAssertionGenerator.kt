package verifier.model.assertions.lamp_status

import core.model.Circuit
import ui.screens.main.sub_screens.assertions.model.AssertionData
import ui.screens.main.sub_screens.assertions.model.ContactStatusAssertionData
import ui.screens.main.sub_screens.assertions.model.LampStatusAssertionData
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionGenerator

class LampStatusAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition> {
        return if (data is LampStatusAssertionData) listOf(LampStatusAssertion(data)) else listOf()
    }
}