package verifier.model.assertions.divergence

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class DivergenceAssertion : AssertionDefinition() {
    override val definition = "assert SYSTEM:[divergence free]"
    override val type = AssertionType.Divergence

    override fun buildRunResult(session: Session, fdrAssertion: Assertion): AssertionRunResult {
        return DivergenceAssertionRunResult(fdrAssertion, session)
    }
}