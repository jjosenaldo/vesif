package verifier.model.assertions.contact_status

import core.model.*
import csp_generator.model.CspPaths
import csp_generator.util.endpoint
import csp_generator.util.endpoint1
import csp_generator.util.endpoint2
import ui.screens.main.sub_screens.assertions.model.ContactStatusAssertionData
import uk.ac.ox.cs.fdr.Assertion
import uk.ac.ox.cs.fdr.Session
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class ContactStatusAssertion(data: ContactStatusAssertionData) : AssertionDefinition() {
    override val definition =
        "assert not STOP [T= SYSTEM_CONTACTS_STATUS((| ${
            data.selectedData.joinToString(", ") { pair ->
                getAssertionPairDefinition(pair.first, pair.second)
            }
        } |))"
    override val type = AssertionType.ContactStatus

    override fun buildRunResult(
        session: Session,
        fdrAssertion: Assertion,
        components: List<Component>,
        paths: CspPaths
    ): AssertionRunResult {
        return ContactStatusAssertionRunResult(fdrAssertion)
    }

    private fun getAssertionPairDefinition(contact: Contact, value: Boolean): String {
        return when (contact) {
            is MonostableSimpleContact -> "${contact.endpoint.id} => $value"
            is RelayChangeoverContact ->
                "${if (value) contact.endpoint1.id else contact.endpoint2.id} => true"
            else -> ""
        }
    }
}