package ui.common

sealed class UiState<T>(open val data: T?)

class UiInitial<T>(data: T? = null) : UiState<T>(data)
class UiLoading<T>(data: T? = null) : UiState<T>(data)
class UiError<T>(val message: String = "", val error: Any? = null, data: T? = null) : UiState<T>(data)
class UiSuccess<T>(override val data: T) : UiState<T>(data)
