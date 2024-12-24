package ui.screens.main.sub_screens.assertions.model

import core.model.Identifiable

abstract class ComponentAssertionData<T : Identifiable, V>(
    components: List<T>,
    values: List<V>,
    defaultValue: V,
) : MultiselectAssertionData<V>(
    defaultValue = defaultValue,
    keys = components.map { it.name },
    values = values
) {
    private val componentsByName = components.associateBy { it.name }

    fun getComponentByName(name: String) = componentsByName[name]
}