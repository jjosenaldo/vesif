package verifier.model

class RingBellAssertionRunResult(assertion: RingBellAssertion, passed: Boolean) :
    AssertionRunResult(AssertionType.RingBell, passed) {
    override val details =
        "Contact: ${assertion.contact.name}, pressed buttons: ${
            assertion.buttonsState.filterValues { it }.keys.joinToString(
                ", "
            ) { it.name }
        }"
}
