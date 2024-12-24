package ui.screens.main.sub_screens.assertions.model

import androidx.compose.ui.graphics.Color
import core.model.BinaryOutput

class ComponentStatusAssertionData(components: List<BinaryOutput>) : ComponentAssertionData<BinaryOutput, Boolean>(
    defaultValue = true,
    components = components,
    values = listOf(true, false)
) {
    override fun getValueInfo(value: Boolean): AssertionDataValueInfo {
        return if (value) AssertionDataValueInfo("On", Color.Blue)
        else AssertionDataValueInfo("Off", Color.Gray)
    }
}