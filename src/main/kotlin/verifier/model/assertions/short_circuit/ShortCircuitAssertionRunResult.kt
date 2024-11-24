package verifier.model.assertions.short_circuit

import core.model.Component
import csp_generator.model.CspPaths
import uk.ac.ox.cs.fdr.*
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType
import verifier.util.errorEvent
import verifier.util.getFirstTraceBehavior
import verifier.util.trace

// TODO(ft): group multiple results into one
class ShortCircuitAssertionRunResult(
    private val session: Session,
    private val fdrAssertion: Assertion,
    private val components: List<Component>,
    private val paths: CspPaths
) :
    AssertionRunResult(AssertionType.ShortCircuit, fdrAssertion.passed()) {
    val shortCircuit by lazy {
        val shortCircuitEvent =
            shortCircuitTrace.lastOrNull() ?: return@lazy listOf()
        val shortCircuitComponents = shortCircuitEvent.split(".")
        // TODO(td): move short_circuit and friends to a dedicated file
        if (shortCircuitComponents.size != 2 || shortCircuitComponents.first() != "short_circuit")
            return@lazy listOf()
        val pathIndex = shortCircuitComponents[1].toIntOrNull() ?: return@lazy listOf()

        (paths[pathIndex] ?: listOf()).mapNotNull { id -> components.firstOrNull { it.id == id } }
    }

    // TODO(ft): consider levers
    val inputs by lazy {
        shortCircuitTrace
            .filter { it.contains("press") }
            .mapNotNull {
                val split = it.split(".")
                if (split.size < 3) return@mapNotNull null

                val id = split[1]
                val state = split[2]

                if (state != "true") return@mapNotNull null
                components.firstOrNull { comp -> comp.id == id }
            }
    }
    private val shortCircuitTrace by lazy {
        val behavior = fdrAssertion.getFirstTraceBehavior() ?: return@lazy listOf()
        behavior.trace(session) + behavior.errorEvent(session)
    }

    override fun hasDetails() = true
}