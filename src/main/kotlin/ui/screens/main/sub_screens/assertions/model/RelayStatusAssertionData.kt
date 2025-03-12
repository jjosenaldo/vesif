package ui.screens.main.sub_screens.assertions.model

import androidx.compose.ui.graphics.Color
import core.model.*

class RelayStatusAssertionData(relays: List<MonostableRelay>) :
    BinaryComponentAssertionData<MonostableRelay>(
        defaultValue = true,
        components = relays,
        values = listOf(true, false)
    ) {
    override fun getValueInfo(key: MonostableRelay, value: Boolean): AssertionDataValueInfo {
        val (texts,colors) = getValueTextsAndColors()
        val (trueText, falseText) = texts
        val (trueColor, falseColor) = colors
        return if (value) AssertionDataValueInfo(trueText, trueColor)
        else AssertionDataValueInfo(falseText, falseColor)
    }

    private fun getValueTextsAndColors(): Pair<Pair<String, String>, Pair<Color, Color>> {
        return Pair(Pair("On","Off"), Pair(Color.Blue, Color.Gray))
    }
}