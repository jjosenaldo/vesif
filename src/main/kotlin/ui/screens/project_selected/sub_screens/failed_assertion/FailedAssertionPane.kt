package ui.screens.project_selected.sub_screens.failed_assertion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ui.common.Pane
import ui.model.assertions.UiFailedAssertion

@Composable
fun FailedAssertionPane(viewModel: FailedAssertionViewModel = koinInject()) {
    Pane {
        LazyColumn {
            viewModel.failedAssertions.map {
                item {
                    FailedAssertionView(it)
                }
            }
        }
    }

}

@Composable
private fun FailedAssertionView(assertion: UiFailedAssertion, viewModel: FailedAssertionViewModel = koinInject()) {
    val isSelected = assertion.id == viewModel.selectedFailedAssertion.id
    val backgroundColor = if (isSelected) Color.LightGray else Color.White

    Surface(
        color = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable(onClick = { viewModel.select(assertion) })
    ) {
        Box(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = assertion.details,
                style = TextStyle(background = Color.Transparent),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
