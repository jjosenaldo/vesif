package presentation.model

import androidx.compose.ui.geometry.Size
import java.io.File

data class UiCircuitParams(val image: File?, val circles: (Size) -> List<UiCircleInfo> = { listOf() }) {
    companion object {
        val DEFAULT = UiCircuitParams(null)
    }
}