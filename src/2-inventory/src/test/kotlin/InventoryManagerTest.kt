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
        assertEquals("Widget", item.name)
        assertEquals(10, item.quantity)
        assertEquals(19.99, item.price)
        assertEquals("Electronics", item.category)
        assertEquals(1, manager.getAllItems().size)
    }

    @Test
    fun testAddItemWithNullCategory() {
        val item = manager.addItem("Mystery Box", 1, 99.99, null)
        assertNull(item.category)
        assertEquals("Mystery Box", item.name)
    }

    @Test
    fun testAutoIncrementId() {
        val item1 = manager.addItem("A", 1, 1.0, null)
        val item2 = manager.addItem("B", 2, 2.0, null)
        val item3 = manager.addItem("C", 3, 3.0, null)
        assertEquals(1, item1.id)
        assertEquals(2, item2.id)
        assertEquals(3, item3.id)
    }

    @Test
    fun testGetAllItems() {
        manager.addItem("A", 1, 1.0, null)
        manager.addItem("B", 2, 2.0, null)
        val items = manager.getAllItems()
        assertEquals(2, items.size)
    }

    @Test
    fun testFindItemById() {
        val item = manager.addItem("Target", 5, 10.0, "Test")
        val found = manager.findItemById(item.id)
        assertNotNull(found)
        assertEquals("Target", found.name)
    }

    @Test
    fun testFindItemByIdNotFound() {
        val found = manager.findItemById(99)
        assertNull(found)
    }

    @Test
    fun testFindItemsByNameCaseInsensitive() {
        manager.addItem("MacBook Pro", 1, 2499.0, "Electronics")
        manager.addItem("macbook air", 2, 999.0, "Electronics")
        manager.addItem("iPad", 3, 599.0, "Electronics")

        val results = manager.findItemsByName("macbook")
        assertEquals(2, results.size)
    }

    @Test
    fun testFindItemsByNameNoMatch() {
        manager.addItem("Widget", 1, 1.0, null)
        val results = manager.findItemsByName("NonExistent")
        assertTrue(results.isEmpty())
    }

    @Test
    fun testUpdateQuantity() {
        val item = manager.addItem("Gadget", 1, 100.0, null)
        val success = manager.updateQuantity(item.id, 5)
        assertTrue(success)
        val updated = manager.findItemById(item.id)
        assertEquals(5, updated?.quantity)
        assertEquals(100.0, updated?.price)
        assertNull(updated?.category)
    }

    @Test
    fun testUpdateQuantityNonExistent() {
        val success = manager.updateQuantity(99, 10)
        assertFalse(success)
    }

    @Test
    fun testRemoveItem() {
        val item = manager.addItem("ToDelete", 1, 1.0, null)
        assertEquals(1, manager.getAllItems().size)
        assertTrue(manager.removeItem(item.id))
        assertTrue(manager.getAllItems().isEmpty())
    }

    @Test
    fun testRemoveItemNonExistent() {
        assertFalse(manager.removeItem(99))
    }

    @Test
    fun testUpdatePreservesOtherFields() {
        val item = manager.addItem("Original", 10, 49.99, "Category")
        manager.updateQuantity(item.id, 20)
        val updated = manager.findItemById(item.id)
        assertNotNull(updated)
        assertEquals("Original", updated.name)
        assertEquals(49.99, updated.price)
        assertEquals("Category", updated.category)
        assertEquals(20, updated.quantity)
    }
}
