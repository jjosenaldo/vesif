package core.model

import core.model.visitor.ComponentVisitor

class MonostableSimpleContact(
    // isNormallyOpen if the controller is monostable,
    // isLeftOpen if it is bistable
    val isNormallyOpen: Boolean,
    var leftNeighbor: Component = DEFAULT,
    var rightNeighbor: Component = DEFAULT,
    // TODO: perhaps create RelayContactController so that a Lever can't be inserted here
    controller: ContactController = ContactController.DEFAULT,
    name: String,
) : Contact(controller, name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitMonostableSimpleContact(this)
    }
}
