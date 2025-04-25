package ui.screens.main.sub_screens.select_circuit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import input.model.ClearsyCircuit
import org.koin.core.component.KoinComponent
import ui.model.UiCircuitParams
import ui.navigation.AppNavigator
import verifier.AssertionManager
import java.io.File

class CircuitViewModel(
    private val assertionManager: AssertionManager
) : KoinComponent {
    var circuits = listOf<ClearsyCircuit>()
        private set
    var selectedCircuit by mutableStateOf(ClearsyCircuit.DEFAULT)
        private set
    var selectedCircuitParams by mutableStateOf(UiCircuitParams.DEFAULT)
        private set
    private var loadedCircuitImages = mutableMapOf<Int, File>()

    fun setup(circuits: List<ClearsyCircuit>) {
        this.circuits = circuits
        loadedCircuitImages = mutableMapOf()
    }

    fun selectCircuit(index: Int) {
        val circuit = circuits[index]

        if (!loadedCircuitImages.contains(index)) {
            loadedCircuitImages[index] = File(circuit.circuitImagePath)
        }

        selectedCircuitParams = UiCircuitParams(loadedCircuitImages[index])
        selectedCircuit = circuit
    }

    fun confirmCircuitSelection(navigator: AppNavigator) {
        navigator.navToAssertions(assertionManager.getAssertionTypes())
    }

    fun zoom(isIn: Boolean) {
        if (selectedCircuitParams.image != null) {
            val currentZoom = selectedCircuitParams.zoomLevel
            val newZoom = if (isIn) currentZoom.zoomIn() else currentZoom.zoomOut()
            selectedCircuitParams = selectedCircuitParams.copy(zoomLevel = newZoom)
        }
    }
}