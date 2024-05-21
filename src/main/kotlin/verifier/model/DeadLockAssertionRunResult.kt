package verifier.model

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.util.getFirstMinAcceptanceBehavior
import verifier.util.prettyTrace

class DeadLockAssertionRunResult(
    val assertion: DeadlockAssertion,
    private val session: Session,
    private val fdrAssertion: Assertion
) :
    AssertionRunResult(AssertionType.Deadlock, fdrAssertion.passed()) {
    override val details = buildDetails()

    private fun buildDetails(): String {
        val trace =
            fdrAssertion.getFirstMinAcceptanceBehavior()?.prettyTrace(session) ?: return ""

        return "Trace leading to deadlock: $trace"
    }
}