package org.example.core.files

import java.nio.file.Path
import java.nio.file.Paths

val projectPath: Path = Paths.get("").toAbsolutePath()
val outputPath = "$projectPath/output"