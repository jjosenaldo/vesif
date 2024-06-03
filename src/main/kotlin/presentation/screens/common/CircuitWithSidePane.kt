package presentation.screens.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import presentation.model.UiCircuitParams

@Composable
fun CircuitWithSidePane(
    params: UiCircuitParams,
    pane: @Composable () -> Unit
) {
    Row {
        pane()
        VerticalDivider()
        CircuitImage(params)
    }
}


@Composable
private fun CircuitImage(params: UiCircuitParams) {
    if (params.image == null) return

    val image = remember(params.image) {
        loadImageBitmap(params.image.inputStream())
    }

    BidirectionalScrollBar {
        Canvas(modifier = Modifier.width(image.width.dp).height(image.height.dp)) {
            drawImage(image)
            params.circles(Size(image.width.toFloat(), image.height.toFloat())).forEach {
                drawCircle(
                    center = it.center,
                    color = it.color,
                    radius = it.radius,
                    style = Stroke(
                        width = 3.0f
                    )
                )
            }
        }
    }
}