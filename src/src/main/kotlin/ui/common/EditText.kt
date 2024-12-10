package ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppEditText(initialValue: String, modifier: Modifier = Modifier, onValueChanged: (String) -> String) {
    var text by remember { mutableStateOf(initialValue) }
    val interactionSource = remember { MutableInteractionSource() }
    val colors = OutlinedTextFieldDefaults.colors().copy(
        focusedContainerColor = defaultInputUnselectedColor,
        unfocusedContainerColor = defaultInputUnselectedColor
    )

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicTextField(
            value = text,
            modifier = modifier,
            onValueChange = {
                text = onValueChanged(it)
            },
            singleLine = true,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(colors.cursorColor),

            ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = text,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(
                    start = 4.dp,
                    top = 2.dp,
                    bottom = 2.dp,
                    end = 4.dp
                ), // this is how you can remove the padding,
                colors = colors,
                container = {
                    OutlinedTextFieldDefaults.ContainerBox(
                        enabled = true,
                        isError = false,
                        interactionSource,
                        colors,
                        OutlinedTextFieldDefaults.shape
                    )
                }
            )
        }
    }
}