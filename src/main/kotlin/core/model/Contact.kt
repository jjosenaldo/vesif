package core.model

abstract class Contact(open var controller: ContactController, val hasEndpoint: Boolean, name: String) : Component(name)