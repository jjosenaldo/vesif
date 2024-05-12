package org.example.verifier.fdr

import org.example.core.file_manager.outputPath
import org.example.core.files.FileManager
import org.example.core.model.Circuit
import org.example.verifier.model.AssertionDefinition
import org.example.verifier.assertion_generator.AssertionGenerator
import org.example.verifier.model.AssertionRunResult
import org.example.verifier.assertion_generator.RingBellAssertionGenerator
import org.example.verifier.model.AssertionType
import uk.ac.ox.cs.fdr.*


object Verifier {
    private val circuitFile = "$outputPath/circuit.csp"
    private val assertionsFile = "$outputPath/assertions.csp"

    private val assertionGenerators = mapOf<AssertionType, AssertionGenerator>(
        AssertionType.ringBell to RingBellAssertionGenerator()
    )

    fun checkFailingAssertions(circuit: Circuit, assertionTypes: List<AssertionType>): Map<AssertionType, List<AssertionRunResult>> {
        return runAssertions(circuit, assertionTypes).filter { !it.passed }.groupBy { it.assertion.type }
    }

    private fun runAssertions(circuit: Circuit, assertionTypes: List<AssertionType>): List<AssertionRunResult> {
        val results = mutableListOf<AssertionRunResult>()
        val assertionDefinitions = buildAssertions(circuit, assertionTypes)

       try {
           Session().apply {
               loadFile(circuitFile)
               results.addAll(
                   assertions().zip(assertionDefinitions).map { (fdrAssertion, assertion) ->
                       fdrAssertion.execute(null)
                       assertion.buildRunResult(fdrAssertion)
                   }
               )
           }
       } catch(e: Exception) {
           e.printStackTrace()
       } finally {
           fdr.libraryExit()
       }

        return results
    }

    private fun buildAssertions(circuit: Circuit, assertionTypes: List<AssertionType>): List<AssertionDefinition> {
        val allAssertions = assertionTypes.mapNotNull { assertionGenerators[it]?.generateAssertions(circuit) }.flatten()

        FileManager.upsertFile(assertionsFile, allAssertions.map { it.definition })

        return allAssertions
    }

    fun exampleCheckAssertions() {
        try {
            val session = Session()
            session.loadFile(circuitFile)

            for (assertion: Assertion in session.assertions()) {
                assertion.execute(null)
                if (assertion.passed()) {
                    continue
                }

                println(assertion)

                if (!assertion.passed()) {
                    println("counterexamples (${assertion.counterexamples().size}):")
                    assertion.counterexamples().forEach { counter ->
                       prettyPrintCounterExample(session, counter)
                    }
                }

                println("\n\n\n\n\n\n")

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fdr.libraryExit()
        }
    }

    private fun prettyPrintCounterExample(session: Session, counterexample: Counterexample) {
        if (counterexample !is TraceCounterexample) {
            println("not a TraceCounterExample...")
            return
        }

        println("event: ${ session.uncompileEvent(counterexample.errorEvent()) }")
        val context = DebugContext(counterexample, false)
        context.initialise(null)
        val behaviors = context.rootBehaviours()

        for (i in 0 until behaviors.size) {
            val root = behaviors[i]
            println("behavior number $i")
            prettyPrintBehavior(session, context, root, 2, true)
        }
    }

    private fun prettyPrintBehavior(session: Session, context: DebugContext, behaviour: Behaviour, indent: Int, recurse: Boolean) {
        printIndent(indent); print("behaviour type: ");
//        indent += 2;
        if (behaviour is ExplicitDivergenceBehaviour)
            println("explicit divergence after trace");
        else if (behaviour is IrrelevantBehaviour)
            println("irrelevant");
        else if (behaviour is LoopBehaviour)
        {
            println("loops after index " + behaviour.loopIndex());
        }
        else if (behaviour is MinAcceptanceBehaviour)
        {
            print("minimal acceptance refusing {");
            for (event in behaviour.minAcceptance())
            print(session.uncompileEvent(event).toString() + ", ");
            println("}");
        }
        else if (behaviour is SegmentedBehaviour)
        {
            println("Segmented behaviour consisting of:");
            // Describe the sections of this behaviour. Note that it is very
            // important that false is passed to the the descibe methods below
            // because segments themselves cannot be divded via the DebugContext.
            // That is, asking for behaviourChildren for a behaviour of a
            // SegmentedBehaviour is not allowed.
            for (child in behaviour.priorSections())
                prettyPrintBehavior(session, context, child, indent + 2, false);
            prettyPrintBehavior(session, context, behaviour.last(),
                indent + 2, false);
        }
        else if (behaviour is TraceBehaviour)
        {
            println("performs event " +
                    session.uncompileEvent(behaviour.errorEvent()).toString());
        }

        // Describe the trace of the behaviour
        printIndent(indent); print("Trace: ");
        for (event in behaviour.trace())
        {
            // INVALIDEVENT indiciates that this machine did not perform an event at
            // the specified index (i.e. it was not synchronised with the machines
            // that actually did perform the event).
            if (event == fdr.INVALIDEVENT.toLong())
                print("-, ");
            else
                print(session.uncompileEvent(event).toString() + ", ");
        }
        println();

        // Describe any named states of the behaviour
        printIndent(indent); print("States: ");
        for (node in behaviour.nodePath())
        {
            if (node == null)
                print("-, ");
            else
            {
                val processName = session.machineNodeName(behaviour.machine(), node);
                if (processName == null)
                    print("(unknown), ");
                else
                    print("$processName, ");
            }
        }
        println();

        // Describe our own children recursively
        if (recurse) {
            for (child in context.behaviourChildren(behaviour))
            prettyPrintBehavior(session, context, child, indent + 2, true);
        }
    }

    private fun printIndent(indent: Int) {
        print(" ".repeat(indent))
    }
}