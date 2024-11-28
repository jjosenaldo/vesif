package ui.screens.main.sub_screens.assertions.model

sealed class AssertionData {
    open fun onSelected(selected: Boolean) {}
    open fun isValid(): Boolean = true
}

data object EmptyAssertionData : AssertionData()

abstract class MultiselectAssertionData<T>(keys: List<String>, val values: List<T>, private val defaultValue: T) :
    AssertionData() {
    val pendingKeys: List<String>
        get() = _pendingKeys.toList()
    private val _pendingKeys = keys.toSortedSet()
    val selectedData: List<Pair<String, T>>
        get() = _selectedData
    private val _selectedData = mutableListOf<Pair<String, T>>()

    fun addRow() {
        val newKey = _pendingKeys.firstOrNull() ?: return
        _selectedData.add(Pair(newKey, defaultValue))
        _pendingKeys.removeFirst()
    }

    fun removeRow(key: String) {
        _pendingKeys.add(key)
        _selectedData.removeIf { pair -> pair.first == key }
    }

    fun editKey(oldKey: String, newKey: String) {
        val position = indexOfKey(oldKey) ?: return
        _selectedData[position] = Pair(newKey, _selectedData[position].second)
        _pendingKeys.remove(newKey)
        _pendingKeys.add(oldKey)
    }

    fun editValue(key: String, newValue: T) {
        val position = indexOfKey(key) ?: return
        _selectedData[position] = Pair(key, newValue)
    }

    fun canAddRow() = _pendingKeys.isNotEmpty()

    abstract fun getValueInfo(value: T): AssertionDataValueInfo

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

    private fun indexOfKey(key: String): Int? {
        val index = _selectedData.indexOfFirst { pair -> pair.first == key }
        return if (index == -1) null else index
    }
}