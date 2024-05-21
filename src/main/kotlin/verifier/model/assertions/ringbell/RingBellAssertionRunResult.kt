package verifier.model.assertions.ringbell

import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class RingBellAssertionRunResult(assertion: RingBellAssertion, passed: Boolean) :
    AssertionRunResult(AssertionType.RingBell, passed) {
    override val details =
        "Contact: ${assertion.contact.name}, pressed buttons: ${
            assertion.buttonsState.filterValues { it }.keys.joinToString(
                ", "
            ) { it.name }
        }"
}
