package ui.screens.main.sub_screens.assertions.preview

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import core.model.MonostableSimpleContact
import ui.screens.main.sub_screens.assertions.MultiselectAssertionDataView
import ui.screens.main.sub_screens.assertions.model.ContactStatusAssertionData

@Preview
@Composable
fun ContactStatusSelectorLessRowsThanDataPreview() {
    val data = remember {
        ContactStatusAssertionData(
            contacts = listOf(
                MonostableSimpleContact(isNormallyOpen = true, name = "Contact 1"),
                MonostableSimpleContact(isNormallyOpen = true, name = "Contact 2"),
                MonostableSimpleContact(
                    isNormallyOpen = true,
                    name = "Contact 1 with a name that is potentially too large of a name and does not fit into a single row well I think"
                ),
                MonostableSimpleContact(isNormallyOpen = true, name = "Contact 4"),
                MonostableSimpleContact(isNormallyOpen = true, name = "Con")
            )
        ).apply {
            addRow()
            addRow()
            addRow()
        }
    }
    MultiselectAssertionDataView(
        data = data,
        onNewData = { }
    )
}