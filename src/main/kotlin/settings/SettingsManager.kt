package settings

import core.files.fileSep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.util.*

class SettingsManager {
    private val configFile = "config.properties"
    private val fdrPathProperty = "fdr.path"

    // TODO(ft): linux and macOS support
    private val defaultFdrPath = "C:${fileSep}Program Files${fileSep}FDR${fileSep}bin"

    suspend fun foo() {
        val javaLibPath = System.getProperty("java.library.path")
        val envVars = System.getenv()
        println(envVars["Path"])
        println(javaLibPath)
        for (`var` in envVars.keys) {
            System.err.println("examining $`var`")
            if (envVars[`var`] == javaLibPath) {
                println(`var`)
            }
        }
    }

    suspend fun loadFdrLibrary() {
        val dependencyPath = getFdrPath()

        // Load the library dynamically at runtime
//        System.loadLibrary("fdr")
    }

    private suspend fun getFdrPath(): String = withContext(Dispatchers.IO) {
        val properties = Properties()
        val configFile = File(configFile)

        if (configFile.exists()) {
            FileInputStream(configFile).use { properties.load(it) }
        }

        properties.getProperty(fdrPathProperty, defaultFdrPath)
    }
}
