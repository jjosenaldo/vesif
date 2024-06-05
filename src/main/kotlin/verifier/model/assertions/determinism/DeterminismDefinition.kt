package verifier.model.assertions.determinism

import core.model.Component
import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class DeterminismDefinition : AssertionDefinition() {
    override val definition = "assert SYSTEM:[deterministic]"
    override val type = AssertionType.Determinism

    override fun buildRunResult(
        session: Session, fdrAssertion: Assertion,
        components: List<Component>
    ): AssertionRunResult {
        return DeterminismAssertionRunResult(session = session, fdrAssertion = fdrAssertion)
    }
}