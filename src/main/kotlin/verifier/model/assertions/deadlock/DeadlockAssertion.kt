package verifier.model.assertions.deadlock

import core.model.Component
import csp_generator.model.CspPaths
import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class DeadlockAssertion : AssertionDefinition() {
    override val definition = "assert SYSTEM:[deadlock free]"
    override val type = AssertionType.Deadlock

    override fun buildRunResult(
        session: Session,
        fdrAssertion: Assertion,
        components: List<Component>,
        paths: CspPaths
    ): AssertionRunResult {
        return DeadLockAssertionRunResult(this, session, fdrAssertion)
    }
}