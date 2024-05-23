package presentation.select_circuit

sealed class LoadCircuitState {
}

class LoadCircuitInitial : LoadCircuitState()
class LoadCircuitLoading : LoadCircuitState()
class LoadCircuitSuccess : LoadCircuitState()
class LoadCircuitError : LoadCircuitState()
