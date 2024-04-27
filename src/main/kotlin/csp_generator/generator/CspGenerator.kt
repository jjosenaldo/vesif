package org.example.csp_generator.generator

import org.example.core.model.Component
import org.example.csp_generator.model.FrontEndCspData
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

object CspGenerator {
    private const val FRONT_END_FILE_NAME = "circuit.csp"
    private val cspFilesPath =
        "${Paths.get("").toAbsolutePath()}/src/main/kotlin/csp_generator/default_csp_files"

    fun generateCsp(outputPath: String, components: List<Component>) {
        val pathWithSlash = "$outputPath${if (outputPath.last() != '/') "/" else ""}"

        generateFrontEndCsp(pathWithSlash, components)
        copyDefaultCspFiles(destinationFolderPath = pathWithSlash)
    }

    private fun generateFrontEndCsp(outputPath: String, components: List<Component>) {
        val frontEndCspData = FrontEndCspData().apply { generate(components) }
        FrontEndCspWriter.write(
            frontEndCspData,
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