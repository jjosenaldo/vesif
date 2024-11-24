package verifier.model.assertions.contact_status

import core.model.Circuit
import verifier.model.assertions.AssertionData
import verifier.model.common.AssertionDefinition
import verifier.model.common.AssertionGenerator

class ContactStatusAssertionGenerator : AssertionGenerator {
    override fun generateAssertions(circuit: Circuit, data: AssertionData): List<AssertionDefinition> {
        return if (data is ContactStatusAssertionData) listOf(ContactStatusAssertion(data)) else listOf()
    }
}