package verifier.model.common

enum class AssertionType(val assertionName: String) {
    RingBell("Ring-bell"),
    ShortCircuit("Short-circuit"),
    Deadlock("Deadlock"),
    Divergence("Divergence"),
    Determinism("Determinism"),
    RelayStatus("Relay status"),
    ContactStatus("Contact status"),
    OutputStatus("Output status"),
}