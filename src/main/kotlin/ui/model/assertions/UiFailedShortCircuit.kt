package ui.model.assertions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import ui.model.UiCircuitParams
import ui.model.UiComponent
import ui.model.UiPathInfo
import java.io.File

// TODO(ft): consider levers on inputs
class UiFailedShortCircuit(
    private val shortCircuit: List<UiComponent>,
    private val inputs: List<UiComponent>,
    circuitImage: File
) :
    UiFailedAssertion(
        circuitParams = UiCircuitParams(
            circuitImage,
            { inputs.map { input -> input.circle(it).copy(color = inputColor) } }
        ) {
            listOf(UiPathInfo(color = pathColor, path = shortCircuit.map { component ->
                component.center(it)
            }))
        }
    ) {
    override val details = buildAnnotatedString {
        append("Short circuit found from ")
        appendWithColor(shortCircuit.first().name, pathColor)
        append(" to ")
        appendWithColor(shortCircuit.last().name, pathColor)
        append(" when pressing the button")
        if (inputs.size > 1) append("s")
        append(" ")
        appendWithColor(inputs, ", ", inputColor, UiComponent::name)
    }

    private fun AnnotatedString.Builder.appendWithColor(text: String, color: Color) {
        withStyle(style = SpanStyle(color = color)) {
            append(text)
        }
    }

    private fun <T> AnnotatedString.Builder.appendWithColor(
        texts: List<T>,
        separator: String,
        color: Color,
        transform: (T) -> String
    ) {
        texts.indices.forEach {
            if (it > 0) append(separator)
            appendWithColor(transform(texts[it]), color)
        }
    }

    companion object {
        private val pathColor = Color.Red
        private val inputColor = Color.Blue
    }
}