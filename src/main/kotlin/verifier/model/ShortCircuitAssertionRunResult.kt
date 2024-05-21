package verifier.model

import core.model.Component
import uk.ac.ox.cs.fdr.*
import verifier.util.getFirstTraceBehavior
import verifier.util.getPrettyTrace

class ShortCircuitAssertionRunResult(private val session: Session, private val fdrAssertion: Assertion) :
    AssertionRunResult(AssertionType.ShortCircuit, fdrAssertion.passed()) {
    override val details = buildDetails()

    private fun buildDetails(): String {
        val behavior = fdrAssertion.getFirstTraceBehavior() ?: return ""
        val poles = getPolesFromBehavior(session, behavior)
        if (poles.size != 2) return ""
        val trace = behavior.getPrettyTrace(session)
        return buildDetailsFromPolesAndTrace(poles, trace)
    }

    private fun buildDetailsFromPolesAndTrace(poles: List<String>, trace: List<String>): String {
        return "Short circuit found from ${poles[0]} to ${poles[1]} with trace: ${trace.joinToString(", ")}"
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
}