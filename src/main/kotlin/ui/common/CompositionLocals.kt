package ui.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


data class AppButtonColors(
    val enabledContainerColor: Color,
    val disabledContainerColor: Color,
    val contentColor: Color,
    val enabledBorderColor: Color,
    val disabledBorderColor: Color
) {
    fun containerColor(enabled: Boolean): Color {
        return if (enabled) enabledContainerColor else disabledContainerColor
    }

    fun borderColor(enabled: Boolean): Color {
        return if (enabled) enabledBorderColor else disabledBorderColor
    }
}

data class AppShapes(val buttonShape: Shape)

val LocalPrimaryButtonColors = staticCompositionLocalOf {
    defaultPrimaryButtonColors
}
val LocalSecondaryButtonColors = staticCompositionLocalOf {
    defaultSecondaryButtonColors
}

val defaultInputSelectedColor = Color(0xff3674f0)
val defaultInputUnselectedColor = Color(0xffffffff)

val defaultPrimaryButtonColors by lazy {
    AppButtonColors(
        defaultInputSelectedColor,
        Color(0xffe4e6eb),
        Color(0xfff9fbff),
        defaultInputSelectedColor,
        tertiaryBackgroundColor
    )
}

val defaultSecondaryButtonColors by lazy {
    AppButtonColors(
        defaultInputUnselectedColor,
        primaryBackgroundColor,
        Color(0xfff9fbff),
        tertiaryBackgroundColor,
        tertiaryBackgroundColor
    )
}

val defaultTextStyle = TextStyle(color = Color(0xff24150e))
val defaultErrorTextStyle = TextStyle(color = Color.Red)

val primaryBackgroundColor = Color(0xffe4e6eb)
val secondaryBackgroundColor = Color(0xfff2f3f7)
val tertiaryBackgroundColor = Color(0xffc6ccd3)
val iconColor = Color(0xff777a87)


// TODO(td): this is unlikely to change, so do not provide it as theme
val LocalShapes = staticCompositionLocalOf {
    AppShapes(RoundedCornerShape(4.dp))
}


