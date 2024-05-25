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


private fun buildEndpoint(name: String) = object : Component(name = "${name}_ENDPOINT") {
    override fun acceptVisitor(visitor: ComponentVisitor) {}
}

private fun buildEndpointUp(name: String) = object : Component(name = "${name}_ENDPOINT_UP") {
    override fun acceptVisitor(visitor: ComponentVisitor) {}
}

private fun buildEndpointDown(name: String) = object : Component(name = "${name}_ENDPOINT_DOWN") {
    override fun acceptVisitor(visitor: ComponentVisitor) {}
}

private fun buildLeftPlate(name: String) = object : Component("${name}_L") {
    override fun acceptVisitor(visitor: ComponentVisitor) {}
}

private fun buildRightPlate(name: String) = object : Component("${name}_R") {
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
