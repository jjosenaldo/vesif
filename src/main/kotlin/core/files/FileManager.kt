package org.example.core.files

import java.io.File

object FileManager {
    val newLine = System.lineSeparator()
    val tab = " ".repeat(4)

    fun upsertFile(path: String, lines: List<String>) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }

        file.bufferedWriter().use { out -> out.apply { lines.map { line -> write(line);newLine() } } }
    }
}