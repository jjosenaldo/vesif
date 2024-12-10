package ui.model

import androidx.compose.ui.geometry.Size
import java.io.File

data class UiCircuitParams(
    val image: File?,
    val opacity: Float = 1f,
    val zoomLevel: UiZoomLevel = UiZoomLevel.ZOOM_100,
    val circles: (Size) -> List<UiCircleInfo> = { listOf() },
    val paths: (Size) -> List<UiPathInfo> = { listOf() }
) {
    companion object {
        val DEFAULT = UiCircuitParams(null)
    }
}