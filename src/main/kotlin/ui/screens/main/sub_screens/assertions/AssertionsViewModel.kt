package ui.screens.main.sub_screens.assertions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.model.BinaryOutput
import core.model.Contact
import core.model.MonostableRelay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.model.UiCircuitParams
import ui.model.toUiComponent
import ui.screens.main.sub_screens.assertions.model.*
import ui.screens.main.sub_screens.select_circuit.CircuitViewModel
import verifier.AssertionManager
import verifier.model.common.AssertionType

class AssertionsViewModel(
    private val assertionManager: AssertionManager
) : KoinComponent {
    private val circuitViewModel: CircuitViewModel by inject()
    val multiselectDataId = mutableStateMapOf<AssertionType, Int>()
    var assertions by mutableStateOf(listOf<AssertionState>())
        private set
    var uiCircuitModifier by mutableStateOf<(UiCircuitParams) -> UiCircuitParams>({ it })
        private set

    fun setup(types: List<AssertionType>) {
        assertions = types.map {
            AssertionInitial(
                type = it, data = when (it) {
                    AssertionType.ContactStatus -> ContactStatusAssertionData(
                        contacts = circuitViewModel.selectedCircuit.circuit.components.filterIsInstance<Contact>()
                    )

                    AssertionType.OutputStatus -> OutputStatusAssertionData(
                        components = circuitViewModel.selectedCircuit.circuit.components.filterIsInstance<BinaryOutput>()
                    )

                    AssertionType.RelayStatus -> RelayStatusAssertionData(
                        relays = circuitViewModel.selectedCircuit.circuit.components.filterIsInstance<MonostableRelay>()
                    )

                    else -> EmptyAssertionData
                }
            )
        }
        multiselectDataId.clear()
        setUiCircuitModifier()
    }

    fun reset() {
        setup(assertions.map(AssertionState::type))
    }

    fun setSelected(selected: Boolean, assertion: AssertionInitial) {
        val assertionIndex = assertions.indexOfFirst { it.type == assertion.type }
        val newAssertions = ArrayList(assertions)
        assertion.data.onSelected(selected)
        newAssertions[assertionIndex] = assertion.copyWith(selected = selected)
        assertions = newAssertions
        setUiCircuitModifier()
    }

    fun updateMultiselect(assertionType: AssertionType, newData: MultiselectAssertionData<*, *>) {
        val assertionIndex = assertions.indexOfFirst { it.type == assertionType }
        val newAssertions = ArrayList(assertions)
        val newAssertion = assertions[assertionIndex].withData(newData)
        newAssertions[assertionIndex] = newAssertion
        assertions = newAssertions
        setUiCircuitModifier()
        multiselectDataId[newAssertion.type] = multiselectDataId.getOrDefault(newAssertion.type, 0) + 1

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

    fun onGoBack() {
        if (assertions.any { it is AssertionRunning }) {
            assertionManager.cancelRunningAssertions()
        }
    }

    private fun setUiCircuitModifier() {
        val allStatusData = assertions.mapNotNull { assertion ->
            if (assertion.data is BinaryComponentAssertionData<*>) {
                assertion.data
            } else {
                null
            }
        }

        uiCircuitModifier = if (allStatusData.isEmpty()) {
            { it }
        } else {
            { oldCircuitUi ->
                val params = allStatusData.mapNotNull { statusData ->
                    val uiComponentsWithColors = statusData.selectedData.mapNotNull { pair ->
                        val component = pair.first
                        val status = pair.second
                        val uiComponent = component.toUiComponent(circuitViewModel.selectedCircuit)
                        if (uiComponent == null) null
                        else Pair(uiComponent,
                            when(statusData) {
                                is ContactStatusAssertionData -> statusData.getValueInfo(component as Contact, status)
                                is OutputStatusAssertionData -> statusData.getValueInfo(component as BinaryOutput, status)
                                is RelayStatusAssertionData -> statusData.getValueInfo(component as MonostableRelay, status)
                            }.color)
                    }

                    if (uiComponentsWithColors.isEmpty()) null
                    else
                        UiCircuitParams(
                            null,
                            circles = { size ->
                                uiComponentsWithColors.map { pair ->
                                    pair.first.circle(size).copy(color = pair.second)
                                }
                            }
                        )

                }
                if (params.isEmpty()) oldCircuitUi
                else oldCircuitUi.copy(
                    opacity = .25f,
                    circles = { size ->
                        params.fold(listOf()) { circles, param -> circles + param.circles(size) }
                    })
            }
        }
    }
}
