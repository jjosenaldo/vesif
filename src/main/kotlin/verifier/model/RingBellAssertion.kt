package org.example.verifier.model

import org.example.core.model.Button
import org.example.core.model.RelayRegularContact
import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.AssertionType

class RingBellAssertion(val contact: RelayRegularContact, val buttonsState: Map<Button, Boolean>) :
    AssertionDefinition() {
    override val definition: String by lazy {
        "assert not MONITORED_SYSTEM_UNSTABLE_CONTACT(${contact.endpointId}, (| ${buttonsState.entries.joinToString(", ") { "${it.key.id} => ${it.value}" }} |)) [T= RUN({switch})"
    }
    override val type: AssertionType = AssertionType.RingBell
    override fun buildRunResult(session: Session, fdrAssertion: Assertion) =
        RingBellAssertionRunResult(this, fdrAssertion.passed())
}