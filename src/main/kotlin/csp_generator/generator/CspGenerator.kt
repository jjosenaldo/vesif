package csp_generator.generator

import core.files.*
import core.model.Circuit
import core.model.Component
import csp_generator.model.CircuitCspData
import csp_generator.model.CspPaths
import csp_generator.model.PathsCspGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CspGenerator {
    private val defaultCspFilesPath =
        "$projectPath/src/main/kotlin/csp_generator/default_csp_files"
    private val generalOutputPath = "$outputPath${fileSep}general.csp"
    private val defaultGeneralPath = "$defaultCspFilesPath${fileSep}general.csp"
    private val defaultFunctionsPath = "$defaultCspFilesPath${fileSep}functions.csp"
    private val functionsOutputPath = "$outputPath${fileSep}functions.csp"

    // TODO(ft): write files in parallel
    suspend fun generateCircuitCsp(circuit: Circuit): CspPaths {
        val circuitData = CircuitCspData().apply { fillData(circuit.components) }
        generateCircuitCsp(circuitData)
        val paths = generatePathsCsp(circuit.components, circuitData)
        val (minPathIndex, maxPathIndex) = paths.keys.let { Pair(it.min(), it.max()) }
        generateGeneralCsp(minPathIndex, maxPathIndex)
        generateFunctionsPath()
        return paths
    }

    private suspend fun generateCircuitCsp(
        circuitCspData: CircuitCspData
    ) {
        CspWriter.writePaths(circuitCspData, circuitOutputPath)
    }

    private suspend fun generatePathsCsp(
        components: List<Component>,
        circuitCspData: CircuitCspData
    ): CspPaths {
        val paths = PathsCspGenerator.generatePaths(components, circuitCspData.connections)
        CspWriter.writePaths("PATHS", paths, pathsOutputPath)
        return paths
    }

    private suspend fun generateGeneralCsp(
        minPathIndex: Int,
        maxPathIndex: Int
    ) = withContext(Dispatchers.IO) {
        val defaultGeneralCspFile = File(defaultGeneralPath)
        val defaultGeneralContent = defaultGeneralCspFile.readText()
        // TODO(td): move "short_circuit" elsewhere
        val defaultShortCircuitChannelDefinition = "channel short_circuit: Int"
        val newShortCircuitChannelDefinition = "channel short_circuit: {${minPathIndex}..${maxPathIndex}}"
        val newContent =
            defaultGeneralContent.replace(defaultShortCircuitChannelDefinition, newShortCircuitChannelDefinition)

        FileManager.upsertFile(generalOutputPath, text = newContent)
    }

    private suspend fun generateFunctionsPath() =
        withContext(Dispatchers.IO) {
            File(defaultFunctionsPath).copyTo(File(functionsOutputPath), overwrite = true)
        }

}