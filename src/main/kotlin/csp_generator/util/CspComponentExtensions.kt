package csp_generator.util

import core.model.*
import core.model.visitor.ComponentVisitor
import java.util.*
import kotlin.reflect.KProperty

val RelayRegularContact.endpoint: Component
        by LazyWithReceiver<RelayRegularContact, Component> {
            buildEndpoint(name)
        }

val RelayChangeOverContact.endpointUp by LazyWithReceiver<RelayChangeOverContact, Component> { buildEndpointUp(name) }
val RelayChangeOverContact.endpointDown by LazyWithReceiver<RelayChangeOverContact, Component> { buildEndpointDown(name) }

val Capacitor.leftPlate by LazyWithReceiver<Capacitor, Component> { buildLeftPlate(name) }
val Capacitor.rightPlate by LazyWithReceiver<Capacitor, Component> { buildRightPlate(name) }

val BistableRelay.leftCoil by LazyWithReceiver<BistableRelay, Component> { buildLeftCoil(name) }
val BistableRelay.rightCoil by LazyWithReceiver<BistableRelay, Component> { buildRightCoil(name) }

private fun buildEndpoint(name: String) = buildComponent("${name}_ENDPOINT")

private fun buildEndpointUp(name: String) = buildComponent("${name}_ENDPOINT_UP")

private fun buildEndpointDown(name: String) = buildComponent("${name}_ENDPOINT_DOWN")

private fun buildLeftPlate(name: String) = buildComponent("${name}_L")

private fun buildRightPlate(name: String) = buildComponent("${name}_R")

private fun buildLeftCoil(name: String) = buildComponent("${name}_COIL_L")

private fun buildRightCoil(name: String) = buildComponent("${name}_COIL_R")

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
