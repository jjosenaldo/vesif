package verifier

import core.model.Circuit
import core.utils.files.FileManager
import core.utils.files.circuitOutputPath
import core.utils.files.outputPath
import core.utils.preferences.Preferences
import csp_generator.generator.CspGenerator
import kotlinx.coroutines.*
import uk.ac.ox.cs.fdr.CancelledError
import uk.ac.ox.cs.fdr.Canceller
import uk.ac.ox.cs.fdr.fdr
import ui.screens.main.sub_screens.assertions.model.AssertionData
import uk.ac.ox.cs.fdr.Session
import verifier.model.assertions.contact_status.ContactStatusAssertionGenerator
import verifier.model.assertions.deadlock.DeadlockAssertionGenerator
import verifier.model.assertions.determinism.DeterminismAssertionGenerator
import verifier.model.assertions.divergence.DivergenceAssertionGenerator
import verifier.model.assertions.lamp_status.LampStatusAssertionGenerator
import verifier.model.assertions.ringbell.RingBellAssertionGenerator
import verifier.model.assertions.short_circuit.ShortCircuitAssertionGenerator
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType
import verifier.util.AssertionTimeoutException
import verifier.util.FdrLoader
import kotlin.time.Duration.Companion.minutes


class AssertionManager(private val cspGenerator: CspGenerator) {
    private val assertionsFile = "$outputPath${FileManager.fileSeparator}assertions.csp"
    private lateinit var canceller: Canceller

    private val assertionGenerators = mapOf(
        AssertionType.RingBell to RingBellAssertionGenerator(),
        AssertionType.ShortCircuit to ShortCircuitAssertionGenerator(),
        AssertionType.Deadlock to DeadlockAssertionGenerator(),
        AssertionType.Divergence to DivergenceAssertionGenerator(),
        AssertionType.Determinism to DeterminismAssertionGenerator(),
        AssertionType.ContactStatus to ContactStatusAssertionGenerator(),
        AssertionType.LampStatus to LampStatusAssertionGenerator()
    )

    fun getAssertionTypes(): List<AssertionType> {
        return AssertionType.entries.toMutableList().apply {
            remove(AssertionType.Deadlock)
        }
    }

    suspend fun runAssertionsReturnFailing(
        circuit: Circuit,
        assertionData: Map<AssertionType, AssertionData>
    ): Map<AssertionType, List<AssertionRunResult>> {
        return runAssertions(circuit, assertionData).filter { !it.passed }.groupBy { it.assertionType }
    }

    fun cancelRunningAssertions() {
        if (!canceller.cancelled()) {
            canceller.cancel()
        }
    }

    private suspend fun runAssertions(
        circuit: Circuit,
        assertionData: Map<AssertionType, AssertionData>
    ): List<AssertionRunResult> {
        return withContext(Dispatchers.IO) {
            FdrLoader.loadFdr()
            val paths = cspGenerator.generateCircuitCsp(circuit)
            val results = mutableListOf<AssertionRunResult>()
            val assertionDefinitions = buildAssertions(circuit, assertionData)
            canceller = Canceller()

            val cancellingJob = launch {
                delay(Preferences.timeoutTimeMinutes.minutes)
                cancel()
            }

            try {
                Session().apply {
                    loadFile(circuitOutputPath)
                    results.addAll(
                        assertions().zip(assertionDefinitions).map { (fdrAssertion, assertion) ->
                            fdrAssertion.execute(canceller)
                            assertion.buildRunResult(this, fdrAssertion, circuit.components, paths)
                        }
                    )
                }
            } catch (e: CancelledError) {
                fdr.libraryExit()
                throw AssertionTimeoutException()
            } finally {
                cancellingJob.cancel()

                if (FdrLoader.fdrLoaded) {
                    fdr.libraryExit()
                }
            }

            return@withContext results
        }

    }

