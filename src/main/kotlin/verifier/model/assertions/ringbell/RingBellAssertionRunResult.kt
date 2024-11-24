package verifier.model.assertions.ringbell

import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class RingBellAssertionRunResult(assertion: RingBellAssertion, passed: Boolean) :
    AssertionRunResult(AssertionType.RingBell, passed) {

    val contact = assertion.contact
    val activeInputs = assertion.inputsState.filterValues { it }.keys.sortedBy { it.name }
    override fun hasDetails() = true
}
