package presentation.model

import java.io.File

class UiFailedRingBell(val contact: UiComponent, private val inputs: List<UiComponent>, circuitImage: File) :
    UiFailedAssertion(
        UiCircuitParams(
            circuitImage
        ) { canvasSize -> (listOf(contact) + inputs).map { it.circle(canvasSize) } }
    )