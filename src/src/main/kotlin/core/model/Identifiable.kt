package core.model

interface Identifiable: Comparable<Identifiable> {
    val name: String
    val id get() = "${name}_id"
    override fun compareTo(other: Identifiable): Int {
        return  name.compareTo(other.name)
    }
}