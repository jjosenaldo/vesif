package ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ui.model.UiCircuitParams

@Composable
fun CircuitImage(params: UiCircuitParams) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = secondaryBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        CircuitImageContent(params)
    }
}

@Composable
fun CircuitImageContent(params: UiCircuitParams) {
    val zoom = params.zoomLevel.factor
    if (params.image == null) return

    val image = remember(params.image) {
        loadImageBitmap(params.image.inputStream())
    }
    val size = Size(
        image.width.toFloat() * zoom,
        image.height.toFloat() * zoom
    )

    BidirectionalScrollBar(
        modifier = Modifier
            .height(size.height.dp)
            .width(size.width.dp)
    ) {

        val paths = remember {
            params.paths(size).map { Path() }
        }

        Canvas(
            modifier = Modifier
                .width(size.width.dp)
                .height(size.height.dp)
        ) {
            drawImage(image, dstSize = IntSize(size.width.toInt(), size.height.toInt()))

            params.circles(size).forEach {
                drawCircle(
                    center = it.center,
                    color = it.color,
                    radius = it.radius,
                    style = Stroke(
                        width = 3.0f * zoom
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
                        width = 3.0f * zoom
                    )
                )
            }
        }
    }
}