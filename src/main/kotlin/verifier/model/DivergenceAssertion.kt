package verifier.model

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session

class DivergenceAssertion : AssertionDefinition() {
    override val definition = "assert SYSTEM:[divergence free]"
    override val type = AssertionType.Divergence

    override fun buildRunResult(session: Session, fdrAssertion: Assertion): AssertionRunResult {
        return DivergenceAssertionRunResult(fdrAssertion, session)
    }
}