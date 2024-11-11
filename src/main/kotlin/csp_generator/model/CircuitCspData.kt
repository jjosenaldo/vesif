package csp_generator.model

import core.model.*
import core.model.visitor.ComponentVisitor
import core.model.RelayChangeoverContact
import csp_generator.util.*

// TODO: do buffered writers need to be closed?
// TODO: clear data after generating a file
class CircuitCspData : ComponentVisitor {
    val ids = mutableSetOf<String>()
    val positiveIds = mutableSetOf<String>()
    val negativeIds = mutableSetOf<String>()
    val bendIds = mutableSetOf<String>()
    val relayIds = mutableSetOf<String>()
    val contactClosedIds = mutableSetOf<String>()
    val contactOpenedIds = mutableSetOf<String>()
    val twoWayContactIds = mutableSetOf<String>()
    val endpointUpIds = mutableSetOf<String>()
    val endpointDownIds = mutableSetOf<String>()
    val endpointIds = mutableSetOf<String>()
    val junctionIds = mutableSetOf<String>()
    val buttonIds = mutableSetOf<String>()
    val stubButtonIds = mutableSetOf<String>()
    val leverIds = mutableSetOf<String>()
    val stubLeverIds = mutableSetOf<String>()
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
    val initialMaxDeactTime = mutableListOf<CspPair<String, Int>>()
    val actBlockIds = mutableSetOf<String>()
    val actBlockIndependentIds = mutableSetOf<String>()
    val actBlockDependentIds = mutableSetOf<String>()
    val timeActSettings = mutableSetOf<CspPair<String, Int>>()
    val initialMaxActTime = mutableListOf<CspPair<String, Int>>()
    val initialOpenComponentIds = mutableSetOf<String>()
    val relayOfs = mutableSetOf<CspPair<String, String>>()
    val getContactOfEndpoints = mutableSetOf<CspPair<String, String>>()
    val getEndpointUpOf = mutableMapOf<String, Set<String>>()
    val getEndpointDownOf = mutableMapOf<String, Set<String>>()
    val openLeverContactsOf = mutableMapOf<CspPair<String, String>, Set<String>>()
    val capPoles = mutableSetOf<CspPair<String, Set<String>>>()
    val capLeftPoles = mutableSetOf<CspPair<String, String>>()
    val capRightPoles = mutableSetOf<CspPair<String, String>>()
    val initialCharges = mutableSetOf<CspPair<String, Int>>()
    val initialMaxCharges = mutableListOf<CspPair<String, Int>>()
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

    fun fillData(components: List<Component>) {
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
            relayOfs.add(CspPair("id0", "R_default"))
        }

