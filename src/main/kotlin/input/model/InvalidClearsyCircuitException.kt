package input.model

class InvalidClearsyCircuitException(circuit: String): Exception("Invalid circuit: $circuit. Check its XML and try again.")