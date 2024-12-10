package ui.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AppButtonPreview() {
    Row {
        AppButton(onClick = {}) {
            AppText("Primary")
        }
        AppButton(onClick = {}, kind = AppButtonKind.Secondary) {
            AppText("Secondary")
        }
    }
}

enum class AppButtonKind {
    Primary, Secondary
}

@Composable
fun AppButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    kind: AppButtonKind = AppButtonKind.Primary,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit)
) {
    val (buttonColors, textStyle) = when (kind) {
        AppButtonKind.Primary -> Pair(
            LocalPrimaryButtonColors.current,
            if (enabled) TextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                letterSpacing = 0.2.sp,
                color = Color(0xffffffff)
            ) else TextStyle()
        )

        AppButtonKind.Secondary -> Pair(LocalSecondaryButtonColors.current, TextStyle())
    }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = onClick
            )
            .border(2.dp, buttonColors.borderColor(enabled = enabled), LocalShapes.current.buttonShape)
            .padding(1.dp)
            .background(buttonColors.containerColor(enabled = enabled))
            .padding(start = 9.dp, end = 9.dp, top = 5.dp, bottom = 7.dp)
            .height(26.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides LocalContentColor.current.copy(
                alpha =
                if (enabled) ContentAlpha.high else ContentAlpha.disabled
            ),
            LocalTextStyle provides LocalTextStyle.current.merge(textStyle)
        ) {
            content()
        }
    }
}

