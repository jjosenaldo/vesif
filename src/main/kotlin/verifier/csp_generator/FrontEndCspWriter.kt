package org.example.verifier.csp_generator

import org.example.verifier.csp_generator.model.CspPair
import java.io.BufferedWriter
import java.io.File

class FrontEndCspWriter {
    fun write(data: FrontEndCspData, output: String) {
        File(output).bufferedWriter().use { out ->
            out.apply {
                writeDataDefinition("IDS", data.ids)
                newLine()
                writeSetDefinition("POSITIVE_IDS", data.positiveIds)
                writeSetDefinition("NEGATIVE_IDS", data.negativeIds)
                newLine()
                writeSetDefinition("RELAY_IDS", data.relayIds)
                writeSetDefinition("CONTACT_CLOSED_IDS", data.contactClosedIds)
                writeSetDefinition("CONTACT_OPENED_IDS", data.contactOpenedIds)
                writeSetDefinition("TWO_WAY_CONTACT_IDS", data.twoWayContactIds)
                writeLine("CONTACT_IDS = union(TWO_WAY_CONTACT_IDS, union(CONTACT_CLOSED_IDS, CONTACT_OPENED_IDS))")
                writeLine("CONTACT_ENDPOINTS_IDS = union(ENDPOINT, union(ENDPOINT_TYPE_UP, ENDPOINT_TYPE_DOWN))")
                writeSetDefinition("ENDPOINT_TYPE_UP", data.endpointUpIds)
                writeSetDefinition("ENDPOINT_TYPE_DOWN", data.endpointDownIds)
                writeSetDefinition("ENDPOINT", data.endpointIds)
                newLine()
                writeSetDefinition("JUNCTION_IDS", data.junctionIds)
                newLine()
                writeSetDefinition("REAL_BUTTON_IDS", data.realButtonIds)
                writeSetDefinition("STUB_BUTTON_IDS", data.stubButtonIds)
                writeLine("BUTTON_IDS = union(REAL_BUTTON_IDS, STUB_BUTTON_IDS)")
                newLine()
                writeSetDefinition("LEVER_IDS", data.leverIds)
                writeSetDefinition("LEVER_CONTACT_IDS", data.leverContactIds)
                newLine()
                writeSetDefinition("CAPACITOR_IDS", data.capacitorIds)
                writeSetDefinition("CAPACITOR_PLATES_IDS", data.capacitorPlatesIds)
                newLine()
                writeSetDefinition("RESISTORS_IDS", data.resistorIds)
                newLine()
                writeSetDefinition("BISTABLE_RELAY_IDS", data.bistableRelayIds)
                writeSetDefinition("BISTABLE_RELAY_COILS_LEFT_IDS", data.bistableRelayCoilLeftIds)
                writeSetDefinition("BISTABLE_RELAY_COILS_RIGHT_IDS", data.bistableRelayCoilRightIds)
                newLine()
                writeSetDefinition("BS_CONTACT_LEFT_IDS", data.bistableRelayContactLeftIds)
                writeSetDefinition("BS_CONTACT_RIGHT_IDS", data.bistableRelayContactRightIds)
                writeSetDefinition("BS_TWO_WAY_CONTACT_IDS", data.bistableRelayTwoWayContactIds)
                writeLine("BISTABLE_CONTACT_IDS = union(BS_TWO_WAY_CONTACT_IDS, union(BS_CONTACT_LEFT_IDS, BS_CONTACT_RIGHT_IDS))")
                writeSetDefinition("ENDPOINT_TYPE_LEFT", data.endpointLeftIds)
                writeSetDefinition("ENDPOINT_TYPE_RIGHT", data.endpointRightIds)
                writeSetDefinition("BS_ENDPOINT", data.bistableRelayEndpointIds)
                writeLine("BS_CONTACT_ENDPOINTS_IDS = union(BS_ENDPOINT, union(ENDPOINT_TYPE_LEFT, ENDPOINT_TYPE_RIGHT))")
                newLine()
                writeSetDefinition("LAMP_IDS", data.lampIds)
                newLine()
                writeSetDefinition("DEACTIVATION_BLOCKS_IDS", data.deactBlockIds)
                writeSetDefinition("DEACTIVATION_BLOCKS_INDEPENDENT_IDS", data.deactBlockIndependentIds)
                writeSetDefinition("DEACTIVATION_BLOCKS_DEPENDENT_IDS", data.deactBlockDependentIds)
                newLine()
                writeSetDefinition("TIME_DEACTIVATION_SETTING", data.timeDeactSettings)
                writeDefinition("INITIAL_MAX_DEACTIVATION_TIME", "setup_max_time${data.initialMaxDeactTimes}")
                writeDefinition("MAX_DEACTIVATION_TIME", data.maxDeactTime)
                newLine()
                writeSetDefinition("ACTIVATION_BLOCKS_IDS", data.actBlockIds)
                writeSetDefinition("ACTIVATION_BLOCKS_INDEPENDENT_IDS", data.actBlockIndependentIds)
                writeSetDefinition("ACTIVATION_BLOCKS_DEPENDENT_IDS", data.actBlockDependentIds)
                newLine()
                writeSetDefinition("TIME_ACTIVATION_SETTING", data.timeActSettings)
                writeDefinition("INITIAL_MAX_ACTIVATION_TIME", "setup_max_time${data.initialMaxActTimes}")
                writeDefinition("MAX_ACTIVATION_TIME", data.maxActTime)
                newLine()
                writeSetDefinition("INITIAL_OPEN_COMPONENTS", data.initialOpenComponentIds)
                newLine()
                writeSingleArgFunctionDefinitions("RELAY_OF", data.relayOfs)
                newLine()
                writeSingleArgFunctionDefinitions("GET_CONTACT_OF_ENDPOINT", data.getContactOfEndpoints)
                writeSingleArgFunctionDefinitions("GET_ENDPOINT_DOWN_OF", data.getEndpointDownOf)
                writeSingleArgFunctionDefinitions("GET_ENDPOINT_UP_OF", data.getEndpointUpOf)
                newLine()
                writeTwoArgFunctionDefinitions("OPEN_LEVER_CONTACTS_OF", data.openLeverContactsOf)
                newLine()
                writeSingleArgFunctionDefinitions("CapPoles", data.capPoles)
                writeSingleArgFunctionDefinitions("CapLeftPole", data.capLeftPoles)
                writeSingleArgFunctionDefinitions("CapRightPole", data.capRightPoles)
                newLine()
                writeLine("INITIAL_POSITIVES = union(POSITIVE_IDS, {})")
                writeLine("INITIAL_NEGATIVES = union(NEGATIVE_IDS, {})")
                writeSetDefinition("INITIAL_CHARGES", data.initialCharges)
                writeDefinition("INITIAL_MAX_CHARGE", "capacitance${data.initialMaxCharge}")
                writeDefinition("MAX_CHARGE", data.maxCharge)
                newLine()
                writeSingleArgFunctionDefinitions("GET_BS_CONTACT_OF", data.getBsContactOf)
                writeSingleArgFunctionDefinitions("GET_COIL_FROM_BS_ENDPOINT_L", data.getCoilFromBsEndpointL)
                writeSingleArgFunctionDefinitions("GET_COIL_FROM_BS_ENDPOINT_R", data.getCoilFromBsEndpointR)
                writeSingleArgFunctionDefinitions("GET_BS_ENDPOINT_LEFT_OF", data.getBsEndpointLeftOf)
                writeSingleArgFunctionDefinitions("GET_BS_ENDPOINT_RIGHT_OF", data.getBsEndpointRightOf)
                newLine()
                writeSingleArgFunctionDefinitions(
                    "GET_INDEPENDENT_CONNECTION_OF_DEACTIVATION",
                    data.getIndependentConnectionOfDeact
                )
                writeSingleArgFunctionDefinitions("GET_POSITIVE_OF_DEACTIVATION_BLOCK", data.getPositiveOfDeactBlock)
                writeSingleArgFunctionDefinitions("GET_NEGATIVE_OF_DEACTIVATION_BLOCK", data.getNegativeOfDeactBlock)
                newLine()
                writeSingleArgFunctionDefinitions(
                    "GET_INDEPENDENT_CONNECTION_OF_ACTIVATION",
                    data.getIndependentConnectionOfAct
                )
                writeSingleArgFunctionDefinitions("GET_POSITIVE_OF_ACTIVATION_BLOCK", data.getPositiveOfActBlock)
                writeSingleArgFunctionDefinitions("GET_NEGATIVE_OF_ACTIVATION_BLOCK", data.getNegativeOfActBlock)
                newLine()
                writeSetOfSetsDefinition("CONNECTIONS", data.connections.map { setOf(it.first, it.second) }.toSet())
            }
        }

    }
}

