package csp_generator.model

import core.model.Component
import core.model.Pole

object PathsCspGenerator {
    fun generatePaths(components: List<Component>, connections: Set<CspPair<String, String>>): Map<Int, List<String>> {
        // TODO(ft): act/deact block
        val positives =
            components.filter { it is Pole && it.isPositive }.map { it.id }
        val negatives =
            components.filter { it is Pole && !it.isPositive }.map { it.id }
        val results = mutableListOf<List<String>>()

        positives.forEach {
            generatePathsFrom(it, negatives, listOf(), setOf(), connections, results)
        }

        return results.mapIndexed { index, paths -> index to paths }.toMap()
    }

    private fun generatePathsFrom(
        current: String,
        ends: List<String>,
        currentPath: List<String>,
        visited: Set<String>,
        connections: Set<CspPair<String, String>>,
        results: MutableList<List<String>>
    ) {
        val newPath = currentPath + listOf(current)

        if (current in ends) {
            results.add(newPath)
            return
        }

        val newVisited = visited + setOf(current)

        getUnvisitedNeighbors(current, connections, visited).forEach {
            generatePathsFrom(it, ends, newPath, newVisited, connections, results)
        }
    }

    private fun getUnvisitedNeighbors(
        element: String,
        connections: Set<CspPair<String, String>>,
        visited: Set<String>
    ): List<String> {
        return connections.asSequence().filter { pair -> pair.first == element || pair.second == element }.map {
            if (it.first == element) it.second else it.first
        }.filter {
            it !in visited
        }.toSet().toList()
    }
}