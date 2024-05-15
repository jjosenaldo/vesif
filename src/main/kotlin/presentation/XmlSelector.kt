package presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.MPFile

@Composable
fun XmlSelector(onFileSelected: (suspend () -> String) -> Unit) {
    var showFilePicker by remember { mutableStateOf(false) }

    Column {
        Button(onClick = {
            showFilePicker = true
        }) {
            Text("Select a XML file")
        }
        LibraryFilePicker(
            show = showFilePicker,
            onShowChanged = { showFilePicker = it },
            onFileSelected = { onFileSelected(suspend { getFileContent(it) }) }
        )
    }
}

@Composable
private fun LibraryFilePicker(show: Boolean, onShowChanged: (Boolean) -> Unit, onFileSelected: (MPFile<Any>?) -> Unit) {
    FilePicker(show = show, fileExtensions = listOf("xml")) { platformFile ->
        onShowChanged(false)
        onFileSelected(platformFile)
    }
}

private suspend fun getFileContent(file: MPFile<Any>?): String {
    if (file == null) return ""
    return file.getFileByteArray().decodeToString()
}