private fun BufferedWriter.writeLine(line: String) {
    write(line)
    newLine()
}

private fun <T> BufferedWriter.writeDefinition(name: String, definition: T) {
    write("$name = $definition")
    newLine()
}

private fun <T> BufferedWriter.writeSetDefinition(name: String, definition: Set<T>) {
    writeDefinition(name, "{${definition.joinToString(", ")}}")
    newLine()
}

private fun <T> BufferedWriter.writeSetOfSetsDefinition(name: String, definition: Set<Set<T>>) {
    writeDefinition(
        name, "{${
            definition.joinToString(", ") { innerSet ->
                "{${innerSet.joinToString { ", " }}}"
            }
        }"
    )
    newLine()
}

private fun BufferedWriter.writeDataDefinition(name: String, definition: Set<String>) {
    write("$name = {${definition.joinToString(" | ")}}")
    newLine()
}

private fun BufferedWriter.writeFunctionDefinition(name: String, args: List<String>, result: Any) {
    write("$name(${args.joinToString { ", " }}) = $result")
}

private fun BufferedWriter.writeSingleArgFunctionDefinition(name: String, arg: String, result: Any) {
    writeFunctionDefinition(name, listOf(arg), result)
}

private fun BufferedWriter.writeSingleArgFunctionDefinitions(name: String, args: Set<CspPair<String, Any>>) {
    args.forEach {
        writeSingleArgFunctionDefinition(name, it.first, it.second)
    }
}

private fun BufferedWriter.writeTwoArgFunctionDefinitions(
    name: String,
    args: Set<CspPair<CspPair<String, String>, Any>>
) {
    args.forEach {
        val functionArgs = it.first
        writeFunctionDefinition(name, listOf(functionArgs.first, functionArgs.second), it.second)
    }
}
