package presentation.failed_assertions

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import org.koin.compose.koinInject
import presentation.model.UiCircleInfo
import presentation.model.UiFailedRingBell
import java.io.File

@Composable
fun FailedAssertionsScreen(vm: FailedAssertionsViewModel = koinInject()) {
    vm.failedAssertions.forEach {
        when (it) {
            is UiFailedRingBell -> FailedRingBell(it)
        }
    }
}

@Composable
private fun FailedRingBell(assertion: UiFailedRingBell) {
    Image(circuitImage = assertion.circuitImage, circles = assertion::circles)
}

@Composable
private fun Image(circuitImage: File, circles: (Size) -> List<UiCircleInfo>) {
    val image = remember(circuitImage) {
        loadImageBitmap(circuitImage.inputStream())
    }
    Canvas(modifier = Modifier) {
        drawImage(image)
        circles(Size(image.width.toFloat(), image.height.toFloat())).forEach {
            println("drawing circle with radius ${it.radius}")
            drawCircle(
                center = it.center,
                color = Color.Red,
                radius = it.radius,
                style = Stroke(
                    width = 3.0f
                )
            )
        }
    }
}