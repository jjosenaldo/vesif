package verifier.model

abstract class AssertionRunResult(val assertionType: AssertionType, val passed: Boolean) {
    abstract val details: String
}