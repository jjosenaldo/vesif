package ui.model.assertions

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import ui.model.UiCircuitParams

abstract class UiFailedAssertion {
    abstract val details: AnnotatedString
    abstract val id: String
    abstract fun modifyCircuitParams(circuitParams: UiCircuitParams): UiCircuitParams

    companion object {
        val DEFAULT = object : UiFailedAssertion() {
            override val details = buildAnnotatedString { }
            override val id = ""
            override fun modifyCircuitParams(circuitParams: UiCircuitParams) = circuitParams
        }
    }
}