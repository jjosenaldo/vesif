package core.utils.files

import java.nio.file.Path
import java.nio.file.Paths

val fileSep = FileManager.fileSeparator
val projectPath: Path = Paths.get("").toAbsolutePath()
val outputPath = "$projectPath/output"
val circuitOutputPath = "$outputPath${fileSep}circuit.csp"
val pathsOutputPath = "$outputPath${fileSep}paths.csp"