package csp_generator.util

import core.model.*
import core.model.visitor.ComponentVisitor
import java.util.*
import kotlin.reflect.KProperty

val RelayRegularContact.endpoint: Component
        by LazyWithReceiver<RelayRegularContact, Component> {
            buildEndpoint(name)
        }

val RelayChangeoverContact.endpoint1 by LazyWithReceiver<RelayChangeoverContact, Component> {
    when (this.controller) {
        is MonostableRelay -> buildEndpointUp(name)
        is BistableRelay -> buildEndpointLeft(name)
        else -> Component.DEFAULT
    }
}
val RelayChangeoverContact.endpoint2 by LazyWithReceiver<RelayChangeoverContact, Component> {
    when (this.controller) {
        is MonostableRelay -> buildEndpointDown(name)
        is BistableRelay -> buildEndpointRight(name)
        else -> Component.DEFAULT
    }
}

val Capacitor.leftPlate by LazyWithReceiver<Capacitor, Component> { buildLeftPlate(name) }
val Capacitor.rightPlate by LazyWithReceiver<Capacitor, Component> { buildRightPlate(name) }

val BistableRelay.leftCoil by LazyWithReceiver<BistableRelay, Component> { buildLeftCoil(name) }
val BistableRelay.rightCoil by LazyWithReceiver<BistableRelay, Component> { buildRightCoil(name) }

val TimedBlock.independentConnection by LazyWithReceiver<TimedBlock, Component> {
    buildIndependentConnection(name)
}
val TimedBlock.dependentEndpointPos by LazyWithReceiver<TimedBlock, Component> {
    buildDependentEndpointPos(name)
}
val TimedBlock.dependentEndpointNeg by LazyWithReceiver<TimedBlock, Component> {
    buildDependentEndpointNeg(name)
}

private fun buildEndpoint(name: String) = buildComponent("${name}_ENDPOINT")

private fun buildEndpointUp(name: String) = buildComponent("${name}_ENDPOINT_UP")

private fun buildEndpointDown(name: String) = buildComponent("${name}_ENDPOINT_DOWN")

private fun buildEndpointLeft(name: String) = buildComponent("${name}_ENDPOINT_LEFT")

private fun buildEndpointRight(name: String) = buildComponent("${name}_ENDPOINT_RIGHT")

private fun buildLeftPlate(name: String) = buildComponent("${name}_L")

private fun buildRightPlate(name: String) = buildComponent("${name}_R")

private fun buildLeftCoil(name: String) = buildComponent("${name}_COIL_L")

private fun buildRightCoil(name: String) = buildComponent("${name}_COIL_R")

private fun buildIndependentConnection(name: String) = buildComponent("${name}_INDEPENDENT_CONNECTION")

private fun buildDependentEndpointPos(name: String) = buildComponent("${name}_DEPENDENT_ENDPOINT_POS")

private fun buildDependentEndpointNeg(name: String) = buildComponent("${name}_DEPENDENT_ENDPOINT_NEG")

private fun buildComponent(name: String) = object : Component(name = name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {}
}


// Inspired by: https://stackoverflow.com/a/38084930/13874413
private class LazyWithReceiver<This, Return>(val initializer: This.() -> Return) {
    private val values = WeakHashMap<This, Return>()

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any, property: KProperty<*>): Return = synchronized(values)
    {
        thisRef as This
        return values.getOrPut(thisRef) { thisRef.initializer() }
    }
}
