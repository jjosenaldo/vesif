package ui.common

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

sealed class HyperLinkAction(val start: Int, val end: Int) {
    abstract fun action()
}

@Composable
fun HyperLinkText(
    text: String,
    links: List<HyperLinkAction>,
    modifier: Modifier = Modifier,
    linkTextColor: Color = Color.Blue,
    linkTextFontWeight: FontWeight = FontWeight.Medium,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = buildAnnotatedString {
        append(text)
        links.forEach { link ->
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = link.start,
                end = link.end
            )
            addStringAnnotation(
                tag = "${link.start}",
                annotation = "",
                start = link.start,
                end = link.end
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = text.length
        )
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            links.forEach loop@{ link ->
                annotatedString
                    .getStringAnnotations("${link.start}", it, it)
                    .firstOrNull()?.let {
                        link.action()
                        return@loop
                    }
            }
        }
    )
}
