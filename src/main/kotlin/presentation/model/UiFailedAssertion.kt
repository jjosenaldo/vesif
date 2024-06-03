package presentation.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString

abstract class UiFailedAssertion(val circuitParams: UiCircuitParams) {
    abstract val details: AnnotatedString

    companion object {
        val DEFAULT = object : UiFailedAssertion(UiCircuitParams.DEFAULT) {
            override val details = buildAnnotatedString { }
        }
    }
}