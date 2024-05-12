package org.example.csp_generator.model

import org.example.core.model.*
import org.example.core.model.visitor.ComponentVisitor

// TODO: do buffered writers need to be closed?
// TODO: clear data after generating a file
class CircuitCspData : ComponentVisitor {
    val ids = mutableSetOf<String>()
    val positiveIds = mutableSetOf<String>()
    val negativeIds = mutableSetOf<String>()
    val relayIds = mutableSetOf<String>()
    val contactClosedIds = mutableSetOf<String>()
    val contactOpenedIds = mutableSetOf<String>()
    val twoWayContactIds = mutableSetOf<String>()
    val endpointUpIds = mutableSetOf<String>()
    val endpointDownIds = mutableSetOf<String>()
    val endpointIds = mutableSetOf<String>()
    val junctionIds = mutableSetOf<String>()
    val realButtonIds = mutableSetOf<String>()
    val stubButtonIds = mutableSetOf<String>()
    val leverIds = mutableSetOf<String>()
    val leverContactIds = mutableSetOf<String>()
    val capacitorIds = mutableSetOf<String>()
    val capacitorPlatesIds = mutableSetOf<String>()
    val resistorIds = mutableSetOf<String>()
    val bistableRelayIds = mutableSetOf<String>()
    val bistableRelayCoilLeftIds = mutableSetOf<String>()
    val bistableRelayCoilRightIds = mutableSetOf<String>()
    val bistableRelayContactLeftIds = mutableSetOf<String>()
    val bistableRelayContactRightIds = mutableSetOf<String>()
    val bistableRelayTwoWayContactIds = mutableSetOf<String>()
    val endpointLeftIds = mutableSetOf<String>()
    val endpointRightIds = mutableSetOf<String>()
    val bistableRelayEndpointIds = mutableSetOf<String>()
    val lampIds = mutableSetOf<String>()
    val deactBlockIds = mutableSetOf<String>()
    val deactBlockIndependentIds = mutableSetOf<String>()
    val deactBlockDependentIds = mutableSetOf<String>()
    val timeDeactSettings = mutableSetOf<CspPair<String, Int>>()
    lateinit var initialMaxDeactTime: CspPair<String, Int>
    val maxDeactTime = 0
    val actBlockIds = mutableSetOf<String>()
    val actBlockIndependentIds = mutableSetOf<String>()
    val actBlockDependentIds = mutableSetOf<String>()
    val timeActSettings = mutableSetOf<CspPair<String, Int>>()
    lateinit var initialMaxActTime: CspPair<String, Int>
    val maxActTime = 0
    val initialOpenComponentIds = mutableSetOf<String>()
    val relayOfs = mutableSetOf<CspPair<String, String>>()
    val getContactOfEndpoints = mutableSetOf<CspPair<String, String>>()
    val getEndpointUpOf = mutableSetOf<CspPair<String, Set<String>>>()
    val getEndpointDownOf = mutableSetOf<CspPair<String, Set<String>>>()
    val openLeverContactsOf = mutableSetOf<CspPair<CspPair<String, String>, Set<String>>>()
    val capPoles = mutableSetOf<CspPair<String, Set<String>>>()
    val capLeftPoles = mutableSetOf<CspPair<String, String>>()
    val capRightPoles = mutableSetOf<CspPair<String, String>>()
    val initialCharges = mutableSetOf<CspPair<String, Int>>()
    lateinit var initialMaxCharge: CspPair<String, Int>
    lateinit var maxCharge: CspPair<String, String>
    val getBsContactOf = mutableSetOf<CspPair<String, String>>()
    val getCoilFromBsEndpointL = mutableSetOf<CspPair<String, String>>()
    val getCoilFromBsEndpointR = mutableSetOf<CspPair<String, String>>()
    val getBsEndpointLeftOf = mutableSetOf<CspPair<String, Set<String>>>()
    val getBsEndpointRightOf = mutableSetOf<CspPair<String, Set<String>>>()
    val getIndependentConnectionOfDeact = mutableSetOf<CspPair<String, String>>()
    val getPositiveOfDeactBlock = mutableSetOf<CspPair<String, Set<String>>>()
    val getNegativeOfDeactBlock = mutableSetOf<CspPair<String, Set<String>>>()
    val getIndependentConnectionOfAct = mutableSetOf<CspPair<String, String>>()
    val getPositiveOfActBlock = mutableSetOf<CspPair<String, Set<String>>>()
    val getNegativeOfActBlock = mutableSetOf<CspPair<String, Set<String>>>()
    val connections = mutableSetOf<CspPair<String, String>>()

