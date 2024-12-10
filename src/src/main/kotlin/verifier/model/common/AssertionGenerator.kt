package verifier.model.common

import core.model.Circuit
import ui.screens.main.sub_screens.assertions.model.AssertionData

interface AssertionGenerator {
    fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition>
}