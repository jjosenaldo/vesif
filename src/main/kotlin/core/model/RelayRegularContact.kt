package core.model

import core.model.visitor.ComponentVisitor

class RelayRegularContact(
    // isNormallyOpen if the controller is monostable,
    // isLeftOpen if it is bistable
    val isNormallyOpenOrIsLeftOpen: Boolean,
    var leftNeighbor: Component = DEFAULT,
    var rightNeighbor: Component = DEFAULT,
    // TODO: perhaps create RelayContactController so that a Lever can't be inserted here
    controller: ContactController = ContactController.DEFAULT,
    name: String,
) : Contact(controller, name) {
    override fun acceptVisitor(visitor: ComponentVisitor) {
        visitor.visitRelayRegularContact(this)
    }
}
