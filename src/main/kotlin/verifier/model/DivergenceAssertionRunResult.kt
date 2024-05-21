package verifier.model

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.util.getFirstLoopBehavior
import verifier.util.prettyTracePriorToLoop

class DivergenceAssertionRunResult(private val fdrAssertion: Assertion, private val session: Session) :
    AssertionRunResult(AssertionType.Divergence, fdrAssertion.passed()) {
    override val details = buildDetails()

    private fun buildDetails(): String {
        // TODO: explicit divergence behavior
        val loopBehavior = fdrAssertion.getFirstLoopBehavior() ?: return ""
        return "Trace prior to divergence: ${loopBehavior.prettyTracePriorToLoop(session)}"
    }
}