    private fun buildAssertions(
        circuit: Circuit,
        assertionData: Map<AssertionType, AssertionData>
    ): List<AssertionDefinition> {
        val allAssertions =
            assertionData.entries.mapNotNull { (type, data) ->
                assertionGenerators[type]?.generateAssertions(
                    circuit,
                    data
                )
            }.flatten()

        FileManager.upsertFile(assertionsFile, allAssertions.map { it.definition })

        return allAssertions
    }

//    fun exampleCheckAssertions() {
//        try {
//            val session = Session()
//            session.loadFile(circuitOutputPath)
//
//            for (assertion: Assertion in session.assertions()) {
//                assertion.execute(null)
//                if (assertion.passed()) {
//                    continue
//                }
//
//                println(assertion)
//
//                if (!assertion.passed()) {
//                    println("counterexamples (${assertion.counterexamples().size}):")
//                    assertion.counterexamples().forEach { counter ->
//                        prettyPrintCounterExample(session, counter)
//                    }
//                }
//
//                println("\n\n\n\n\n\n")
//
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            fdr.libraryExit()
//        }
//    }

//    private fun prettyPrintCounterExample(session: Session, counterexample: Counterexample) {
//        if (counterexample is DeadlockCounterexample) {
//            val context = DebugContext(counterexample, false)
//            context.initialise(null)
//            prettyPrintBehavior(session, context, context.rootBehaviours()[0], 2, true)
//            return
//        }
//        if (counterexample is DivergenceCounterexample) {
//            val context = DebugContext(counterexample, false)
//            context.initialise(null)
//            prettyPrintBehavior(session, context, context.rootBehaviours()[0], 2, true)
//            return
//        }
//        if (counterexample is DeterminismCounterexample) {
//            val context = DebugContext(counterexample, false)
//            context.initialise(null)
//            prettyPrintBehavior(session, context, context.rootBehaviours()[0], 2, true)
//            prettyPrintBehavior(session, context, context.rootBehaviours()[1], 2, true)
//            return
//        }
//        if (counterexample !is TraceCounterexample) {
//            println("not a TraceCounterExample...")
//            return
//        }
//
//        println("event: ${session.uncompileEvent(counterexample.errorEvent())}")
//        val context = DebugContext(counterexample, false)
//        context.initialise(null)
//        val behaviors = context.rootBehaviours()
//
//        for (i in 0 until behaviors.size) {
//            val root = behaviors[i]
//            println("behavior number $i")
//            prettyPrintBehavior(session, context, root, 2, true)
//        }
//    }
//
//    private fun prettyPrintBehavior(
//        session: Session,
//        context: DebugContext,
//        behaviour: Behaviour,
//        indent: Int,
//        recurse: Boolean
//    ) {
//        printIndent(indent); print("behaviour type: ")
////        indent += 2;
//        when (behaviour) {
//            is ExplicitDivergenceBehaviour -> println("explicit divergence after trace")
//            is IrrelevantBehaviour -> println("irrelevant")
//            is LoopBehaviour -> {
//                println("loops after index " + behaviour.loopIndex())
//            }
//
//            is MinAcceptanceBehaviour -> {
//                print("minimal acceptance refusing {")
//                for (event in behaviour.minAcceptance())
//                    print(session.uncompileEvent(event).toString() + ", ")
//                println("}")
//            }
//
//            is SegmentedBehaviour -> {
//                println("Segmented behaviour consisting of:")
//                // Describe the sections of this behaviour. Note that it is very
//                // important that false is passed to the the descibe methods below
//                // because segments themselves cannot be divded via the DebugContext.
//                // That is, asking for behaviourChildren for a behaviour of a
//                // SegmentedBehaviour is not allowed.
//                for (child in behaviour.priorSections())
//                    prettyPrintBehavior(session, context, child, indent + 2, false)
//                prettyPrintBehavior(
//                    session, context, behaviour.last(),
//                    indent + 2, false
//                )
//            }
//
//            is TraceBehaviour -> {
//                println(
//                    "performs event " +
//                            session.uncompileEvent(behaviour.errorEvent()).toString()
//                )
//            }
//        }
//
//        // Describe the trace of the behaviour
//        printIndent(indent); print("Trace: ")
//        for (event in behaviour.trace()) {
//            // INVALIDEVENT indiciates that this machine did not perform an event at
//            // the specified index (i.e. it was not synchronised with the machines
//            // that actually did perform the event).
//            if (event == fdr.INVALIDEVENT.toLong())
//                print("-, ")
//            else
//                print(session.uncompileEvent(event).toString() + ", ")
//        }
//        println()
//
//        // Describe any named states of the behaviour
//        printIndent(indent); print("States: ")
//        for (node in behaviour.nodePath()) {
//            if (node == null)
//                print("-, ")
//            else {
//                val processName = session.machineNodeName(behaviour.machine(), node)
//                if (processName == null)
//                    print("(unknown), ")
//                else
//                    print("$processName, ")
//            }
//        }
//        println()
//
//        // Describe our own children recursively
//        if (recurse) {
//            for (child in context.behaviourChildren(behaviour))
//                prettyPrintBehavior(session, context, child, indent + 2, true)
//        }
//    }
//
//    private fun printIndent(indent: Int) {
//        print(" ".repeat(indent))
//    }
}