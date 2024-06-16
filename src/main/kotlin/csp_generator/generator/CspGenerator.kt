package csp_generator.generator

import core.files.*
import core.model.Circuit
import core.model.Component
import csp_generator.model.CircuitCspData
import csp_generator.model.PathsCspGenerator
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class CspGenerator {
    private val cspFilesPath =
        "$projectPath/src/main/kotlin/csp_generator/default_csp_files"

    // TODO(ft): write files in parallel
    suspend fun generateCircuitCsp(circuit: Circuit) {
        val circuitData = CircuitCspData().apply { fillData(circuit.components) }
        generateCircuitCsp(circuitPath, circuitData)
        generatePathsCsp(pathsPath, circuit.components, circuitData)
        copyDefaultCspFiles(outputPath)
    }

    private suspend fun generateCircuitCsp(
        outputPath: String,
        circuitCspData: CircuitCspData
    ) {
        CspWriter.write(circuitCspData, outputPath)
    }

    private suspend fun generatePathsCsp(
        outputPath: String,
        components: List<Component>,
        circuitCspData: CircuitCspData
    ) {
        val paths = PathsCspGenerator.generatePaths(components, circuitCspData.connections)
        CspWriter.write("PATHS", paths, outputPath)
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