package core.files

import java.nio.file.Path
import java.nio.file.Paths

val fileSep = FileManager.fileSeparator
val projectPath: Path = Paths.get("").toAbsolutePath()
val outputPath = "$projectPath/output"
val circuitPath = "$outputPath${FileManager.fileSeparator}circuit.csp"
val pathsPath = "$outputPath${FileManager.fileSeparator}paths.csp"