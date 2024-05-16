package presentation.circuit

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.darkrockstudios.libraries.mpfilepicker.FilePicker

@Composable
fun XmlSelector(extension: String = "xml", buttonText: String, onFileSelected: (String?) -> Unit) {
    var showFilePicker by remember { mutableStateOf(false) }
    var filePicked by remember { mutableStateOf<String?>("") }

    Column {
        if (filePicked?.isNotEmpty() == true)
            Text(text = filePicked!!)

        Button(onClick = {
            showFilePicker = true
        }) {
            Text(buttonText)
        }
        LibraryFilePicker(
            show = showFilePicker,
            onShowChanged = { showFilePicker = it },
            onFileSelected = { filePicked = it; onFileSelected(it) },
            extension = extension
        )
    }
}

@Composable
private fun LibraryFilePicker(
    extension: String,
    show: Boolean,
    onShowChanged: (Boolean) -> Unit,
    onFileSelected: (String?) -> Unit
) {
    FilePicker(show = show, fileExtensions = listOf(extension)) { platformFile ->
        onShowChanged(false)
        onFileSelected(platformFile?.path)
    }
}
