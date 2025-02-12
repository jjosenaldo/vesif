package verifier.util

import uk.ac.ox.cs.fdr.*

fun Behaviour.trace(session: Session): List<String> {
    return trace()
        .map { if (it == fdr.INVALIDEVENT.toLong()) "-" else session.uncompileEvent(it).toString() }
}

fun Behaviour.prettyTrace(session: Session): String {
    return prettifyTrace(trace(session))
}

fun LoopBehaviour.prettyTracePriorToLoop(session: Session): String {
    val loopIndex = loopIndex()

    return prettifyTrace(trace(session).subList(0, loopIndex.toInt()))
}

private fun prettifyTrace(trace: List<String>): String {
    return "<${trace.joinToString(", ")}>"
}

fun MinAcceptanceBehaviour.prettyRefusals(session: Session): String {
    return "{${refusals(session).joinToString(", ")}}"
}

fun MinAcceptanceBehaviour.refusals(session: Session): List<String> {
    return minAcceptance().map { session.uncompileEvent(it).toString() }
}

fun Assertion.rootBehaviors(): List<Behaviour> {
    val counterExample = counterexamples().firstOrNull() ?: return listOf()
    val context = when (counterExample) {
        is RefinementCounterexample -> getDebugContextRefinement(counterExample)
        is PropertyCounterexample -> getDebugContextProperty(counterExample)
        else -> null
    } ?: return listOf()

    return context.let {
        it.initialise(null)
        it.rootBehaviours()
    }
}

fun Assertion.getFirstTraceBehavior() = getFirstBehavior<TraceBehaviour>()
fun Assertion.getFirstLoopBehavior() = getFirstBehavior<LoopBehaviour>()
fun Assertion.getFirstMinAcceptanceBehavior() = getFirstBehavior<MinAcceptanceBehaviour>()

fun TraceBehaviour.errorEvent(session: Session): String {
    return session.uncompileEvent(errorEvent()).toString()
}

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