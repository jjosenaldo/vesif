package ui.common

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker

@Composable
fun FolderPicker(
    show: Boolean,
    onShowChanged: (Boolean) -> Unit,
    onFolderSelected: (String?) -> Unit
) {
    DirectoryPicker(show) { path ->
        onShowChanged(false)
        onFolderSelected(path)
    }
}