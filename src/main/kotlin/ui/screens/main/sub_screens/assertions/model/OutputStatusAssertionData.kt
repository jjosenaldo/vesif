package ui.screens.main.sub_screens.assertions.model

import androidx.compose.ui.graphics.Color
import core.model.BinaryOutput

class OutputStatusAssertionData(components: List<BinaryOutput>) : BinaryComponentAssertionData<BinaryOutput>(
    defaultValue = true,
    components = components,
    values = listOf(true, false)
) {
    override fun getValueInfo(key: BinaryOutput, value: Boolean): AssertionDataValueInfo {
        return if (value) AssertionDataValueInfo("On", Color.Blue)
        else AssertionDataValueInfo("Off", Color.Gray)
    }
}