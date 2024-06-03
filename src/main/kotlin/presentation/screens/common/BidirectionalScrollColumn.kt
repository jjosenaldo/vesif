package presentation.screens.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BidirectionalScrollColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = modifier) {
        val stateVertical = rememberScrollState(0)
        val stateHorizontal = rememberScrollState(0)

        Column(
            modifier = Modifier
                .verticalScroll(stateVertical)
                .horizontalScroll(stateHorizontal)
        ) {
            content()
        }

        VerticalScrollbar(
            modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
        HorizontalScrollbar(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomStart),
            adapter = rememberScrollbarAdapter(stateHorizontal)
        )
    }
}

