package verifier.model.assertions.component_status

import uk.ac.ox.cs.fdr.Assertion
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class ComponentStatusAssertionRunResult(fdrAssertion: Assertion) :
    AssertionRunResult(AssertionType.ComponentStatus, fdrAssertion.passed()) {
    override fun hasDetails() = false
}