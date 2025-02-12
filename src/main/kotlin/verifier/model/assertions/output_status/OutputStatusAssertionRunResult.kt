package verifier.model.assertions.output_status

import uk.ac.ox.cs.fdr.Assertion
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class OutputStatusAssertionRunResult(fdrAssertion: Assertion) :
    AssertionRunResult(AssertionType.OutputStatus, fdrAssertion.passed()) {
    override fun hasDetails() = false
}