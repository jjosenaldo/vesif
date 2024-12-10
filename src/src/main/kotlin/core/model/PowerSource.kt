package core.model

interface PowerSource {
    val isPositiveSource: Boolean
    var neighbor: Component
}