package ui.screens.main.sub_screens.assertions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import ui.common.AppIconButton
import ui.common.AppText
import ui.screens.main.sub_screens.assertions.model.MultiselectAssertionData

@Composable
fun <T> MultiselectAssertionDataView(
    data: MultiselectAssertionData<T>,
    modifier: Modifier = Modifier,
    onNewData: (MultiselectAssertionData<T>) -> Unit,
) {
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        AppIconButton(
                            imageVector = Icons.Default.DoNotDisturbOn,
                            tint = Color.Red,
                            contentDescription = "Remove",
                            size = 18.dp,
                        ) { onNewData(data.apply { removeRow(key) }) }
                        Spacer(Modifier.width(4.dp))
                        Dropdown(
                            selected = key,
                            options = data.pendingKeys,
                            enabled = data.canAddRow(),
                            optionText = { it },
                            onClick = { onNewData(data.apply { editKey(oldKey = key, newKey = it) }) },
                            modifier = Modifier.weight(1f, false)
                        )
                    }

                    DataView(selected = value, data = data) {
                        onNewData(data.apply { editValue(key, it) })
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
    selected: T,
    data: MultiselectAssertionData<T>,
    onDataChanged: (T) -> Unit
) {
    Dropdown(
        selected = selected,
        options = data.values,
        enabled = true,
        optionText = { data.getValueInfo(it).text },
        optionColor = { data.getValueInfo(it).color },
        onClick = onDataChanged,
    )
}

@Composable
private fun <T> Dropdown(
    selected: T,
    options: List<T>,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    optionText: (T) -> String,
    optionColor: ((T) -> Color)? = null,
    onClick: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = (
                if (enabled)
                    Modifier
                        .pointerHoverIcon(PointerIcon.Hand)
                        .clickable(onClick = { expanded = true })
                else Modifier
                )
            .padding(horizontal = 2.dp)
    ) {
        AppText(
            optionText(selected),
            maxLines = 2,
            modifier = modifier,
            color = optionColor?.invoke(selected)
        )
        Icon(
            Icons.Default.KeyboardArrowDown, "Select",
            tint = Color.Gray
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        options.forEach { option ->
            DropdownMenuItem(onClick = {
                expanded = false
                onClick(option)
            }) {
                AppText(
                    optionText(option),
                    color = optionColor?.invoke(option)
                )
            }
        }
    }
}
