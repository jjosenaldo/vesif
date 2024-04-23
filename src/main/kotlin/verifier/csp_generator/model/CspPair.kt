package org.example.verifier.csp_generator.model

class CspPair<A, out B>(val first: A, val second: B) {
    override fun toString(): String {
        return "($first, $second)"
    }
}