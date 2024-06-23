package core.files

import java.io.File
import java.io.FileInputStream
import java.util.*

class Settings {
    fun writeFdrPath(path: String) {
        FileManager.getResourceProperties("config.properties")["fdr.path"] = path
    }
}