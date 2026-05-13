data class InventoryItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val price: Double,
    val category: String? = null // Demonstrating nullable type
)
