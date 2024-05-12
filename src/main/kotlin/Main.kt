package org.example

import org.example.core.model.examples.circuit01
import org.example.csp_generator.generator.CspGenerator
import org.example.verifier.fdr.Verifier
import org.example.verifier.model.AssertionType
import java.nio.file.Paths

fun main() {
    val circuit = circuit01
    CspGenerator.generateCsp(
        Paths.get(
            System.getProperty("user.dir"), "output"
        ).toString(), circuit
    )
    val assertionsToCheck = listOf(AssertionType.ringBell)
    val allFailingAssertions = Verifier.checkFailingAssertions(circuit, assertionsToCheck)

    assertionsToCheck.forEach {
        val failingAssertions = allFailingAssertions[it] ?: listOf()
        println("$it: ${if (failingAssertions.isEmpty()) "✓" else "⚠"}")
        failingAssertions.forEach { result -> println(result.details) }
        println()
    }
}