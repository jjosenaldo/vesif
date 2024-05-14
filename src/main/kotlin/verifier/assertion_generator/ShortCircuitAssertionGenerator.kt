package org.example.verifier.assertion_generator

import org.example.core.model.Circuit
import org.example.verifier.model.AssertionDefinition
import org.example.verifier.model.ShortCircuitAssertion

class ShortCircuitAssertionGenerator: AssertionGenerator {
    override fun generateAssertions(circuit: Circuit): List<AssertionDefinition> {
        return listOf(ShortCircuitAssertion())
    }
}