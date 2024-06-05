package ui.model.assertions

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import ui.model.UiCircuitParams

abstract class UiFailedAssertion(val circuitParams: UiCircuitParams) {
    abstract val details: AnnotatedString

    companion object {
        val DEFAULT = object : UiFailedAssertion(UiCircuitParams.DEFAULT) {
            override val details = buildAnnotatedString { }
        }
    }
}