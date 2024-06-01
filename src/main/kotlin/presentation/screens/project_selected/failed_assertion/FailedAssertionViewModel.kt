package presentation.screens.project_selected.failed_assertion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.model.UiComponent
import presentation.model.UiFailedAssertion
import presentation.model.UiFailedRingBell
import presentation.screens.project_selected.select_circuit.CircuitViewModel
import verifier.model.assertions.ringbell.RingBellAssertionRunResult
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

                else -> null
            }
        }
        selectedFailedAssertion = this.failedAssertions.first()
    }

}