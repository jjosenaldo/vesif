package presentation.assertions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.files.FileManager
import verifier.fdr.AssertionManager
import presentation.circuit.CircuitViewModel
import verifier.model.AssertionType

class AssertionsViewModel(
    private val assertionManager: AssertionManager,
    private val circuitViewModel: CircuitViewModel
) {
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
                circuitViewModel.circuit,
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
    }

    fun showAssertionDetails(assertion: AssertionFailed?) {
        assertionDetails = assertion
    }
}
