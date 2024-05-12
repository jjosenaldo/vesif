package org.example.verifier.model

import uk.ac.ox.cs.fdr.Assertion

abstract class AssertionDefinition {
    abstract val definition: String
    abstract val type: AssertionType
    abstract fun buildRunResult(fdrAssertion: Assertion): AssertionRunResult
}