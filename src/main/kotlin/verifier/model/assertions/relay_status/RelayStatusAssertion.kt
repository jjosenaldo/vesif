package verifier.model.assertions.relay_status

import core.model.*
import csp_generator.model.CspPaths
import ui.screens.main.sub_screens.assertions.model.RelayStatusAssertionData
import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class RelayStatusAssertion(data: RelayStatusAssertionData) : AssertionDefinition() {
    override val definition =
        "assert not STOP [T= SYSTEM_RELAYS_STATUS((| ${
            data.selectedData.joinToString(", ") { pair ->
                getAssertionPairDefinition(pair.first, pair.second)
            }
        } |))"
    override val type = AssertionType.RelayStatus

    override fun buildRunResult(
        session: Session,
        fdrAssertion: Assertion,
        components: List<Component>,
        paths: CspPaths
    ): AssertionRunResult {
        return RelayStatusAssertionRunResult(fdrAssertion)
    }

    private fun getAssertionPairDefinition(relay: MonostableRelay, value: Boolean): String {
        return "${relay.id} => $value"
    }
}