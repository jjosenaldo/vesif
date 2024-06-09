package ui.screens.project_selected.sub_screens.failed_assertion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.model.UiComponent
import ui.model.assertions.UiFailedAssertion
import ui.model.assertions.UiFailedRingBell
import ui.model.assertions.UiFailedShortCircuit
import ui.screens.project_selected.sub_screens.select_circuit.CircuitViewModel
import verifier.model.assertions.ringbell.RingBellAssertionRunResult
import verifier.model.assertions.short_circuit.ShortCircuitAssertionRunResult
import verifier.model.common.AssertionRunResult
import java.io.File

class FailedAssertionViewModel : KoinComponent {
    private val circuitViewModel: CircuitViewModel by inject()

    var selectedFailedAssertion by mutableStateOf(UiFailedAssertion.DEFAULT)
        private set
    var failedAssertions by mutableStateOf(listOf<UiFailedAssertion>())
        private set

    fun setup(failedAssertions: List<AssertionRunResult>) {
        val circuit = circuitViewModel.selectedCircuit

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

                is ShortCircuitAssertionRunResult -> {
                    val inputs = it.inputs.mapNotNull { input -> circuit.findComponentById(input.id) }
                    val path = it.shortCircuit.mapNotNull { component -> circuit.findComponentById(component.id) }

                    UiFailedShortCircuit(
                        circuitImage = File(circuit.circuitImagePath),
                        shortCircuit = path.map(UiComponent::fromClearsyComponent),
                        inputs = inputs.map(UiComponent::fromClearsyComponent)
                    )
                }

                else -> null
            }
        }
        selectedFailedAssertion = this.failedAssertions.first()
    }

}