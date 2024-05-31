package presentation.model

import androidx.compose.ui.geometry.Size
import java.io.File

class UiFailedRingBell(val contact: UiComponent, private val inputs: List<UiComponent>, circuitImage: File) :
    UiFailedAssertion(circuitImage) {
    fun circles(canvasSize: Size): List<UiCircleInfo> {
        return (listOf(contact) + inputs).map { it.circle(canvasSize) }
    }
}