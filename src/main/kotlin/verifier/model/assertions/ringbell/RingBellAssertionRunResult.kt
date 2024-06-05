package verifier.model.assertions.ringbell

import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

// TODO: multiple contacts
class RingBellAssertionRunResult(assertion: RingBellAssertion, passed: Boolean) :
    AssertionRunResult(AssertionType.RingBell, passed) {

    val contact = assertion.contact
    val pressedButtons = assertion.buttonsState.filterValues { it }.keys.toList()
}
