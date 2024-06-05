package verifier.model.common

import core.model.Component
import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session

abstract class AssertionDefinition {
    abstract val definition: String
    abstract val type: AssertionType
    abstract fun buildRunResult(
        session: Session,
        fdrAssertion: Assertion,
        components: List<Component>
    ): AssertionRunResult
}