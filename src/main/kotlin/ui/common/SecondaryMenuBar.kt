package ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.navigation.AppNavigator

@Composable
fun SecondaryMenuBar() {
    Row {
        BackButton()
    }
}

@Composable
private fun BackButton(navigator: AppNavigator = koinInject()) {
    Button(onClick = navigator::navBack) {
        Text("<-")
    }
}
