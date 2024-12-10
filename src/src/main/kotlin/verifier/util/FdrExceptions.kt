package verifier.util

class InvalidFdrException(cause: Throwable) : Exception(cause)
class FdrNotFoundException : Exception()
class AssertionTimeoutException : Exception()