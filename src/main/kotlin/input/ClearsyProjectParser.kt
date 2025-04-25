package input

import core.utils.files.FileManager
import core.utils.files.fileSep
import input.model.InvalidClearsyProjectException
import org.w3c.dom.Document
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.pathString

class ClearsyProjectParser {
    private val projectFileExtension = "cdeproject"

    suspend fun getCircuitsPaths(projectPath: String): List<String> {
        val projectFilePath = getProjectFilePath(projectPath)
        val projectDocument = FileManager.readXml(projectFilePath)
        return getCircuitsPaths(projectDocument)
            .map { "$projectPath$fileSep${it.replace('/', fileSep)}" }
    }

    private fun getProjectFilePath(projectPath: String): String {
        val directoryPath: Path = Paths.get(projectPath)

        if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
            throw InvalidClearsyProjectException()
        }

        val path = Files.list(directoryPath)
            .filter { Files.isRegularFile(it) && it.fileName.toString().contains(".$projectFileExtension") }
            .map { it.pathString }
            .findFirst()

        if (path.isPresent) {
            return path.get()
        } else {
            throw InvalidClearsyProjectException()
        }
    }

    private fun getCircuitsPaths(document: Document): List<String> {
        return document.documentElement.childrenByName("SheetFile").map { it.textContent }
    }
}