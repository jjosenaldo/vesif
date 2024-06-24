package ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.navigation.AppNavigator
import ui.screens.main.MainScreenId

@Composable
fun SecondaryMenuBar(navigator: AppNavigator = koinInject()) {
    Row {
        when (navigator.currentMainScreen) {
            MainScreenId.Assertions, MainScreenId.FailedAssertion -> BackButton()
            else -> {}
        }
    }
}

@Composable
private fun BackButton(navigator: AppNavigator = koinInject()) {
    Button(onClick = navigator::navBack) {
        Text("<-")
    }
}
