package verifier.model

abstract class AssertionRunResult(val assertion: AssertionDefinition, val passed: Boolean) {
    abstract val details: String
}