    fun generate(components: List<Component>) {
        visitComponents(components)
        generateAdditionalIds(components)
        addDefaultIds(components)
    }

    private fun visitComponents(components: List<Component>) {
        for (component in components) {
            component.acceptVisitor(this)
        }
    }

    private fun addDefaultIds(components: List<Component>) {
        addPoleDefaultIds()
        addRelayDefaultIds()
        addJunctionDefaultIds()
        addMonostableRelayDefaultIds(components)
        addLeverDefaultIds()
        addButtonDefaultIds()
        addCapacitorDefaultIds(components)
        addResistorDefaultIds()
        addLampDefaultIds()
        addBistableRelayDefaultIds(components)
        addBlockDefaultIds(components)
    }

    private fun addPoleDefaultIds() {
        ids.add("P_default")
        ids.add("N_default")
    }

    private fun addRelayDefaultIds() {
        ids.add("R_default")
        relayIds.add("R_default")
    }

    private fun addJunctionDefaultIds() {
        ids.add("J_default")
        junctionIds.add("J_default")
    }

    private fun addMonostableRelayDefaultIds(components: List<Component>) {
        if (components.all { it !is MonostableRelay }) {
            val id = "C_default"
            ids.add(id)
            contactClosedIds.add(id)
            twoWayContactIds.add(id)
            getContactOfEndpoints.add(CspPair("id", id))
        }

        if (components.all { it !is RelayChangeOverContact || it.controller !is MonostableRelay }) {
            getEndpointUpOf.add(CspPair("id", setOf()))
            getEndpointDownOf.add(CspPair("id", setOf()))
        }
    }

    private fun addLeverDefaultIds() {
        val contactId = "LC_default"
        val leverId = "L_default"
        ids.add(contactId)
        ids.add(leverId)
        leverContactIds.add(contactId)
        leverIds.add(leverId)
        openLeverContactsOf.add(CspPair(CspPair("id", "side"), setOf()))
    }

    private fun addButtonDefaultIds() {
        val id = "B_default"
        ids.add(id)
        realButtonIds.add(id)
    }

    private fun addCapacitorDefaultIds(components: List<Component>) {
        val defaultCapacitorId = "CP_default"
        val defaultPlate1 = "PL_default"
        val defaultPlate2 = "PL2_default"

        if (components.all { it !is Capacitor }) {
            ids.run {
                add(defaultCapacitorId)
                add(defaultPlate1)
                add(defaultPlate2)
            }
            capacitorIds.add(defaultCapacitorId)
            capacitorPlatesIds.run {
                add(defaultPlate1)
                add(defaultPlate2)
            }
            capPoles.add(CspPair(defaultCapacitorId, setOf(defaultPlate1, defaultPlate2)))
            capLeftPoles.add(CspPair(defaultCapacitorId, defaultPlate1))
            capRightPoles.add(CspPair(defaultCapacitorId, defaultPlate2))
            initialCharges.add(CspPair(defaultCapacitorId, 0))
            initialMaxCharge = CspPair(defaultCapacitorId, 0)
            maxCharge = CspPair("INITIAL_MAX_CHARGE", defaultCapacitorId)
            return
        } else {
            capacitorIds.add(defaultCapacitorId)
            ids.add(defaultCapacitorId)
        }
    }

    private fun addResistorDefaultIds() {
        val id = "RES_default"
        ids.add(id)
        resistorIds.add(id)
    }

