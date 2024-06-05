package verifier.model.assertions.divergence

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType
import verifier.util.getFirstLoopBehavior
import verifier.util.prettyTracePriorToLoop

class DivergenceAssertionRunResult(private val fdrAssertion: Assertion, private val session: Session) :
    AssertionRunResult(AssertionType.Divergence, fdrAssertion.passed()) {

    private fun buildDetails(): String {
        // TODO: explicit divergence behavior
        val loopBehavior = fdrAssertion.getFirstLoopBehavior() ?: return ""
        return "Trace prior to divergence: ${loopBehavior.prettyTracePriorToLoop(session)}"
    }
}