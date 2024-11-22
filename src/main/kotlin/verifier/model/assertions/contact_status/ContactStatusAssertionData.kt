package verifier.model.assertions.contact_status

import core.model.MonostableSimpleContact
import verifier.model.assertions.MultiselectAssertionData

class ContactStatusAssertionData(contacts: List<MonostableSimpleContact>) : MultiselectAssertionData<Boolean>(
    defaultValue = true,
    keys = contacts.map { it.name }
) {
    override fun getValueText(value: Boolean): String {
        return if (value) "Closed" else "Open"
    }
}