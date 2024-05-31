package presentation.failed_assertions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import input.model.ClearsyCircuit
import presentation.model.UiCircuit
import presentation.model.UiComponent
import presentation.model.UiFailedAssertion
import presentation.model.UiFailedRingBell
import verifier.model.assertions.ringbell.RingBellAssertionRunResult
import verifier.model.common.AssertionRunResult
import java.io.File

class FailedAssertionsViewModel {
    var failedAssertions by mutableStateOf(listOf<UiFailedAssertion>())
        private set

    fun setup(failedAssertions: List<AssertionRunResult>, circuit: ClearsyCircuit) {
        this.failedAssertions = failedAssertions.mapNotNull {
            when (it) {
                is RingBellAssertionRunResult -> {
                    val contact = circuit.findComponentById(it.contact.id) ?: return
                    val inputs = it.pressedButtons.mapNotNull { button -> circuit.findComponentById(button.id) }

                    UiFailedRingBell(
                        circuitImage = File(circuit.circuitImagePath),
                        contact = UiComponent.fromClearsyComponent(contact),
                        inputs = inputs.map(UiComponent::fromClearsyComponent)
                    )
                }

                else -> null
            }
        }
    }

}