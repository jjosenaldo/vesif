package org.example.csp_generator.generator

import org.example.core.file_manager.projectPath
import org.example.core.model.Circuit
import org.example.core.model.Component
import org.example.csp_generator.model.CircuitCspData
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

object CspGenerator {
    private const val FRONT_END_FILE_NAME = "circuit.csp"
    private val cspFilesPath =
        "$projectPath/src/main/kotlin/csp_generator/default_csp_files"

    fun generateCsp(outputPath: String, circuit: Circuit) {
        val pathWithSlash = "$outputPath${if (outputPath.last() != '/') "/" else ""}"

        generateCircuitCsp(pathWithSlash, circuit.components)
        copyDefaultCspFiles(destinationFolderPath = pathWithSlash)
    }

    private fun generateCircuitCsp(outputPath: String, components: List<Component>) {
        val circuitCspData = CircuitCspData().apply { generate(components) }
        CircuitCspWriter.write(
            circuitCspData,
            "$outputPath$FRONT_END_FILE_NAME"
        )
    }

    private fun copyDefaultCspFiles(destinationFolderPath: String) {
        val sourceFolder = Paths.get(cspFilesPath)
        val destinationFolder = Paths.get(destinationFolderPath)

        try {
            Files.newDirectoryStream(sourceFolder).use { directoryStream ->
                directoryStream.forEach { file ->
                    val destinationFile = destinationFolder.resolve(file.fileName)
                    Files.copy(file, destinationFile, StandardCopyOption.REPLACE_EXISTING)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}