package core.model.visitor

import core.model.*

interface ComponentVisitor {
    fun visitButton(button: Button)
    fun visitPole(pole: Pole)
    fun visitMonostableRelay(monostableRelay: MonostableRelay)
    fun visitRelayRegularContact(contact: RelayRegularContact)
    fun visitCapacitor(capacitor: Capacitor)
    fun visitJunction(junction: Junction)
    fun visitLever(lever: Lever)
    fun visitLeverContact(leverContact: LeverContact)
    fun visitLamp(lamp: Lamp)
    fun visitRelayChangeoverContact(contact: RelayChangeoverContact)
    fun visitResistor(resistor: Resistor)
    fun visitBistableRelay(relay: BistableRelay)
    fun visitTimedBlock(block: TimedBlock)
}