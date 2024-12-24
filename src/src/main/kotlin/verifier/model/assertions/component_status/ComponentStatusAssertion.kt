package verifier.model.assertions.component_status

import core.model.Component
import csp_generator.model.CspPaths
import ui.screens.main.sub_screens.assertions.model.ComponentStatusAssertionData
import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class ComponentStatusAssertion(data: ComponentStatusAssertionData) : AssertionDefinition() {
    override val definition =
        "assert not STOP [T= SYSTEM_COMPONENTS_STATUS((| ${
            data.selectedData.joinToString(", ") { pair ->
                "${data.getComponentByName(pair.first)!!.id} => ${pair.second}"
            }
        } |))"
    override val type = AssertionType.ComponentStatus

    override fun buildRunResult(
        session: Session,
        fdrAssertion: Assertion,
        components: List<Component>,
        paths: CspPaths
    ): AssertionRunResult {
        return ComponentStatusAssertionRunResult(fdrAssertion)
    }
}