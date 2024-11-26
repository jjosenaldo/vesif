package ui.screens.main.sub_screens.assertions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import ui.common.AppIconButton
import ui.common.AppText
import verifier.model.assertions.MultiselectAssertionData

@Composable
fun <T> MultiselectAssertionDataView(
    data: MultiselectAssertionData<T>,
    modifier: Modifier = Modifier,
    onNewData: (MultiselectAssertionData<T>) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    if (data.selectedData.isNotEmpty())
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(3.dp) // Adds 16.dp spacing between items
        ) {
            data.selectedData.forEach { pair ->
                val key = pair.first
                val value = pair.second

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AppIconButton(
                        imageVector = Icons.Default.DoNotDisturbOn,
                        tint = Color.Red,
                        contentDescription = "Remove",
                        size = 18.dp,
                    ) { onNewData(data.apply { removeRow(key) }) }
                    AppText(
                        key,
                        modifier = (
                                if (data.canAddRow()) Modifier
                                    .pointerHoverIcon(PointerIcon.Hand)
                                    .clickable(onClick = { expanded = true })
                                else Modifier
                                )
                            .padding(horizontal = 8.dp)
                            .weight(1f),
                        maxLines = 2,
                    )
                    DataView(value = value) {
                        onNewData(data.apply { editValue(key, it) })
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    data.pendingKeys.forEach { listKey ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            onNewData(data.apply { editKey(oldKey = key, newKey = listKey) })
                        }) {
                            AppText(listKey)
                        }
                    }
                }
            }

            if (data.canAddRow()) {
                Spacer(modifier = Modifier.width(3.dp))
                AppIconButton(
                    imageVector = Icons.Default.AddCircle,
                    tint = Color.Blue,
                    contentDescription = "Add",
                    size = 18.dp,
                ) { onNewData(data.apply { addRow() }) }
            }
        }


}

@Composable
private fun <T> DataView(
    value: T,
    onDataChanged: (T) -> Unit
) {
    if (value is Boolean) {
        val boolVal: Boolean = value

        Switch(
            checked = boolVal,
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand).scale(0.75f).height(18.dp),
            onCheckedChange = {
                @Suppress("UNCHECKED_CAST")
                onDataChanged(it as T)
            }
        )
    }
}