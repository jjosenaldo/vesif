package core.files

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object FileManager {
    val newLine = System.lineSeparator() ?: "\n"
    val tab = " ".repeat(4)
    val fileSeparator = File.separatorChar

    fun upsertFile(path: String, lines: List<String>) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }

        file.bufferedWriter().use { out -> out.apply { lines.map { line -> write(line);newLine() } } }
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