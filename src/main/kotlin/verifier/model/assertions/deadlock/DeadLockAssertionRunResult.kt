package verifier.model.assertions.deadlock

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType
import verifier.util.getFirstMinAcceptanceBehavior
import verifier.util.prettyTrace

class DeadLockAssertionRunResult(
    val assertion: DeadlockAssertion,
    private val session: Session,
    private val fdrAssertion: Assertion
) :
    AssertionRunResult(AssertionType.Deadlock, fdrAssertion.passed()) {

    private fun buildDetails(): String {
        val trace =
            fdrAssertion.getFirstMinAcceptanceBehavior()?.prettyTrace(session) ?: return ""

        return "Trace leading to deadlock: $trace"
    }
}