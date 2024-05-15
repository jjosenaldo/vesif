package org.example.verifier.model

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.AssertionType

abstract class AssertionDefinition {
    abstract val definition: String
    abstract val type: AssertionType
    abstract fun buildRunResult(session: Session, fdrAssertion: Assertion): AssertionRunResult
}