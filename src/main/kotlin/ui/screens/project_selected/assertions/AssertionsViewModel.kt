package ui.screens.project_selected.assertions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.screens.project_selected.failed_assertion.FailedAssertionViewModel
import ui.screens.project_selected.ProjectSelectedScreenId
import ui.screens.project_selected.ProjectSelectedViewModel
import verifier.AssertionManager
import ui.screens.project_selected.select_circuit.CircuitViewModel
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class AssertionsViewModel(
    private val assertionManager: AssertionManager
) : KoinComponent {
    private val circuitViewModel: CircuitViewModel by inject()
    private val failedAssertionsViewModel: FailedAssertionViewModel by inject()
    private val projectSelectedViewModel: ProjectSelectedViewModel by inject()

    var assertions by mutableStateOf(listOf<AssertionState>())
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

    suspend fun runSelectedAssertions() {
        assertions = assertions.map {
            if (it is AssertionInitial && it.selected)
                AssertionRunning(it.type)
            else
                AssertionNotSelected(it.type)
        }

        val typesToCheck = assertions.filterIsInstance<AssertionRunning>().map { it.type }
        val allFailingAssertions =
            assertionManager.runAssertionsReturnFailing(
                circuitViewModel.selectedCircuit.circuit,
                typesToCheck
            )
        assertions = assertions.map {
            val failingAssertions = allFailingAssertions[it.type] ?: listOf()

            when {
                failingAssertions.isNotEmpty() -> AssertionFailed(it.type, failingAssertions)
                typesToCheck.contains(it.type) -> AssertionPassed(it.type)
                else -> it
            }
        }
    }

    fun goToFailedAssertion(results: List<AssertionRunResult>) {
        failedAssertionsViewModel.setup(results)
        projectSelectedViewModel.currentScreen = ProjectSelectedScreenId.FailedAssertion
    }
}
