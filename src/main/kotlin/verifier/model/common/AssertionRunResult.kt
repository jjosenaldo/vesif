package verifier.model.common

abstract class AssertionRunResult(val assertionType: AssertionType, val passed: Boolean) {
    abstract fun hasDetails(): Boolean
}