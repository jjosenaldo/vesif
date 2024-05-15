package core.files

import org.example.core.files.FileManager
import java.nio.file.Path
import java.nio.file.Paths

val projectPath: Path = Paths.get("").toAbsolutePath()
val outputPath = "$projectPath/output"
val circuitPath = "$outputPath${FileManager.fileSeparator}circuit.csp"