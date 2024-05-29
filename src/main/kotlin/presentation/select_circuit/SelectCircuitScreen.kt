package presentation.select_circuit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.AppScreen
import presentation.select_project.ProjectViewModel
import java.io.File

@Composable
fun SelectCircuitScreen(
    navController: NavHostController,
    projectVm: ProjectViewModel = koinInject(),
    circuitVm: CircuitViewModel = koinInject()
) {
    val scope = rememberCoroutineScope()

    Row {
        Column(modifier = Modifier.fillMaxHeight()) {
            Box(modifier = Modifier.weight(1f, false)) {
                val stateVertical = rememberScrollState(0)

                Column(modifier = Modifier.verticalScroll(stateVertical)) {
                    projectVm.circuitsPaths.forEach { path ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = circuitVm.selectedCircuitPath == path,
                                onClick = {
                                    if (circuitVm.selectedCircuitPath != path && circuitVm.loadCircuitState !is LoadCircuitLoading) {
                                        scope.launch { circuitVm.selectCircuit(path) }
                                    }
                                }
                            )
                            Text(
                                text = circuitVm.getCircuitName(path)
                            )
                        }
                    }
                }
                VerticalScrollbar(
                    modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
                    adapter = rememberScrollbarAdapter(
                        stateVertical
                    )
                )
            }
            SelectCircuitButton(navController)
        }

        CircuitImageCanvas(circuitVm.selectedCircuitImage)
    }

}

@Composable
private fun SelectCircuitButton(
    navController: NavHostController,
    circuitVm: CircuitViewModel = koinInject()
) {
    Button(
        enabled = circuitVm.selectedCircuitPath.isNotEmpty() && circuitVm.loadCircuitState !is LoadCircuitLoading,
        onClick = {
            navController.navigate(
                AppScreen.Assertions.name
            )

        }) {
        if (circuitVm.loadCircuitState is LoadCircuitLoading)
            CircularProgressIndicator()
        else
            Text(
                text = "Select circuit"
            )
    }
}

@Composable
private fun CircuitImage(circuitImage: File?) {
    if (circuitImage == null) return

    Image(
        painter = BitmapPainter(image = loadImageBitmap(circuitImage.inputStream())),
        // TODO: circuit name
        contentDescription = "Image from circuit"
    )
}

@Composable
private fun CircuitImageCanvas(circuitImage: File?) {
    if (circuitImage == null) return

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawImage(loadImageBitmap(circuitImage.inputStream()))
    }
}