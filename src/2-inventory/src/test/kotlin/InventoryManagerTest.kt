import kotlin.test.*

class InventoryManagerTest {
    private lateinit var manager: InventoryManager

    @BeforeTest
    fun setup() {
        manager = InventoryManager()
    }

    @Test
    fun testAddItem() {
        val item = manager.addItem("Widget", 10, 19.99, "Electronics")
        assertEquals(1, item.id)
        assertEquals(1, manager.getAllItems().size)
    }

    @Test
    fun testNullSafeSearch() {
        manager.addItem("Apple", 5, 0.5, "Fruit")
        val found = manager.findItemById(99)
        assertNull(found, "Should return null for non-existent ID")
        
        val foundName = manager.findItemsByName("NonExistent")
        assertTrue(foundName.isEmpty())
    }

    @Test
    fun testUpdateWithCopy() {
        val item = manager.addItem("Gadget", 1, 100.0, null)
        val success = manager.updateQuantity(item.id, 5)
        
        assertTrue(success)
        val updated = manager.findItemById(item.id)
        assertEquals(5, updated?.quantity)
        // Verify other fields remain the same
        assertEquals(100.0, updated?.price)
        assertNull(updated?.category)
    }
}
