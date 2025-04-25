package ui.screens.main.sub_screens.select_circuit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import input.model.ClearsyCircuit
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.common.*
import ui.navigation.AppNavigator


@Composable
fun SelectCircuitPane(
    circuitVm: CircuitViewModel = koinInject()
) {
    val scope = rememberCoroutineScope()
    Pane {
        BidirectionalScrollBar(modifier = Modifier.fillMaxHeight().weight(1f)) {
            circuitVm.circuits.forEachIndexed { index, circuit ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = circuitVm.selectedCircuit.name == circuit.name,
                        onClick = {
                            if (circuitVm.selectedCircuit.name != circuit.name ) {
                                scope.launch { circuitVm.selectCircuit(index) }
                            }
                        }
                    )
                    AppText(text = circuit.name)
                }
            }
        }
        Divider(color = tertiaryBackgroundColor)
        SelectCircuitButton()
    }
}


@Composable
private fun SelectCircuitButton(
    circuitVm: CircuitViewModel = koinInject(),
    navigator: AppNavigator = koinInject()
) {
    AppButton(
        onClick = { circuitVm.confirmCircuitSelection(navigator) },
        enabled = circuitVm.selectedCircuit != ClearsyCircuit.DEFAULT,
        modifier = Modifier.padding(16.dp)
    ) {
        AppText(
            text = "Select circuit"
        )
    }
}
