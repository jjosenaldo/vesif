package ui.screens.main.sub_screens.assertions.model

import androidx.compose.ui.graphics.Color
import core.model.Lamp

class LampStatusAssertionData(lamps: List<Lamp>) : ComponentAssertionData<Lamp, Boolean>(
    defaultValue = true,
    components = lamps,
    values = listOf(true, false)
) {
    override fun getValueInfo(value: Boolean): AssertionDataValueInfo {
        return if (value) AssertionDataValueInfo("On", Color.Blue)
        else AssertionDataValueInfo("Off", Color.Gray)
    }
}