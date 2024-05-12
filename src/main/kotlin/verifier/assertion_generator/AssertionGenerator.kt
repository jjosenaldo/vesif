package org.example.verifier.assertion_generator

import org.example.core.model.Circuit
import org.example.verifier.model.AssertionDefinition

interface AssertionGenerator {
    fun generateAssertions(circuit: Circuit): List<AssertionDefinition>
}