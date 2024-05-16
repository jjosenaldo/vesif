package verifier.model

class RingBellAssertionRunResult(assertion: RingBellAssertion, passed: Boolean) :
    AssertionRunResult(assertion, passed) {
    override val details =
        if (passed) ""
        else "Contact: ${assertion.contact.name}, pressed buttons: ${
            assertion.buttonsState.filterValues { it }.keys.joinToString(
                ", "
            ) { it.name }
        }"
}
