package org.example

import org.example.core.model.examples.example01
import org.example.core.model.examples.exampleWithShortCircuit
import org.example.csp_generator.generator.CspGenerator
import org.example.verifier.fdr.Verifier
import org.example.verifier.model.AssertionType
import java.nio.file.Paths

fun main() {
    val circuit = exampleWithShortCircuit

    CspGenerator.generateCsp(
        Paths.get(
            System.getProperty("user.dir"), "output"
        ).toString(), circuit
    )

    val assertionsToCheck = AssertionType.entries
    val allFailingAssertions = Verifier.checkFailingAssertions(circuit, assertionsToCheck)

    assertionsToCheck.forEach {
        val failingAssertions = allFailingAssertions[it] ?: listOf()
        println("$it: ${if (failingAssertions.isEmpty()) "✓" else "⚠"}")
        failingAssertions.forEach { result -> println(result.details) }
        println()
    }
}