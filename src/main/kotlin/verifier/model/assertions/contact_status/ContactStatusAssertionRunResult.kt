package verifier.model.assertions.contact_status

import uk.ac.ox.cs.fdr.Assertion
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class ContactStatusAssertionRunResult(fdrAssertion: Assertion) :
    AssertionRunResult(AssertionType.ContactStatus, fdrAssertion.passed()) {
    override fun hasDetails() = false
}