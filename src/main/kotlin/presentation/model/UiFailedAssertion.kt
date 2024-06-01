package presentation.model

abstract class UiFailedAssertion(val circuitParams: UiCircuitParams) {
    companion object {
        val DEFAULT = object : UiFailedAssertion(UiCircuitParams.DEFAULT) {}
    }
}