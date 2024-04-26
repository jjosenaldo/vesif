package org.example.verifier.csp_generator.generator

import org.example.verifier.csp_generator.model.CspPair
import org.example.verifier.csp_generator.model.FrontEndCspData
import java.io.BufferedWriter
import java.io.File

object FrontEndCspWriter {
    fun write(data: FrontEndCspData, output: String) {
        val file = File(output)
        if (!file.exists()) {
            file.createNewFile()
        }

        file.bufferedWriter().use { out ->
            out.apply {
                writeDataDefinition("IDS", data.ids)
                writeDefinition("POSITIVE_IDS", data.positiveIds)
                writeDefinition("NEGATIVE_IDS", data.negativeIds)
                newLine()
                writeDefinition("RELAY_IDS", data.relayIds)
                writeDefinition("CONTACT_CLOSED_IDS", data.contactClosedIds)
                writeDefinition("CONTACT_OPENED_IDS", data.contactOpenedIds)
                writeDefinition("TWO_WAY_CONTACT_IDS", data.twoWayContactIds)
                writeLine("CONTACT_IDS = union(TWO_WAY_CONTACT_IDS, union(CONTACT_CLOSED_IDS, CONTACT_OPENED_IDS))")
                writeLine("CONTACT_ENDPOINTS_IDS = union(ENDPOINT, union(ENDPOINT_TYPE_UP, ENDPOINT_TYPE_DOWN))")
                writeDefinition("ENDPOINT_TYPE_UP", data.endpointUpIds)
                writeDefinition("ENDPOINT_TYPE_DOWN", data.endpointDownIds)
                writeDefinition("ENDPOINT", data.endpointIds)
                newLine()
                writeDefinition("JUNCTION_IDS", data.junctionIds)
                newLine()
                writeDefinition("REAL_BUTTON_IDS", data.realButtonIds)
                writeDefinition("STUB_BUTTON_IDS", data.stubButtonIds)
                writeLine("BUTTON_IDS = union(REAL_BUTTON_IDS, STUB_BUTTON_IDS)")
                newLine()
                writeDefinition("LEVER_IDS", data.leverIds)
                writeDefinition("LEVER_CONTACT_IDS", data.leverContactIds)
                newLine()
                writeDefinition("CAPACITOR_IDS", data.capacitorIds)
                writeDefinition("CAPACITOR_PLATES_IDS", data.capacitorPlatesIds)
                newLine()
                writeDefinition("RESISTORS_IDS", data.resistorIds)
                newLine()
                writeDefinition("BISTABLE_RELAY_IDS", data.bistableRelayIds)
                writeDefinition("BISTABLE_RELAY_COILS_LEFT_IDS", data.bistableRelayCoilLeftIds)
                writeDefinition("BISTABLE_RELAY_COILS_RIGHT_IDS", data.bistableRelayCoilRightIds)
                newLine()
                writeDefinition("BS_CONTACT_LEFT_IDS", data.bistableRelayContactLeftIds)
                writeDefinition("BS_CONTACT_RIGHT_IDS", data.bistableRelayContactRightIds)
                writeDefinition("BS_TWO_WAY_CONTACT_IDS", data.bistableRelayTwoWayContactIds)
                writeLine("BISTABLE_CONTACT_IDS = union(BS_TWO_WAY_CONTACT_IDS, union(BS_CONTACT_LEFT_IDS, BS_CONTACT_RIGHT_IDS))")
                writeDefinition("ENDPOINT_TYPE_LEFT", data.endpointLeftIds)
                writeDefinition("ENDPOINT_TYPE_RIGHT", data.endpointRightIds)
                writeDefinition("BS_ENDPOINT", data.bistableRelayEndpointIds)
                writeLine("BS_CONTACT_ENDPOINTS_IDS = union(BS_ENDPOINT, union(ENDPOINT_TYPE_LEFT, ENDPOINT_TYPE_RIGHT))")
                newLine()
                writeDefinition("LAMP_IDS", data.lampIds)
                newLine()
                writeDefinition("DEACTIVATION_BLOCKS_IDS", data.deactBlockIds)
                writeDefinition("DEACTIVATION_BLOCKS_INDEPENDENT_IDS", data.deactBlockIndependentIds)
                writeDefinition("DEACTIVATION_BLOCKS_DEPENDENT_IDS", data.deactBlockDependentIds)
                newLine()
                writeDefinition("TIME_DEACTIVATION_SETTING", data.timeDeactSettings)
                writeDefinition("INITIAL_MAX_DEACTIVATION_TIME", "setup_max_time${data.initialMaxDeactTime}")
                writeDefinition("MAX_DEACTIVATION_TIME", data.maxDeactTime)
                newLine()
                writeDefinition("ACTIVATION_BLOCKS_IDS", data.actBlockIds)
                writeDefinition("ACTIVATION_BLOCKS_INDEPENDENT_IDS", data.actBlockIndependentIds)
                writeDefinition("ACTIVATION_BLOCKS_DEPENDENT_IDS", data.actBlockDependentIds)
                newLine()
                writeDefinition("TIME_ACTIVATION_SETTING", data.timeActSettings)
                writeDefinition("INITIAL_MAX_ACTIVATION_TIME", "setup_max_time${data.initialMaxActTime}")
                writeDefinition("MAX_ACTIVATION_TIME", data.maxActTime)
                newLine()
                writeDefinition("INITIAL_OPEN_COMPONENTS", data.initialOpenComponentIds)
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
                writeDefinition("INITIAL_CHARGES", data.initialCharges)
                writeDefinition("INITIAL_MAX_CHARGE", "capacitance${data.initialMaxCharge}")
                writeDefinition("MAX_CHARGE", "charge_max${data.maxCharge}")
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
                writeSingleArgFunctionDefinitions(
                    "GET_POSITIVE_OF_DEACTIVATION_BLOCK",
                    data.getPositiveOfDeactBlock
                )
                writeSingleArgFunctionDefinitions(
                    "GET_NEGATIVE_OF_DEACTIVATION_BLOCK",
                    data.getNegativeOfDeactBlock
                )
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

private fun BufferedWriter.writeDefinition(name: String, definition: Any) {
    writeLine(
        "$name = ${
            definitionDataToString(definition)
        }"
    )
}

private fun <T> BufferedWriter.writeSetOfSetsDefinition(name: String, definition: Set<Set<T>>) {
    // TODO: put in an utils file
    val newLine = System.lineSeparator()
    val trailingNewLine = if (definition.isEmpty()) "" else newLine

    // TODO: put in a config file
    val tab = " ".repeat(4)

    writeDefinition(
        name, "{$trailingNewLine${
            definition.joinToString(",$newLine") { innerSet ->
                "$tab{${innerSet.joinToString(", ")}}"
            }
        }$trailingNewLine}"
    )
}

private fun BufferedWriter.writeDataDefinition(name: String, definition: Set<String>) {
    writeLine("datatype $name = ${definition.joinToString(" | ")}")
}

private fun BufferedWriter.writeFunctionDefinition(name: String, args: List<String>, result: Any) {
    writeDefinition("$name(${args.joinToString(", ")})", result)
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

private fun definitionDataToString(data: Any): String {
    return when (data) {
        is Set<*> -> "{${data.joinToString(", ")}}"
        else -> data.toString()
    }
}