package core.utils.files

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Document
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import javax.xml.parsers.DocumentBuilderFactory

object FileManager {
    val newLine = System.lineSeparator() ?: "\n"
    val tab = " ".repeat(4)
    val fileSeparator = File.separatorChar

    fun getResource(path: String): File {
        return File(System.getProperty("compose.application.resources.dir")).resolve(path)
    }

    suspend fun readXml(xmlPath: String): Document {
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

        return withContext(Dispatchers.IO) {
            documentBuilder.parse(FileInputStream(xmlPath))
        }
    }

    fun upsertFile(path: String, lines: List<String>) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }

        file.bufferedWriter().use { out -> out.apply { lines.map { line -> write(line);newLine() } } }
    }

    suspend fun upsertFile(path: String, text: String) = withContext(Dispatchers.IO) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }

        file.writeText(text)
    }

    fun createFileIfNotExists(output: String) {
        val file = File(output)

        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                var lastSeparatorPosition = output.lastIndexOf(fileSeparator)

                if (lastSeparatorPosition == -1) lastSeparatorPosition = output.length
                Files.createDirectories(Paths.get(output.substring(0, lastSeparatorPosition)))
            }
        }
    }

}