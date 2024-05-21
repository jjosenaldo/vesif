package verifier.util

import uk.ac.ox.cs.fdr.*

fun Behaviour.getPrettyTrace(session: Session): List<String> {
    return trace()
        .map { if (it == fdr.INVALIDEVENT.toLong()) "-" else session.uncompileEvent(it).toString() }
}

fun Assertion.getFirstTraceBehavior() = getFirstBehavior<TraceBehaviour>()

fun Assertion.getFirstMinAcceptanceBehavior() = getFirstBehavior<MinAcceptanceBehaviour>()

private inline fun <reified T1 : Behaviour> Assertion.getFirstBehavior(): T1? {
    val counterExample = counterexamples().firstOrNull() ?: return null
    val context = when (counterExample) {
        is RefinementCounterexample -> getDebugContextRefinement(counterExample)
        is PropertyCounterexample -> getDebugContextProperty(counterExample)
        else -> null
    } ?: return null

    return context.let {
        it.initialise(null)
        it.rootBehaviours().firstOrNull { behavior -> behavior is T1 } as T1?
    }
}

private fun getDebugContextRefinement(counterExample: RefinementCounterexample) = DebugContext(counterExample, false)
private fun getDebugContextProperty(counterExample: PropertyCounterexample) = DebugContext(counterExample, false)