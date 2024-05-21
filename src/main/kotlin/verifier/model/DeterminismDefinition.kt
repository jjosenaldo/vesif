package verifier.model

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session

class DeterminismDefinition : AssertionDefinition() {
    override val definition = "assert SYSTEM:[deterministic]"
    override val type = AssertionType.Determinism

    override fun buildRunResult(session: Session, fdrAssertion: Assertion): AssertionRunResult {
        return DeterminismAssertionRunResult(session = session, fdrAssertion = fdrAssertion)
    }
}