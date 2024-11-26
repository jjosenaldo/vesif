package ui.common

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class AppTextKind {
    Regular,
    Error
}

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    kind: AppTextKind = AppTextKind.Regular,
    maxLines: Int = Int.MAX_VALUE,
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
            color = actualStyle.color.copy(alpha = LocalContentColor.current.alpha)
        ),
        maxLines = maxLines,
    )
}
