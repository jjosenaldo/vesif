package ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ui.common.CircuitImage
import ui.common.SecondaryMenuBar
import ui.common.VerticalDivider
import ui.common.tertiaryBackgroundColor
import ui.model.UiCircuitParams

@Composable
fun MainScreenContent(
    params: UiCircuitParams,
    pane: @Composable () -> Unit
) {
    Column {
        SecondaryMenuBar()
        Row {
            pane()
            VerticalDivider(color = tertiaryBackgroundColor)
            CircuitImage(params)
        }
    }
}