    private fun addLampDefaultIds() {
        val id = "LAMP_default"
        ids.add(id)
        lampIds.add(id)
    }

    private fun addBistableRelayDefaultIds(components: List<Component>) {
        ids.add("BS_C_LEFT_default")
        ids.add("BS_C_RIGHT_default")
        ids.add("BS_R_default")
        ids.add("BS_L_COIL_default")
        ids.add("BS_R_COIL_default")
        ids.add("C_ENDPOINT_default")
        bistableRelayIds.add("BS_R_default")
        bistableRelayCoilLeftIds.add("BS_L_COIL_default")
        bistableRelayCoilRightIds.add("BS_R_COIL_default")

        val relayContacts = components.filterIsInstance<RelayContact>()
        val bistableRelayContacts = relayContacts.filter { it.controller is BistableRelayPlate }
        val monostableRelayContacts = relayContacts.filter { it.controller is MonostableRelay }

        // BS_ENDPOINT only filled with regular contacts from bistable relays
        if (relayContacts.all { it.controller is MonostableRelay || it.controller.contacts.none { it2 -> it2 is RelayRegularContact } }) {
            bistableRelayEndpointIds.add("C_ENDPOINT_default")
        }

        // ENDPOINT only filled with regular contacts from monostable relays
        if (relayContacts.all { it.controller is BistableRelayPlate || it.controller.contacts.none { it2 -> it2 is RelayRegularContact } }) {
            endpointIds.add("C_ENDPOINT_default")
        }

        if (bistableRelayContacts.isEmpty() && monostableRelayContacts.isNotEmpty()) {
            getBsContactOf.add(CspPair("C_ENDPOINT_default", "C_default"))
        }

        if (bistableRelayContacts.none { it is RelayChangeOverContact }) {
            getBsEndpointLeftOf.add(CspPair("BS_C_default", setOf("BS_C_LEFT_default")))
            getBsEndpointRightOf.add(CspPair("BS_C_default", setOf("BS_C_RIGHT_default")))
        }

        if (bistableRelayContacts.none { it is RelayRegularContact }) {
            bistableRelayContactLeftIds.add("BS_C_LEFT_default")
        } else {
            getBsEndpointLeftOf.add(CspPair("BS_C_default", setOf("BS_C_LEFT_default")))
            getBsEndpointRightOf.add(CspPair("BS_C_default", setOf("BS_C_RIGHT_default")))
        }

        if (bistableRelayContacts.isEmpty()) {
            getCoilFromBsEndpointL.add(CspPair("C_ENDPOINT_default", "BS_L_COIL_default"))
            getCoilFromBsEndpointR.add(CspPair("C_ENDPOINT_default", "BS_R_COIL_default"))
            bistableRelayContactRightIds.add("C_default")
            ids.add("C_default")
        }
    }

    private fun addBlockDefaultIds(components: List<Component>) {
        ids.add("BL_default")
        ids.add("BL_INDEPENDENT_CONNECTION_default")
        ids.add("BL_DEPENDENT_ENDPOINT_POS_default")
        ids.add("BL_DEPENDENT_ENDPOINT_NEG_default")

        val actBlocks = components.filterIsInstance<ActivationBlock>()
        val deactBlocks = components.filterIsInstance<DeactivationBlock>()

        if (actBlocks.isEmpty() && deactBlocks.isEmpty()) {
            ids.add("BL_DEPENDENT_CONNECTION_default")
            actBlockIndependentIds.add("BL_INDEPENDENT_CONNECTION_default")
            actBlockDependentIds.add("BL_DEPENDENT_ENDPOINT_POS_default")
            actBlockDependentIds.add("BL_DEPENDENT_ENDPOINT_NEG_default")
            deactBlockIndependentIds.add("BL_INDEPENDENT_CONNECTION_default")
            deactBlockDependentIds.add("BL_DEPENDENT_ENDPOINT_POS_default")
            deactBlockDependentIds.add("BL_DEPENDENT_ENDPOINT_NEG_default")
        }

        if (actBlocks.isEmpty()) {
            actBlockIds.add("BL_default")
            timeActSettings.add(CspPair("BL_default", 0))
            initialMaxActTime = CspPair("BL_default", 0)
            getIndependentConnectionOfAct.add(CspPair("BL_default", "BL_INDEPENDENT_CONNECTION_default"))
            getPositiveOfActBlock.add(CspPair("BL_default", emptySet()))
            getNegativeOfActBlock.add(CspPair("BL_default", emptySet()))
        }

        if (deactBlocks.isEmpty()) {
            deactBlockIds.add("BL_default")
            timeDeactSettings.add(CspPair("BL_default", 0))
            initialMaxDeactTime = CspPair("BL_default", 0)
            getIndependentConnectionOfDeact.add(CspPair("BL_default", "BL_INDEPENDENT_CONNECTION_default"))
            getPositiveOfDeactBlock.add(CspPair("BL_default", emptySet()))
            getNegativeOfDeactBlock.add(CspPair("BL_default", emptySet()))
        }
    }

