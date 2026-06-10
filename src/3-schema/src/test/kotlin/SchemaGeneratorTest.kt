import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

data class SimplePerson(
    val name: String,
    val age: Int,
    val email: String?
)

data class Address(
    val city: String,
    val zip: Int
)

data class NestedPerson(
    val name: String,
    val address: Address
)

data class EmptyClass(val placeholder: String? = null)

data class WithBoolean(val active: Boolean, val count: Int)

data class WithAllTypes(
    val text: String,
    val num: Int,
    val bigNum: Long,
    val decimal: Double,
    val fraction: Float,
    val flag: Boolean
)

class SchemaGeneratorTest {
    private val generator = SchemaGenerator()

    @Test
    fun testSimpleSchema() {
        val schema = generator.generate<SimplePerson>()
        assertTrue(schema.contains("\"type\": \"string\""), "Should contain string type")
        assertTrue(schema.contains("\"type\": \"integer\""), "Should contain integer type")
        assertTrue(schema.contains("\"name\""), "Should contain name property")
        assertTrue(schema.contains("\"age\""), "Should contain age property")
        assertTrue(schema.contains("\"required\":"), "Should have required field")
        assertTrue(schema.contains("\"name\"") && schema.contains("\"age\""), "Required should include name and age")
    }

    @Test
    fun testNullableExcludedFromRequired() {
        val schema = generator.generate<SimplePerson>()
        // email is nullable, so it should NOT appear in required
        val requiredSection = schema.substringAfter("\"required\":").substringBefore("]")
        assertTrue(!requiredSection.contains("\"email\""), "Nullable email should not be in required")
    }

    @Test
    fun testArraySchema() {
        data class CollectionWrapper(val items: List<String>)

        val schema = generator.generate<CollectionWrapper>()
        assertTrue(schema.contains("\"type\": \"array\""), "Should contain array type")
        assertTrue(schema.contains("\"items\": { \"type\": \"string\" }"), "Items should be string")
    }

    @Test
    fun testComplexNestedSchema() {
        data class User(val id: Int, val name: String)
        data class Root(val users: List<User>)

        val schema = generator.generate<Root>()
        assertTrue(schema.contains("\"type\": \"array\""))
        assertTrue(schema.contains("\"users\": {"))
        assertTrue(schema.contains("\"id\""))
    }

    @Test
    fun testNestedObjectSchema() {
        val schema = generator.generate<NestedPerson>()
        assertTrue(schema.contains("\"city\""), "Should contain nested city field")
        assertTrue(schema.contains("\"zip\""), "Should contain nested zip field")
        assertTrue(schema.contains("\"address\": {"))
        assertTrue(schema.contains("\"schema\":"))
    }

    @Test
    fun testEmptyClass() {
        val schema = generator.generate<EmptyClass>()
        assertTrue(schema.contains("\"type\": \"object\""))
        assertTrue(schema.contains("\"properties\": {"))
    }

    @Test
    fun testBooleanType() {
        val schema = generator.generate<WithBoolean>()
        assertTrue(schema.contains("\"type\": \"boolean\""))
        assertTrue(schema.contains("\"type\": \"integer\""))
    }

    @Test
    fun testAllTypes() {
        val schema = generator.generate<WithAllTypes>()
        assertTrue(schema.contains("\"type\": \"string\""))
        assertTrue(schema.contains("\"type\": \"integer\""))
        assertTrue(schema.contains("\"type\": \"number\""))
        assertTrue(schema.contains("\"type\": \"boolean\""))
    }

    @Test
    fun testSetSupport() {
        data class SetWrapper(val items: Set<Int>)
        val schema = generator.generate<SetWrapper>()
        assertTrue(schema.contains("\"type\": \"array\""), "Set should map to array")
        assertTrue(schema.contains("\"type\": \"integer\""), "Items should be integer")
    }

    @Test
    fun testMapSupport() {
        data class MapWrapper(val data: Map<String, Int>)
        val schema = generator.generate<MapWrapper>()
        assertTrue(schema.contains("\"type\": \"object\""), "Map should map to object")
    }

    @Test
    fun testGenerateForClass() {
        val schema = generator.generateForClass(SimplePerson::class)
        assertTrue(schema.contains("\"name\""))
        assertTrue(schema.contains("\"age\""))
    }

    @Test
    fun testSchemaIsValidJson() {
        val schema = generator.generate<SimplePerson>()
        // Basic structural checks
        assertTrue(schema.startsWith("{"))
        assertTrue(schema.endsWith("}"))
        assertTrue(schema.contains("\"type\": \"object\""))
        assertTrue(schema.contains("\"properties\""))
    }
}
