package org.example.verifier.model

class RingBellAssertionRunResult(assertion: RingBellAssertion, passed: Boolean) : AssertionRunResult(assertion, passed) {
    override val details: String by lazy {
        if (passed) return@lazy ""

        "Contact: ${assertion.contact.name}, pressed buttons: ${assertion.buttonsState.filterValues { it }.keys.joinToString (", "){ it.name }}"
    }
}