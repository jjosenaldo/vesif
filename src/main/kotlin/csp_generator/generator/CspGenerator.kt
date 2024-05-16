package csp_generator.generator

import core.files.projectPath
import core.files.circuitPath
import core.files.outputPath
import core.model.Circuit
import core.model.Component
import csp_generator.model.CircuitCspData
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class CspGenerator {
    private val cspFilesPath =
        "$projectPath/src/main/kotlin/csp_generator/default_csp_files"

    fun generateCircuitCsp(circuit: Circuit) {
        generateCircuitCsp(circuitPath, circuit.components)
        copyDefaultCspFiles(destinationFolderPath = outputPath)
    }

    private fun generateCircuitCsp(outputPath: String, components: List<Component>) {
        val circuitCspData = CircuitCspData().apply { generate(components) }
        CircuitCspWriter.write(circuitCspData, outputPath)
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