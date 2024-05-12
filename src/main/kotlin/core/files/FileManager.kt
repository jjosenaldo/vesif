package org.example.core.files

import java.io.File

object FileManager {
    fun upsertFile(path: String, lines: List<String>) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }

        file.bufferedWriter().use { out -> out.apply { lines.map { line -> write(line);newLine() } } }
    }
}