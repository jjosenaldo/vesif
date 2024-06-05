package ui.screens.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import ui.model.UiCircuitParams

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
        val size = remember { Size(image.width.toFloat(), image.height.toFloat()) }
        val paths = remember {
            params.paths(size).map { Path() }
        }

        Canvas(modifier = Modifier.width(image.width.dp).height(image.height.dp)) {
            drawImage(image)

            params.circles(size).forEach {
                drawCircle(
                    center = it.center,
                    color = it.color,
                    radius = it.radius,
                    style = Stroke(
                        width = 3.0f
                    )
                )
            }
            params.paths(size).forEachIndexed { index, path ->
                val uiPath = paths[index]
                uiPath.run {
                    reset()
                    val points = path.path
                    moveTo(points[0].x, points[1].y)
                    (1 until points.size).forEach {
                        lineTo(points[it].x, points[it].y)
                    }
                }
                drawPath(
                    path = paths[index],
                    color = path.color,
                    style = Stroke(
                        width = 3.0f
                    )
                )
            }
        }
    }
}