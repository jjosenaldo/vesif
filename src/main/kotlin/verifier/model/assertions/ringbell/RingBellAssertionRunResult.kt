package verifier.model.assertions.ringbell

import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class RingBellAssertionRunResult(assertion: RingBellAssertion, passed: Boolean) :
    AssertionRunResult(AssertionType.RingBell, passed) {
    override val details by lazy {
        "Contact: ${assertion.contact.name}, pressed buttons: ${pressedButtonsDescription()}"
    }

    val contact = assertion.contact
    val pressedButtons = assertion.buttonsState.filterValues { it }.keys.toList()

    private fun pressedButtonsDescription(): String {
        if (pressedButtons.isEmpty()) return "None"

        return "{${
            pressedButtons.joinToString(
                ", "
            ) { it.name }
        }}"
    }
}
