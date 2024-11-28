package verifier.model.assertions.lamp_status

import core.model.Component
import csp_generator.model.CspPaths
import ui.screens.main.sub_screens.assertions.model.LampStatusAssertionData
import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class LampStatusAssertion(data: LampStatusAssertionData) : AssertionDefinition() {
    override val definition =
        "assert not STOP [T= SYSTEM_LAMPS_STATUS((| ${
            data.selectedData.joinToString(", ") { pair ->
                "${data.getComponentByName(pair.first)!!.id} => ${pair.second}"
            }
        } |),{})"
    override val type = AssertionType.LampStatus

    override fun buildRunResult(
        session: Session,
        fdrAssertion: Assertion,
        components: List<Component>,
        paths: CspPaths
    ): AssertionRunResult {
        return LampStatusAssertionRunResult(fdrAssertion)
    }
}