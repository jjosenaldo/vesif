package verifier.model

import core.model.Component
import uk.ac.ox.cs.fdr.*

class ShortCircuitAssertionRunResult(assertion: ShortCircuitAssertion, session: Session, fdrAssertion: Assertion) :
    AssertionRunResult(assertion, fdrAssertion.passed()) {
    override val details = buildDetails(session, fdrAssertion)

    private fun buildDetails(session: Session, fdrAssertion: Assertion): String {
        val behavior = getBehavior(fdrAssertion) ?: return ""
        val poles = getPolesFromBehavior(session, behavior)
        if (poles.size != 2) return ""
        val trace = getTraceFromBehavior(session, behavior)
        return buildDetailsFromPolesAndTrace(poles, trace)
    }

    private fun buildDetailsFromPolesAndTrace(poles: List<String>, trace: List<String>): String {
        return "Short circuit found from ${poles[0]} to ${poles[1]} with trace: ${trace.joinToString(", ")}"
    }

    private fun getBehavior(fdrAssertion: Assertion): TraceBehaviour? {
        val counterExample = fdrAssertion.counterexamples().firstOrNull() ?: return null
        if (counterExample !is TraceCounterexample) return null

        return DebugContext(counterExample, false).let {
            it.initialise(null)
            it.rootBehaviours().firstOrNull { behavior -> behavior is TraceBehaviour } as TraceBehaviour?
        }
    }

    private fun getPolesFromBehavior(session: Session, behavior: TraceBehaviour): List<String> {
        val event = session.uncompileEvent(behavior.errorEvent())
        return getPolesFromEvent(event)
    }

    private fun getPolesFromEvent(event: Event): List<String> {
        val components = event.toString().split(".")

        if (components[0] != "short_circuit" || components.size != 3) return listOf()

        return listOf(components[1], components[2]).map(Component::getNameFromId)
    }

    private fun getTraceFromBehavior(session: Session, traceBehavior: TraceBehaviour): List<String> {
        return traceBehavior.trace()
            .map { if (it == fdr.INVALIDEVENT.toLong()) "-" else session.uncompileEvent(it).toString() }
    }
}