package verifier.model.assertions.ringbell

import core.model.*
import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionType

class RingBellAssertion(val contact: Contact, endpoint: Component, val buttonsState: Map<Button, Boolean>) :
    AssertionDefinition() {
    override val definition =
        "assert not $mainProcessName(${endpoint.id}, (| ${buttonsState.entries.joinToString(", ") { "${it.key.id} => ${it.value}" }} |)) [T= RUN({switch})"
    override val type: AssertionType = AssertionType.RingBell
    override fun buildRunResult(session: Session, fdrAssertion: Assertion) =
        RingBellAssertionRunResult(this, fdrAssertion.passed())

    private val mainProcessName
        get() = when (contact.controller) {
            is MonostableRelay -> "MONITORED_SYSTEM_UNSTABLE_CONTACT"
            is BistableRelay -> "MONITORED_SYSTEM_UNSTABLE_BS_CONTACT"
            else -> ""
        }
}