package core.model

import core.model.visitor.ComponentVisitor

class TimedBlock(
    name: String,
    val isActivation: Boolean,
    val initialTime: Int,
    val maxTime: Int,
    var posSource: Pole = Pole.DEFAULT,
    var negSource: Pole = Pole.DEFAULT,
    var dependentPos: Component = DEFAULT,
    var dependentNeg: Component = DEFAULT,
    var independentUp: Component = DEFAULT,
    var independentDown: Component = DEFAULT
) : Component(name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitTimedBlock(this)
    }
}