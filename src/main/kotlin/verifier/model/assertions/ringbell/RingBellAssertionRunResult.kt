package verifier.model.assertions.ringbell

import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class RingBellAssertionRunResult(private val assertion: RingBellAssertion, passed: Boolean) :
    AssertionRunResult(AssertionType.RingBell, passed) {
    override val details =
        "Contact: ${assertion.contact.name}, pressed buttons: ${pressedButtons()}"

    private fun pressedButtons(): String {
        val buttons = assertion.buttonsState.filterValues { it }.keys

        if (buttons.isEmpty()) return "None"

        return "{${
            buttons.joinToString(
                ", "
            ) { it.name }
        }}"
    }
}
