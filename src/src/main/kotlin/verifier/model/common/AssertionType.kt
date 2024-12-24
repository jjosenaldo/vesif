package verifier.model.common

enum class AssertionType(val assertionName: String) {
    RingBell("Ring-bell"),
    ShortCircuit("Short-circuit"),
    Deadlock("Deadlock"),
    Divergence("Divergence"),
    Determinism("Determinism"),
    ContactStatus("Contact status"),
    OutputStatus("Output status"),
}