package ui.screens.main.sub_screens.assertions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.model.MonostableSimpleContact
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.screens.main.sub_screens.select_circuit.CircuitViewModel
import verifier.AssertionManager
import verifier.model.assertions.EmptyAssertionData
import verifier.model.assertions.MultiselectAssertionData
import verifier.model.assertions.contact_status.ContactStatusAssertionData
import verifier.model.common.AssertionType

class AssertionsViewModel(
    private val assertionManager: AssertionManager
) : KoinComponent {
    private val circuitViewModel: CircuitViewModel by inject()

    var multiselectDataId by mutableStateOf(0)
        private set

    var assertions by mutableStateOf(listOf<AssertionState>())
        private set

    fun setAssertionsFromTypes(types: List<AssertionType>) {
        assertions = types.map {
            AssertionInitial(
                type = it, data = when (it) {
                    AssertionType.ContactStatus -> ContactStatusAssertionData(
                        contacts = circuitViewModel.selectedCircuit.circuit.components.filterIsInstance<MonostableSimpleContact>()
                    )

                    else -> EmptyAssertionData
                }
            )
        }
    }

    fun reset() {
        setAssertionsFromTypes(assertions.map(AssertionState::type))
    }

    fun setSelected(selected: Boolean, assertion: AssertionInitial) {
        val assertionIndex = assertions.indexOfFirst { it.type == assertion.type }
        val newAssertions = ArrayList(assertions)
        assertion.data.onSelected(selected)
        newAssertions[assertionIndex] = assertion.copyWith(selected = selected)
        assertions = newAssertions
    }

    fun updateMultiselect(newData: MultiselectAssertionData<*>) {
        val assertionIndex = assertions.indexOfFirst { it.data is MultiselectAssertionData<*> }
        val newAssertions = ArrayList(assertions)
        val newAssertion = assertions[assertionIndex].withData(newData)
        newAssertions[assertionIndex] = newAssertion
        assertions = newAssertions
        multiselectDataId += 1

        if (!newAssertion.data.isValid() && newAssertion is AssertionInitial)
            setSelected(false, newAssertion)
    }

    suspend fun runSelectedAssertions() {
        assertions = assertions.map {
            if (it is AssertionInitial && it.selected)
                AssertionRunning(it.type, it.data)
            else
                AssertionNotSelected(it.type, it.data)
        }

        val assertionsToCheck = assertions.filterIsInstance<AssertionRunning>()

        try {
            val allFailingAssertions =
                assertionManager.runAssertionsReturnFailing(
                    circuitViewModel.selectedCircuit.circuit,
                    assertionsToCheck.associate { it.type to it.data }
                )
            assertions = assertions.map { assertion ->
                val failedResults = allFailingAssertions[assertion.type] ?: listOf()

                when {
                    failedResults.isNotEmpty() -> AssertionFailed(
                        assertion.type,
                        failedResults.filter { it.hasDetails() },
                        assertion.data
                    )

                    assertionsToCheck.any { it.type == assertion.type } -> AssertionPassed(
                        assertion.type,
                        assertion.data
                    )

                    else -> assertion
                }
            }
        } catch (e: Throwable) {
            assertions = assertions.map { AssertionError(it.type, e, it.data) }
        }
    }

}
