package ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.West
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ui.navigation.AppNavigator
import ui.screens.main.MainScreenId

@Composable
fun SecondaryMenuBar(navigator: AppNavigator = koinInject()) {
    Row {
        when (navigator.currentMainScreen) {
            MainScreenId.Assertions, MainScreenId.FailedAssertion -> {
                Column {
                    BackButton()
                    Divider(color = tertiaryBackgroundColor)
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun BackButton(navigator: AppNavigator = koinInject()) {
    AppIconButton(
        imageVector = Icons.Default.West,
        contentDescription = "Back",
        tint = iconColor,
        modifier = Modifier
            .padding(start = 8.dp, top = 1.dp, bottom = 1.dp),
        onClick = navigator::navBack
    )
}


