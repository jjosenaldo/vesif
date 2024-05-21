package verifier.model

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session

class DeadlockAssertion : AssertionDefinition() {
    override val definition = "assert SYSTEM:[deadlock free]"
    override val type = AssertionType.Deadlock

    override fun buildRunResult(session: Session, fdrAssertion: Assertion): AssertionRunResult {
        return DeadLockAssertionRunResult(this, session, fdrAssertion)
    }
}