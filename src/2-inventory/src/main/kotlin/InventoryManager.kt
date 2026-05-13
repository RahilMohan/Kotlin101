class InventoryManager {
    private val items = mutableListOf<InventoryItem>()
    private var nextId = 1

    fun addItem(name: String, quantity: Int, price: Double, category: String?): InventoryItem {
        val item = InventoryItem(nextId++, name, quantity, price, category)
        items.add(item)
        return item
    }

    fun getAllItems(): List<InventoryItem> = items.toList()

    fun findItemById(id: Int): InventoryItem? {
        return items.find { it.id == id }
    }

    fun findItemsByName(query: String): List<InventoryItem> {
        return items.filter { it.name.contains(query, ignoreCase = true) }
    }

    fun updateQuantity(id: Int, newQuantity: Int): Boolean {
        val item = findItemById(id) ?: return false
        val index = items.indexOf(item)
        
        // Demonstrating .copy() for immutability-aware updates
        items[index] = item.copy(quantity = newQuantity)
        return true
    }

    fun removeItem(id: Int): Boolean {
        return items.removeIf { it.id == id }
    }
}
