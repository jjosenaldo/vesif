package presentation.assertions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import core.files.FileManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.AppScreen
import presentation.failed_assertions.FailedAssertionsViewModel
import verifier.AssertionManager
import presentation.select_circuit.CircuitViewModel
import verifier.model.common.AssertionType

class AssertionsViewModel(
    private val assertionManager: AssertionManager,
    private val circuitViewModel: CircuitViewModel
) : KoinComponent {
    private val failedAssertionsViewModel: FailedAssertionsViewModel by inject()

    var assertions by mutableStateOf(listOf<AssertionState>())
        private set
    var assertionDetails by mutableStateOf<AssertionFailed?>(null)
        private set


    fun setAssertionsFromTypes(types: List<AssertionType>) {
        assertions = types.map { AssertionInitial(type = it) }
    }

    fun setSelected(selected: Boolean, assertion: AssertionInitial) {
        val assertionIndex = assertions.indexOfFirst { it.type == assertion.type }
        val newAssertions = ArrayList(assertions)
        newAssertions[assertionIndex] = assertion.copyWith(selected = selected)
        assertions = newAssertions
    }

    suspend fun runSelectedAssertions(nav: NavHostController) {
        assertions = assertions.map {
            if (it is AssertionInitial && it.selected)
                AssertionRunning(it.type)
            else
                AssertionNotSelected(it.type)
        }

        val typesToCheck = assertions.filterIsInstance<AssertionRunning>().map { it.type }
        val allFailingAssertions =
            assertionManager.runAssertionsReturnFailing(
                circuitViewModel.selectedCircuit.circuit.circuit,
                typesToCheck
            )
        assertions = assertions.map {
            val failingAssertions = allFailingAssertions[it.type] ?: listOf()

            when {
                failingAssertions.isNotEmpty() -> AssertionFailed(
                    it.type,
                    details = failingAssertions.joinToString(FileManager.newLine) { result -> result.details }
                )

                typesToCheck.contains(it.type) -> AssertionPassed(it.type)
                else -> it
            }
        }

        failedAssertionsViewModel.setup(
            failedAssertions = allFailingAssertions.values.flatten(),
            circuit = circuitViewModel.selectedCircuit.circuit
        )
        nav.navigate(AppScreen.FailedAssertions.name)
    }

    fun showAssertionDetails(assertion: AssertionFailed?) {

    }
}
