package presentation.model

import core.model.Circuit
import input.model.ClearsyCircuit

data class UiCircuit(val circuit: ClearsyCircuit, val imagePath: String) {
    companion object {
        val DEFAULT = UiCircuit(ClearsyCircuit.DEFAULT, "")
    }
}