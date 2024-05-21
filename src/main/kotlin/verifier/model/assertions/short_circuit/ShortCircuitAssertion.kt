package verifier.model.assertions.short_circuit

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class ShortCircuitAssertion : AssertionDefinition() {
    override val definition = "assert RUN(ALPHA_PATH_MASTER) [T= SYSTEM"
    override val type = AssertionType.ShortCircuit

    override fun buildRunResult(session: Session, fdrAssertion: Assertion): AssertionRunResult {
        return ShortCircuitAssertionRunResult(session, fdrAssertion)
    }
}