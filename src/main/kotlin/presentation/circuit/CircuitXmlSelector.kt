package presentation.circuit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*


@Composable
fun CircuitXmlSelector(onFilesSelected: (String, String) -> Unit) {
    var circuitFile by remember { mutableStateOf<String?>(null) }
    var objectsFile by remember { mutableStateOf<String?>(null) }

    Column {
        Row {
            XmlSelector(
                extension = "cdedatamodel",
                buttonText = "Select the object model file"
            ) { if (it != null) objectsFile = it }
            XmlSelector(buttonText = "Select the circuit file") { if (it != null) circuitFile = it }
        }
        Button(
            onClick = { onFilesSelected(objectsFile ?: "", circuitFile ?: "") },
            enabled = circuitFile != null && objectsFile != null
        ) {
            Text(text = "Confirm")
        }
    }
}