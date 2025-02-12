package ui.screens.main.sub_screens.assertions.model

sealed class AssertionData {
    open fun onSelected(selected: Boolean) {}
    open fun isValid(): Boolean = true
}

data object EmptyAssertionData : AssertionData()

abstract class MultiselectAssertionData<K: Comparable<K>, V>(keys: List<K>, val values: List<V>, private val defaultValue: V) :
    AssertionData() {
    val pendingKeys: List<K>
        get() = _pendingKeys.toList()
    private val _pendingKeys = keys.toSortedSet()
    val selectedData: List<Pair<K, V>>
        get() = _selectedData
    private val _selectedData = mutableListOf<Pair<K, V>>()

    fun addRow() {
        val newKey = _pendingKeys.firstOrNull() ?: return
        _selectedData.add(Pair(newKey, defaultValue))
        _pendingKeys.removeFirst()
    }

    fun removeRow(key: K) {
        _pendingKeys.add(key)
        _selectedData.removeIf { pair -> pair.first == key }
    }

    fun editKey(oldKey: K, newKey: K) {
        val position = indexOfKey(oldKey) ?: return
        _selectedData[position] = Pair(newKey, _selectedData[position].second)
        _pendingKeys.remove(newKey)
        _pendingKeys.add(oldKey)
    }

    fun editValue(key: K, newValue: V) {
        val position = indexOfKey(key) ?: return
        _selectedData[position] = Pair(key, newValue)
    }

    fun canAddRow() = _pendingKeys.isNotEmpty()

    abstract fun getValueInfo(key: K, value: V): AssertionDataValueInfo

    abstract fun getKeyDescription(key: K): String

    override fun onSelected(selected: Boolean) {
        if (!selected) clear()
        else addRow()
    }

    override fun isValid() = !isEmpty()

    private fun isEmpty() = _selectedData.isEmpty()

    private fun clear() {
        _pendingKeys.addAll(_selectedData.map { it.first })
        _selectedData.clear()
    }

    private fun indexOfKey(key: K): Int? {
        val index = _selectedData.indexOfFirst { pair -> pair.first == key }
        return if (index == -1) null else index
    }
}