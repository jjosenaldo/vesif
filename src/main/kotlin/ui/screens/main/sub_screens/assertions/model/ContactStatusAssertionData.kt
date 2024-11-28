package ui.screens.main.sub_screens.assertions.model

import androidx.compose.ui.graphics.Color
import core.model.MonostableSimpleContact

class ContactStatusAssertionData(contacts: List<MonostableSimpleContact>) :
    ComponentAssertionData<MonostableSimpleContact, Boolean>(
        defaultValue = true,
        components = contacts,
        values = listOf(true, false)
    ) {
    override fun getValueInfo(value: Boolean): AssertionDataValueInfo {
        return if (value) AssertionDataValueInfo("Closed", Color.Blue)
        else AssertionDataValueInfo("Open", Color.Gray)
    }
}