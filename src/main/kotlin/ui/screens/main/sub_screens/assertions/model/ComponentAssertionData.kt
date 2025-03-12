package ui.screens.main.sub_screens.assertions.model

import core.model.Identifiable

sealed class BinaryComponentAssertionData<T : Identifiable>(
    components: List<T>,
    values: List<Boolean>,
    defaultValue: Boolean
) : ComponentAssertionData<T, Boolean>(
    components = components,
    values = values,
    defaultValue = defaultValue
)

sealed class ComponentAssertionData<T : Identifiable, V>(
    components: List<T>,
    values: List<V>,
    defaultValue: V,
) : MultiselectAssertionData<T, V>(
    defaultValue = defaultValue,
    keys = components,
    values = values
) {
    override fun getKeyDescription(key: T) = key.name
}