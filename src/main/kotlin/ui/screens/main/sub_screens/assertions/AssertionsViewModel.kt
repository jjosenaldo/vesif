package ui.screens.main.sub_screens.assertions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.model.Lamp
import core.model.MonostableSimpleContact
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
                        contacts = circuitViewModel.selectedCircuit.circuit.components.filterIsInstance<MonostableSimpleContact>()
                    )

                    AssertionType.LampStatus -> LampStatusAssertionData(
                        lamps = circuitViewModel.selectedCircuit.circuit.components.filterIsInstance<Lamp>()
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

    fun updateMultiselect(assertionType: AssertionType, newData: MultiselectAssertionData<*>) {
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
        var contactStatusData: ContactStatusAssertionData? = null
        var lampStatusData: LampStatusAssertionData? = null

        for (assertion in assertions) {
            when (assertion.data) {
                is ContactStatusAssertionData -> contactStatusData = assertion.data
                is LampStatusAssertionData -> lampStatusData = assertion.data
                else -> {}
            }
        }

        val allStatusData = listOfNotNull(contactStatusData, lampStatusData)
        uiCircuitModifier = if (allStatusData.isEmpty()) {
            { it }
        } else {
            { oldCircuitUi ->
                val params = allStatusData.mapNotNull { statusData ->
                    val uiComponentsWithColors = statusData.selectedData.mapNotNull { pair ->
                        val contactName = pair.first
                        val status = pair.second
                        val component = statusData.getComponentByName(contactName)
                            ?.toUiComponent(circuitViewModel.selectedCircuit)
                        if (component == null) null
                        else Pair(component, statusData.getValueInfo(status).color)
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
