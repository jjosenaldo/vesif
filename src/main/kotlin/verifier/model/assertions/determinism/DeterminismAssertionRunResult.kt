package verifier.model.assertions.determinism

import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Behaviour
import uk.ac.ox.cs.fdr.LoopBehaviour
import uk.ac.ox.cs.fdr.MinAcceptanceBehaviour
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType
import verifier.util.*

class DeterminismAssertionRunResult(val fdrAssertion: Assertion, val session: Session) :
    AssertionRunResult(AssertionType.Determinism, fdrAssertion.passed()) {
    override val details = buildDetails()

    private fun buildDetails(): String {
        val behaviors = fdrAssertion.rootBehaviors()

        if (behaviors.size < 2) return ""

        val firstBehavior = behaviors[0]
        val secondBehavior = behaviors[1]

        // TODO: is there other possibility?
        return handleLoopBehavior(firstBehavior)
            ?: handleLoopBehavior(secondBehavior)
            ?: handleMinAcceptanceBehaviors(firstBehavior, secondBehavior)
            ?: ""
    }

    private fun handleLoopBehavior(behavior: Behaviour): String? {
        if (behavior !is LoopBehaviour) return null

        return "Loops after trace: ${behavior.prettyTracePriorToLoop(session)}"
    }

    private fun handleMinAcceptanceBehaviors(first: Behaviour, second: Behaviour): String? {
        if (first !is MinAcceptanceBehaviour || second !is MinAcceptanceBehaviour) return null
        val trace = first.prettyTrace(session)
        val firstRefusals = first.prettyRefusals(session)
        val secondRefusals = second.prettyRefusals(session)

        return "After the trace $trace, the refusals can be either $firstRefusals or $secondRefusals"
    }
}