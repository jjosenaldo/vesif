package presentation.select_circuit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.AppScreen
import presentation.select_project.ProjectViewModel

@Composable
fun SelectCircuitScreen(
    navController: NavHostController,
    projectVm: ProjectViewModel = koinInject(),
    circuitVm: CircuitViewModel = koinInject()
) {
    val scope = rememberCoroutineScope()

    Column {
        projectVm.circuitsPaths.forEach { path ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = circuitVm.selectedCircuitPath == path,
                    onClick = { circuitVm.selectCircuit(path) }
                )
                Text(
                    text = circuitVm.getCircuitName(path)
                )
            }
        }
        Button(
            enabled = circuitVm.selectedCircuitPath.isNotEmpty() && circuitVm.loadCircuitState !is LoadCircuitLoading,
            onClick = {
                scope.launch {
                    circuitVm.loadCircuitFromXml(circuitVm.selectedCircuitPath) {
                        navController.navigate(
                            AppScreen.Assertions.name
                        )
                    }
                }
            }) {
            if (circuitVm.loadCircuitState is LoadCircuitLoading)
                CircularProgressIndicator()
            else
                Text(
                    text = "Select circuit"
                )
        }
    }
}