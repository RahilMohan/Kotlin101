import kotlin.test.*

data class User(val id: Int, val name: String, val email: String?)
data class Product(val id: Int, val details: Details)
data class Details(val sku: String, val weight: Double)

class SchemaGeneratorTest {
    private val generator = SchemaGenerator()

    @Test
    fun testSimpleClass() {
        val schema = generator.generate<User>()
        assertTrue(schema.contains("\"type\": \"integer\""))
        assertTrue(schema.contains("\"type\": \"string\""))
        assertTrue(schema.contains("\"required\": [\"id\", \"name\"]"))
        assertFalse(schema.contains("\"required\": [\"id\", \"name\", \"email\"]"))
    }

    @Test
    fun testNestedClass() {
        val schema = generator.generate<Product>()
        assertTrue(schema.contains("\"details\""))
        assertTrue(schema.contains("\"sku\""))
        assertTrue(schema.contains("\"weight\""))
    }
}
