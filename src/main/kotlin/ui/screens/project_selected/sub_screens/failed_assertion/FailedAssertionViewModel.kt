package ui.screens.project_selected.sub_screens.failed_assertion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.model.Component
import input.model.ClearsyCircuit
import input.model.ClearsyComponent
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

        this.failedAssertions = buildFailedAssertions(circuit, failedAssertions)
        selectedFailedAssertion = this.failedAssertions.first()
    }

    fun select(failedAssertion: UiFailedAssertion) {
        selectedFailedAssertion = failedAssertion
    }

    @Suppress("UNCHECKED_CAST")
    private fun buildFailedAssertions(
        circuit: ClearsyCircuit,
        results: List<AssertionRunResult>
    ): List<UiFailedAssertion> {
        return results.groupBy { it.javaClass.kotlin }.map { (klass, results) ->
            when (klass) {
                ShortCircuitAssertionRunResult::class -> buildShortCircuitAssertions(
                    circuit,
                    results as List<ShortCircuitAssertionRunResult>
                )

                RingBellAssertionRunResult::class -> buildRingBellAssertions(
                    circuit,
                    results as List<RingBellAssertionRunResult>
                )

                else -> listOf()
            }
        }.flatten()
    }

    private fun buildShortCircuitAssertions(
        circuit: ClearsyCircuit,
        results: List<ShortCircuitAssertionRunResult>
    ): List<UiFailedAssertion> {
        return results.map {
            UiFailedShortCircuit(
                circuitImage = File(circuit.circuitImagePath),
                shortCircuit = it.shortCircuit.toUiComponents(circuit),
                inputs = it.inputs.toUiComponents(circuit)
            )
        }
    }

    private fun buildRingBellAssertions(
        circuit: ClearsyCircuit,
        results: List<RingBellAssertionRunResult>
    ): List<UiFailedAssertion> {
        return results.groupBy { it.pressedButtons.joinToString(",") { button -> button.name } }.values.map { resultsByState ->
            UiFailedRingBell(
                circuitImage = File(circuit.circuitImagePath),
                contacts = resultsByState.map { it.contact }.toUiComponents(circuit),
                inputs = resultsByState.first().pressedButtons.toUiComponents(circuit)
            )
        }
    }

    private fun List<Component>.toUiComponents(circuit: ClearsyCircuit): List<UiComponent> {
        return toClearsyComponents(circuit).map(UiComponent::fromClearsyComponent)
    }

    private fun List<Component>.toClearsyComponents(circuit: ClearsyCircuit): List<ClearsyComponent> {
        return mapNotNull { circuit.findComponentById(it.id) }
    }

}
