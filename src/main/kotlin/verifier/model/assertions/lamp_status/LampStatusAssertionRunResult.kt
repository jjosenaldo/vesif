package verifier.model.assertions.lamp_status

import uk.ac.ox.cs.fdr.Assertion
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class LampStatusAssertionRunResult(fdrAssertion: Assertion) :
    AssertionRunResult(AssertionType.LampStatus, fdrAssertion.passed()) {
    override fun hasDetails() = false
}