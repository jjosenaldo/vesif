package ui.common

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp
import core.utils.files.FileManager

enum class AppTextKind {
    Regular,
    Error
}

private val fontFamily = FontFamily(
    Font(
        FileManager.getResource("fonts/inter/Inter-Black.ttf"),
        weight = FontWeight.Black,
        style = FontStyle.Normal
    ),
    Font(
        FileManager.getResource("fonts/inter/Inter-Bold.ttf"),
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        FileManager.getResource("fonts/inter/Inter-ExtraBold.ttf"),
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),
    Font(
        FileManager.getResource("fonts/inter/Inter-ExtraLight.ttf"),
        weight = FontWeight.ExtraLight,
        style = FontStyle.Normal
    ),
    Font(
        FileManager.getResource("fonts/inter/Inter-Light.ttf"),
        weight = FontWeight.Light,
        style = FontStyle.Normal
    ),
    Font(
        FileManager.getResource("fonts/inter/Inter-Medium.ttf"),
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        FileManager.getResource("fonts/inter/Inter-Regular.ttf"),
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        FileManager.getResource("fonts/inter/Inter-SemiBold.ttf"),
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        FileManager.getResource("fonts/inter/Inter-Thin.ttf"),
        weight = FontWeight.Thin,
        style = FontStyle.Normal
    ),

    )

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    kind: AppTextKind = AppTextKind.Regular,
    maxLines: Int = Int.MAX_VALUE,
    color: Color? = null
) {
    val actualStyle = when (kind) {
        AppTextKind.Regular -> LocalTextStyle.current
        AppTextKind.Error -> LocalTextStyle.current.merge(
            defaultErrorTextStyle
        )
    }
    Text(
        text,
        modifier = modifier,
        style = actualStyle.copy(
            color = color ?: actualStyle.color.copy(alpha = LocalContentColor.current.alpha),
            fontFamily = fontFamily,
            fontSize = 14.sp,
        ),
        maxLines = maxLines,
    )
}
