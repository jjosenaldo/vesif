package core.model

interface Identifiable {
    val name: String
    val id get() = "${name}_id"
}