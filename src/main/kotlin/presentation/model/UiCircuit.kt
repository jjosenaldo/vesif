package presentation.model

import core.model.Circuit

data class UiCircuit(val circuit: Circuit, val imagePath: String) {
    companion object {
        val DEFAULT = UiCircuit(Circuit.DEFAULT, "")
    }
}