package org.example

import org.example.core.model.Component
import org.example.core.model.examples.componentsExample01
import org.example.verifier.csp_generator.generator.CspGenerator
import java.nio.file.Paths

private fun buildComponents(): List<Component> {
    return componentsExample01
}


fun main() {
    val components = buildComponents()
    CspGenerator.generateCsp(
        Paths.get(
            System.getProperty("user.dir"), "output"
        ).toString(), components
    )
}