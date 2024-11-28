package verifier.model.assertions.determinism

import core.model.Circuit
import ui.screens.main.sub_screens.assertions.model.AssertionData
import verifier.model.common.AssertionGenerator
import verifier.model.common.AssertionDefinition

class DeterminismAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition> {
        return listOf(DeterminismDefinition())
    }
}