        if (components.all { it !is RelayChangeoverContact || it.controller !is MonostableRelay }) {
            addFunctionArg("id", target = getEndpointUpOf)
            addFunctionArg("id", target = getEndpointDownOf)
        }
    }

    private fun addLeverDefaultIds() {
        val contactId = "LC_default"
        val leverId = "L_default"
        ids.add(contactId)
        ids.add(leverId)
        leverContactIds.add(contactId)
        leverIds.add(leverId)
        openLeverContactsOf[CspPair("id", "side")] = setOf()
    }

    private fun addButtonDefaultIds() {
        val id = "B_default"
        ids.add(id)
        buttonIds.add(id)
    }

    private fun addCapacitorDefaultIds(components: List<Component>) {
        val defaultCapacitorId = "CP_default"
        val defaultPlate1 = "PL_default"
        val defaultPlate2 = "PL2_default"

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

        if (components.all { it !is Capacitor }) {
            capPoles.add(CspPair(defaultCapacitorId, setOf(defaultPlate1, defaultPlate2)))
            capLeftPoles.add(CspPair(defaultCapacitorId, defaultPlate1))
            capRightPoles.add(CspPair(defaultCapacitorId, defaultPlate2))
            initialCharges.add(CspPair(defaultCapacitorId, 0))
            initialMaxCharges.add(CspPair(defaultCapacitorId, 0))
            return
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

        val relayContacts = components.filterIsInstance<Contact>()
        val bistableRelayContacts = relayContacts.filter { it.controller is BistableRelay }
        relayContacts.filter { it.controller is MonostableRelay }

        // BS_ENDPOINT only filled with regular contacts from bistable relays
        if (relayContacts.all { it.controller is MonostableRelay || it.controller.contacts.none { it2 -> it2 is MonostableSimpleContact } }) {
            bistableRelayEndpointIds.add("C_ENDPOINT_default")
        }

        // ENDPOINT only filled with regular contacts from monostable relays
        if (relayContacts.all { it.controller is BistableRelay || it.controller.contacts.none { it2 -> it2 is MonostableSimpleContact } }) {
            endpointIds.add("C_ENDPOINT_default")
        }

        if (bistableRelayContacts.isEmpty()) {
            getBsContactOf.add(CspPair("C_ENDPOINT_default", "C_default"))
        }

        if (bistableRelayContacts.none { it is RelayChangeoverContact }) {
            getBsEndpointLeftOf.add(CspPair("BS_C_default", setOf("BS_C_LEFT_default")))
            getBsEndpointRightOf.add(CspPair("BS_C_default", setOf("BS_C_RIGHT_default")))
        }

        if (bistableRelayContacts.none { it is MonostableSimpleContact }) {
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

        val blocks = components.filterIsInstance<TimedBlock>()
        val actBlocks = blocks.filter { it.isActivation }
        val deactBlocks = blocks.filter { !it.isActivation }

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
            initialMaxActTime.add(CspPair("BL_default", 0))
            getIndependentConnectionOfAct.add(CspPair("BL_default", "BL_INDEPENDENT_CONNECTION_default"))
            getPositiveOfActBlock.add(CspPair("BL_default", emptySet()))
            getNegativeOfActBlock.add(CspPair("BL_default", emptySet()))
        }

        if (deactBlocks.isEmpty()) {
            deactBlockIds.add("BL_default")
            timeDeactSettings.add(CspPair("BL_default", 0))
            initialMaxDeactTime.add(CspPair("BL_default", 0))
            getIndependentConnectionOfDeact.add(CspPair("BL_default", "BL_INDEPENDENT_CONNECTION_default"))
            getPositiveOfDeactBlock.add(CspPair("BL_default", emptySet()))
            getNegativeOfDeactBlock.add(CspPair("BL_default", emptySet()))
        }
    }

    /**
     * Inserts ids of elements specific to the CSP translation, such as endpoint ids,
     * PATH_MASTER_id etc
     */
    // TODO: no need to filter like this, make the functions not to receive lists
    private fun generateAdditionalIds(components: List<Component>) {
        ids.add("PATH_MASTER_id")
        insertMonostableSimpleContactEndpoints(components.filterIsInstance<MonostableSimpleContact>())
        insertBistableSimpleContactEndpoints(components.filterIsInstance<BistableSimpleContact>())
        insertChangeoverContactEndpoints(components.filterIsInstance<RelayChangeoverContact>())
        insertCapacitorPlates(components.filterIsInstance<Capacitor>())
        insertBistableRelayCoils(components.filterIsInstance<BistableRelay>())
        insertTimedBlockIndependentConnections(components.filterIsInstance<TimedBlock>())
        insertTimedBlockDependentConnections(components.filterIsInstance<TimedBlock>())
    }

    private fun addComponentId(component: Component) {
        ids.add(component.id)
    }

    // TODO: simplify IF expressions
    private fun addConnection(left: Component, right: Component) {
        if (left is MonostableSimpleContact
            && right.id == left.rightNeighbor.id
        ) {
            addConnection(left, left.endpoint)
            addConnection(left.endpoint, right)
        } else if (right is MonostableSimpleContact
            && left.id == right.rightNeighbor.id
        ) {
            addConnection(right.endpoint, right)
            addConnection(left, right.endpoint)
        } else if (left is RelayChangeoverContact
            && right.id == left.pairNeighbor1.id
        ) {
            addConnection(left, left.endpoint1)
            addConnection(left.endpoint1, right)
        } else if (left is RelayChangeoverContact
            && right.id == left.pairNeighbor2.id
        ) {
            addConnection(left, left.endpoint2)
            addConnection(left.endpoint2, right)
        } else if (right is RelayChangeoverContact
            && left.id == right.pairNeighbor1.id
        ) {
            addConnection(right, right.endpoint1)
            addConnection(right.endpoint1, left)
        } else if (right is RelayChangeoverContact
            && left.id == right.pairNeighbor2.id
        ) {
            addConnection(right, right.endpoint2)
            addConnection(right.endpoint2, left)
        } else if (left is Capacitor
            && right.id == left.rightNeighbor.id
        ) {
            addConnection(left.rightPlate, right)
        } else if (left is Capacitor
            && right.id == left.leftNeighbor.id
        ) {
            addConnection(left.leftPlate, right)
        } else if (right is Capacitor
            && left.id == right.rightNeighbor.id
        ) {
            addConnection(right.rightPlate, left)
        } else if (right is Capacitor
            && left.id == right.leftNeighbor.id
        ) {
            addConnection(right.leftPlate, left)
        } else if (left is BistableRelay && left.neighbors.any { it.id == right.id }) {
            addBistableRelayConnections(left, right)
        } else if (right is BistableRelay && right.neighbors.any { it.id == left.id }) {
            addBistableRelayConnections(right, left)
        } else if (left is TimedBlock) {
            addTimedBlockConnections(left, right)
        } else if (right is TimedBlock) {
            addTimedBlockConnections(right, left)
        } else {
            addComponentPair(left, right, connections)
        }
    }

    private fun addBistableRelayConnections(relay: BistableRelay, neighbor: Component) {
        when (neighbor.id) {
            relay.leftUpNeighbor.id, relay.leftDownNeighbor.id -> {
                addConnection(relay.leftUpNeighbor, relay.leftCoil)
                addConnection(relay.leftCoil, relay.leftDownNeighbor)
            }

            relay.rightUpNeighbor.id, relay.rightDownNeighbor.id -> {
                addConnection(relay.rightUpNeighbor, relay.rightCoil)
                addConnection(relay.rightCoil, relay.rightDownNeighbor)
            }
        }
    }

    private fun addTimedBlockConnections(timedBlock: TimedBlock, neighbor: Component) {
        when (neighbor.id) {
            timedBlock.dependentPos.id -> {
                addConnection(timedBlock.dependentEndpointPos, neighbor)

                if (neighbor.id == timedBlock.dependentNeg.id) {
                    addConnection(timedBlock.dependentEndpointNeg, neighbor)
                }
            }

            timedBlock.dependentNeg.id -> {
                addConnection(timedBlock.dependentEndpointNeg, neighbor)
            }

            timedBlock.posSource.id -> {
                addConnection(timedBlock.independentDown, neighbor)
            }

            timedBlock.negSource.id -> {
                addConnection(timedBlock.independentConnection, neighbor)
            }

            timedBlock.independentUp.id -> {
                addConnection(timedBlock.independentConnection, neighbor)
            }
        }
    }

    private fun addComponentPair(
        leftComponent: Component,
        rightComponent: Component,
        target: MutableSet<CspPair<String, String>>
    ) {
        target.add(CspPair(leftComponent.id, rightComponent.id))
    }

    private fun addFunctionArg(arg: Component, value: Component, target: MutableMap<String, Set<String>>) {
        addFunctionArg(arg.id, value.id, target)
    }

    private fun addFunctionArg(arg: String, value: String? = null, target: MutableMap<String, Set<String>>) {
        target[arg] = (target[arg] ?: setOf()) union if (value != null) setOf(value) else setOf()
    }

    override fun visitButton(button: Button) {
        addComponentId(button)
        // All buttons should be stub, as of the meeting on 22/10/2024
        stubButtonIds.add(button.id)
        initialOpenComponentIds.add(button.id)
        addConnection(button.leftNeighbor, button)
        addConnection(button, button.rightNeighbor)
    }

    override fun visitPole(pole: Pole) {
        addComponentId(pole)
        val listToAdd = if (pole.isPositive) positiveIds else negativeIds
        listToAdd.add(pole.id)
        addConnection(pole, pole.neighbor)
    }

    override fun visitMonostableRelay(monostableRelay: MonostableRelay) {
        addComponentId(monostableRelay)
        relayIds.add(monostableRelay.id)
        addConnection(monostableRelay.leftNeighbor, monostableRelay)
        addConnection(monostableRelay, monostableRelay.rightNeighbor)
    }

    override fun visitMonostableSimpleContact(contact: MonostableSimpleContact) {
        addComponentId(contact)
        val listToAdd = if (contact.isNormallyOpen) contactOpenedIds else contactClosedIds
        listToAdd.add(contact.id)
        addConnection(contact.leftNeighbor, contact)
        addConnection(contact, contact.rightNeighbor)
    }

    override fun visitBistableSimpleContact(contact: BistableSimpleContact) {
        addComponentId(contact)
        if (contact.isInitiallyOpen) initialOpenComponentIds.add(contact.id)

        val listToAdd =
            if (contact.isCloseSideLeft) bistableRelayContactLeftIds else bistableRelayContactRightIds

        listToAdd.add(contact.id)
        addConnection(contact.leftNeighbor, contact)
        addConnection(contact, contact.rightNeighbor)
    }

    override fun visitCapacitor(capacitor: Capacitor) {
        addComponentId(capacitor)
        capacitorIds.add(capacitor.id)
        initialCharges.add(CspPair(capacitor.id, capacitor.initialCharge))
        initialMaxCharges.add(CspPair(capacitor.id, capacitor.maxCharge))
    }

    override fun visitJunction(junction: Junction) {
        addComponentId(junction)
        junctionIds.add(junction.id)
        addConnection(junction.leftNeighbor, junction)
        addConnection(junction, junction.upNeighbor)
        addConnection(junction, junction.downNeighbor)
    }

    override fun visitLever(lever: Lever) {
        addComponentId(lever)
        stubLeverIds.add(lever.id)
    }

    override fun visitLeverContact(leverContact: LeverContact) {
        addComponentId(leverContact)
        leverContactIds.add(leverContact.id)
        addConnection(leverContact.leftNeighbor, leverContact)
        addConnection(leverContact, leverContact.rightNeighbor)
        val lever = leverContact.controller
        val side = if (leverContact.isLeftOpen) "LEFT" else "RIGHT"
        openLeverContactsOf[CspPair(lever.id, side)] =
            (openLeverContactsOf[CspPair(lever.id, side)] ?: setOf()) union setOf(leverContact.id)
    }

    override fun visitLamp(lamp: Lamp) {
        addComponentId(lamp)
        lampIds.add(lamp.id)
        addConnection(lamp.leftNeighbor, lamp)
        addConnection(lamp, lamp.rightNeighbor)
    }

    override fun visitRelayChangeoverContact(contact: RelayChangeoverContact) {
        addComponentId(contact)
        addConnection(contact.soloNeighbor, contact)
        addConnection(contact, contact.pairNeighbor1)
        addConnection(contact, contact.pairNeighbor2)

        when (contact.controller) {
            is MonostableRelay -> {
                twoWayContactIds.add(contact.id)
            }

            is BistableRelay -> {
                bistableRelayTwoWayContactIds.add(contact.id)
            }
        }
    }

    override fun visitResistor(resistor: Resistor) {
        addComponentId(resistor)
        resistorIds.add(resistor.id)
        addConnection(resistor.leftNeighbor, resistor)
        addConnection(resistor, resistor.rightNeighbor)
    }

    override fun visitBistableRelay(relay: BistableRelay) {
        addComponentId(relay)
        bistableRelayIds.add(relay.id)
        addConnection(relay.leftUpNeighbor, relay)
        addConnection(relay, relay.leftDownNeighbor)
        addConnection(relay.rightUpNeighbor, relay)
        addConnection(relay, relay.rightDownNeighbor)
    }

    override fun visitTimedBlock(block: TimedBlock) {
        addComponentId(block)

        if (block.isActivation) {
            actBlockIds.add(block.id)
            timeActSettings.add(CspPair(block.id, block.initialTime))
            initialMaxActTime.add(CspPair(block.id, block.maxTime))
        } else {
            deactBlockIds.add(block.id)
            timeDeactSettings.add(CspPair(block.id, block.initialTime))
            initialMaxDeactTime.add(CspPair(block.id, block.maxTime))
        }

        addConnection(block, block.posSource)
        addConnection(block, block.negSource)
        addConnection(block, block.dependentPos)
        addConnection(block, block.dependentNeg)
        addConnection(block, block.independentUp)
        addConnection(block, block.independentDown)
    }

    override fun visitBend(bend: Bend) {
        addComponentId(bend)
        bendIds.add(bend.id)
        addConnection(bend.leftNeighbor, bend)
        addConnection(bend, bend.rightNeighbor)
    }

    /**
     * Convention: the endpoint is always the contact's right neighbor
     */
    private fun insertMonostableSimpleContactEndpoints(contacts: List<MonostableSimpleContact>) {
        for (contact in contacts) {
            addComponentId(contact.endpoint)
            if (contact.isNormallyOpen) initialOpenComponentIds.add(contact.endpoint.id)

            addComponentPair(contact.endpoint, contact.controller, relayOfs)
            addComponentPair(contact.endpoint, contact, getContactOfEndpoints)
            endpointIds.add(contact.endpoint.id)
        }
    }

    private fun insertBistableSimpleContactEndpoints(contacts: List<BistableSimpleContact>) {
        for (contact in contacts) {
            addComponentId(contact.endpoint)
            if (contact.isInitiallyOpen) initialOpenComponentIds.add(contact.endpoint.id)

            addComponentPair(contact.endpoint, contact, getBsContactOf)
            bistableRelayEndpointIds.add(contact.endpoint.id)

            val controller = contact.controller

            if (controller !is BistableRelay) {
                // TODO: improve this check
                throw Exception()
            }
            getCoilFromBsEndpointL.add(CspPair(contact.endpoint.id, controller.leftCoil.id))
            getCoilFromBsEndpointR.add(CspPair(contact.endpoint.id, controller.rightCoil.id))
        }
    }

    private fun insertChangeoverContactEndpoints(contacts: List<RelayChangeoverContact>) {
        for (contact in contacts) {
            addComponentId(contact.endpoint1)
            addComponentId(contact.endpoint2)
            val openEndpoint = if (contact.isClosedSideUpOrLeft) contact.endpoint2 else contact.endpoint1
            initialOpenComponentIds.add(openEndpoint.id)

            when (val controller = contact.controller) {
                is MonostableRelay -> {
                    endpointUpIds.add(contact.endpoint1.id)
                    endpointDownIds.add(contact.endpoint2.id)
                    addComponentPair(contact.endpoint1, controller, relayOfs)
                    addComponentPair(contact.endpoint2, controller, relayOfs)
                    addComponentPair(contact.endpoint1, contact, getContactOfEndpoints)
                    addComponentPair(contact.endpoint2, contact, getContactOfEndpoints)
                    addFunctionArg(controller, contact.endpoint1, getEndpointUpOf)
                    addFunctionArg(controller, contact.endpoint2, getEndpointDownOf)
                }

                is BistableRelay -> {
                    endpointLeftIds.add(contact.endpoint1.id)
                    endpointRightIds.add(contact.endpoint2.id)
                    addComponentPair(contact.endpoint1, contact, getBsContactOf)
                    addComponentPair(contact.endpoint2, contact, getBsContactOf)
                    getCoilFromBsEndpointL.add(CspPair(contact.endpoint1.id, controller.leftCoil.id))
                    getCoilFromBsEndpointR.add(CspPair(contact.endpoint1.id, controller.rightCoil.id))
                    getCoilFromBsEndpointL.add(CspPair(contact.endpoint2.id, controller.leftCoil.id))
                    getCoilFromBsEndpointR.add(CspPair(contact.endpoint2.id, controller.rightCoil.id))
                    getBsEndpointLeftOf.add(CspPair(contact.id, mutableSetOf(contact.endpoint1.id)))
                    getBsEndpointRightOf.add(CspPair(contact.id, mutableSetOf(contact.endpoint2.id)))
                }
            }

        }
    }

    private fun insertCapacitorPlates(capacitors: List<Capacitor>) {
        for (capacitor in capacitors) {
            addComponentId(capacitor.leftPlate)
            addComponentId(capacitor.rightPlate)
            listOf(capacitor.leftPlate.id, capacitor.rightPlate.id).let {
                capacitorPlatesIds.addAll(it)
                capPoles.add(CspPair(capacitor.id, it.toSet()))
            }
            capLeftPoles.add(CspPair(capacitor.id, capacitor.leftPlate.id))
            capRightPoles.add(CspPair(capacitor.id, capacitor.rightPlate.id))
        }
    }

    private fun insertBistableRelayCoils(relays: List<BistableRelay>) {
        for (relay in relays) {
            addComponentId(relay.leftCoil)
            addComponentId(relay.rightCoil)
            bistableRelayCoilLeftIds.add(relay.leftCoil.id)
            bistableRelayCoilRightIds.add(relay.rightCoil.id)
        }
    }

    private fun insertTimedBlockDependentConnections(blocks: List<TimedBlock>) {
        for (block in blocks) {
            addComponentId(block.dependentEndpointPos)
            addComponentId(block.dependentEndpointNeg)
            positiveIds.add(block.dependentEndpointPos.id)
            negativeIds.add(block.dependentEndpointNeg.id)

            if (block.isActivation) {
                getPositiveOfActBlock.add(CspPair(block.id, setOf(block.dependentEndpointPos.id)))
                getNegativeOfActBlock.add(CspPair(block.id, setOf(block.dependentEndpointNeg.id)))
            } else {
                getPositiveOfDeactBlock.add(CspPair(block.id, setOf(block.dependentEndpointPos.id)))
                getNegativeOfDeactBlock.add(CspPair(block.id, setOf(block.dependentEndpointNeg.id)))
            }
        }
    }

    private fun insertTimedBlockIndependentConnections(blocks: List<TimedBlock>) {
        for (block in blocks) {
            addComponentId(block.independentConnection)

            if (block.isActivation) {
                getIndependentConnectionOfAct.add(CspPair(block.id, block.independentConnection.id))
            } else {
                getIndependentConnectionOfDeact.add(CspPair(block.id, block.independentConnection.id))
            }
        }
    }
}