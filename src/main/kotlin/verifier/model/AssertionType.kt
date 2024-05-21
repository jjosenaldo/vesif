package verifier.model

enum class AssertionType(val assertionName: String) {
    RingBell("Ring-bell"),
    ShortCircuit("Short-circuit"),
    Deadlock("Deadlock"),
    Divergence("Divergence"),
    Determinism("Determinism")
}