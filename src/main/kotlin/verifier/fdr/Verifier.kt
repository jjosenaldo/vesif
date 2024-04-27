package org.example.verifier.fdr

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import uk.ac.ox.cs.fdr.fdr
import java.nio.file.Paths


object Verifier {
    private val frontEndFile = "${Paths.get("").toAbsolutePath()}/output/circuit.csp"

    fun checkAssertions() {
        try {
            val session = Session()
            session.loadFile(frontEndFile)

            for (assertion: Assertion in session.assertions()) {
                assertion.execute(null)
                println("$assertion ${if (assertion.passed()) "Passed" else "Failed"}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fdr.libraryExit()
        }
    }
}