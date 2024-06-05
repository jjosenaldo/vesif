package ui.screens.project_selected.assertions

import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

sealed class AssertionState(val type: AssertionType) {
    val name = type.assertionName
}

class AssertionRunning(type: AssertionType) : AssertionState(type)

class AssertionInitial(type: AssertionType, val selected: Boolean = false) : AssertionState(type) {
    fun copyWith(selected: Boolean?): AssertionInitial {
        return AssertionInitial(type = type, selected = selected ?: this.selected)
    }
}

class AssertionNotSelected(type: AssertionType) : AssertionState(type)
class AssertionPassed(type: AssertionType) : AssertionState(type)
class AssertionFailed(type: AssertionType, val results: List<AssertionRunResult>) : AssertionState(type)