    /**
     * Inserts ids of elements specific to the CSP translation, such as endpoint ids,
     * PATH_MASTER_id etc
     */
    private fun generateAdditionalIds(components: List<Component>) {
        ids.add("PATH_MASTER_id")
        insertRegularContactEndpoints(components.filterIsInstance<RelayRegularContact>())
    }

    private fun addComponentId(component: Component) {
        ids.add(component.id)
    }

    private fun addConnection(leftComponent: Component, rightComponent: Component, forceAdd: Boolean = false) {
        if (!forceAdd && leftComponent is RelayRegularContact) {
            return
        }
        addComponentPair(leftComponent, rightComponent, connections)
    }

    private fun addComponentPair(
        leftComponent: Component,
        rightComponent: Component,
        target: MutableSet<CspPair<String, String>>
    ) {
        target.add(CspPair(leftComponent.id, rightComponent.id))
    }

    override fun visitButton(button: Button) {
        addComponentId(button)
        stubButtonIds.add(button.id)
        initialOpenComponentIds.add(button.id)
        addConnection(button.leftNeighbor, button)
        addConnection(button, button.rightNeighbor)
    }

    override fun visitPole(pole: Pole) {
        addComponentId(pole)
        val listToAdd = if (pole.isPositive) positiveIds else negativeIds
        listToAdd.add(pole.id)

        if (pole.isLeft) addConnection(pole, pole.neighbor)
        else addConnection(pole.neighbor, pole)
    }

    override fun visitMonostableRelay(monostableRelay: MonostableRelay) {
        addComponentId(monostableRelay)
        relayIds.add(monostableRelay.id)
        addConnection(monostableRelay.leftNeighbor, monostableRelay)
        addConnection(monostableRelay, monostableRelay.rightNeighbor)
    }

    override fun visitRelayRegularContact(contact: RelayRegularContact) {
        addComponentId(contact)
        val openOrClosedListToAdd = if (contact.isNormallyOpen) contactOpenedIds else contactClosedIds
        openOrClosedListToAdd.add(contact.id)
        addConnection(contact.leftNeighbor, contact)
    }

    override fun visitCapacitor(capacitor: Capacitor) {
        TODO("Not yet implemented")
    }

    /**
     * Convention: the endpoint is always the contact's right neighbor
     */
    private fun insertRegularContactEndpoints(contacts: List<RelayRegularContact>) {
        for (contact in contacts) {
            val endpoint = object : Component(name = contact.endpointName) {
                override fun acceptVisitor(visitor: ComponentVisitor) {}
            }
            addConnection(contact, endpoint, forceAdd = true)
            addConnection(endpoint, contact.rightNeighbor)
            addComponentPair(endpoint, contact.controller, relayOfs)
            addComponentPair(endpoint, contact, getContactOfEndpoints)
            addComponentId(endpoint)
            endpointIds.add(endpoint.id)
            if (contact.isNormallyOpen) initialOpenComponentIds.add(endpoint.id)
        }
    }
}