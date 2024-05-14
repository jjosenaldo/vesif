package org.example.verifier.model

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session

class ShortCircuitAssertion: AssertionDefinition() {
    override val definition = "assert RUN(ALPHA_PATH_MASTER) [T= SYSTEM"
    override val type = AssertionType.ShortCircuit

    override fun buildRunResult(session: Session, fdrAssertion: Assertion): AssertionRunResult {
        return ShortCircuitAssertionRunResult(this, session, fdrAssertion)
    }
}