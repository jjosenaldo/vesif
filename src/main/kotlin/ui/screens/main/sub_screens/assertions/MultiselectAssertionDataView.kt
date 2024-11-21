package ui.screens.main.sub_screens.assertions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.common.AppButton
import ui.common.AppIconButton
import ui.common.AppText

@Composable
fun <T> MultiselectAssertionDataView(
    data: MultiselectAssertionData<T>,
    onNewData: (MultiselectAssertionData<T>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    if (data.selectedData.isNotEmpty())
        Column {
            data.selectedData.forEach { pair ->
                val key = pair.first
                val value = pair.second

                Row {
                    AppIconButton(
                        imageVector = Icons.Default.Remove,
                        tint = Color.Red,
                        contentDescription = "Remove contact"
                    ) { onNewData(data.apply { removeRow(key) }) }
                    AppText(key, modifier = Modifier.clickable(onClick = {
                        if (data.canAddRow())
                            expanded = true
                    }))
                    DataView(data = value) {
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
        }

    if (data.canAddRow()) {
        AppButton(onClick = {
            onNewData(data.apply { addRow() })
        }) {
            AppText("+")
        }
    }
}

@Composable
private fun <T> DataView(
    data: T,
    onDataChanged: (T) -> Unit
) {
    if (data is Boolean) {
        val boolVal: Boolean = data
        AppButton(onClick = {
            @Suppress("UNCHECKED_CAST")
            onDataChanged(!boolVal as T)
        }) {
            AppText(if (boolVal) "Closed" else "Open")
        }
    }
}