package org.example.verifier.csp_generator

import org.example.core.model.Component

class CspGenerator(val outputPath: String) {
    private fun generateFrontEndCsp(components: List<Component>) {
        val frontEndCspData = FrontEndCspData().apply { generate(components) }
    }
}