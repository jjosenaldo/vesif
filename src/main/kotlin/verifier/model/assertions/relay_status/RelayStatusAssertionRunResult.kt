package verifier.model.assertions.relay_status

import uk.ac.ox.cs.fdr.Assertion
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class RelayStatusAssertionRunResult(fdrAssertion: Assertion) :
    AssertionRunResult(AssertionType.RelayStatus, fdrAssertion.passed()) {
    override fun hasDetails() = false
}