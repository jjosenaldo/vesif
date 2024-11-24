package ui.screens.main.sub_screens.assertions

import verifier.model.assertions.AssertionData
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

sealed class AssertionState(val type: AssertionType, val data: AssertionData) {
    val name = type.assertionName

    abstract fun withData(data: AssertionData?): AssertionState
}

class AssertionRunning(type: AssertionType, data: AssertionData) : AssertionState(type, data) {
    override fun withData(data: AssertionData?): AssertionState {
        return AssertionRunning(type = type, data = data ?: this.data)
    }
}

class AssertionInitial(type: AssertionType, val selected: Boolean = false, data: AssertionData) : AssertionState(
    type,
    data
) {
    fun copyWith(selected: Boolean?): AssertionInitial {
        return AssertionInitial(type = type, selected = selected ?: this.selected, data = data)
    }

    override fun withData(data: AssertionData?): AssertionState {
        return AssertionInitial(type = type, selected = selected, data = data ?: this.data)
    }
}

class AssertionError(type: AssertionType, val error: Throwable, data: AssertionData) : AssertionState(type, data) {
    override fun withData(data: AssertionData?): AssertionState {
        return AssertionError(type = type, error = error, data = data ?: this.data)
    }
}

class AssertionNotSelected(type: AssertionType, data: AssertionData) : AssertionState(type, data) {
    override fun withData(data: AssertionData?): AssertionState {
        return AssertionNotSelected(type = type, data = data ?: this.data)
    }
}

class AssertionPassed(type: AssertionType, data: AssertionData) : AssertionState(type, data) {
    override fun withData(data: AssertionData?): AssertionState {
        return AssertionPassed(type = type, data = data ?: this.data)
    }
}

class AssertionFailed(
    type: AssertionType,
    val results: List<AssertionRunResult>,
    data: AssertionData
) : AssertionState(type, data) {
    override fun withData(data: AssertionData?): AssertionState {
        return AssertionFailed(type = type, results = results, data = data ?: this.data)
    }
}
