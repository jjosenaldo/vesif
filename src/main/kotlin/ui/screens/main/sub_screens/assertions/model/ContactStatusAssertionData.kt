package ui.screens.main.sub_screens.assertions.model

import androidx.compose.ui.graphics.Color
import core.model.*

class ContactStatusAssertionData(contacts: List<Contact>) :
    BinaryComponentAssertionData<Contact>(
        defaultValue = true,
        components = contacts,
        values = listOf(true, false)
    ) {
    override fun getValueInfo(key: Contact, value: Boolean): AssertionDataValueInfo {
        val (texts,colors) = getValueTextsAndColors(key)
        val (trueText, falseText) = texts
        val (trueColor, falseColor) = colors
        return if (value) AssertionDataValueInfo(trueText, trueColor)
        else AssertionDataValueInfo(falseText, falseColor)
    }

    private fun getValueTextsAndColors(key: Contact): Pair<Pair<String, String>, Pair<Color, Color>> {
        return when {
            key is MonostableSimpleContact -> Pair(Pair("Closed","Open"), Pair(Color.Blue, Color.Gray))
            key is RelayChangeoverContact && key.controller is MonostableRelay -> Pair(Pair("Up","Down"), Pair(Color.Blue, Color.Green))
            key is RelayChangeoverContact && key.controller is BistableRelay -> Pair(Pair("Left","Right"), Pair(Color.Blue, Color.Green))
            else ->  Pair(Pair("True","False"), Pair(Color.Blue, Color.Gray))
        }
    }
}