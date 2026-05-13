import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

class SchemaGenerator {

    inline fun <reified T : Any> generate(): String {
        return generateForClass(T::class)
    }

    fun generateForClass(kClass: KClass<*>): String {
        val properties = kClass.memberProperties
        val sb = StringBuilder()
        sb.append("{\n")
        sb.append("  \"type\": \"object\",\n")
        sb.append("  \"properties\": {\n")

        val propStrings = properties.map { prop ->
            val type = prop.returnType
            val jsonType = mapToJsonType(type.jvmErasure)
            val nullable = type.isMarkedNullable
            
            val entry = StringBuilder()
            entry.append("    \"${prop.name}\": {\n")
            if (jsonType == "object") {
                // Recursive call for nested data classes
                val nested = generateForClass(type.jvmErasure)
                    .split("\n")
                    .joinToString("\n      ")
                entry.append("      \"schema\": $nested\n")
            } else {
                entry.append("      \"type\": \"$jsonType\"\n")
            }
            entry.append("    }")
            entry.toString()
        }

        sb.append(propStrings.joinToString(",\n"))
        sb.append("\n  },\n")
        
        val required = properties.filter { !it.returnType.isMarkedNullable }.map { it.name }
        sb.append("  \"required\": [${required.joinToString { "\"$it\"" }}]\n")
        sb.append("}")
        
        return sb.toString()
    }

    private fun mapToJsonType(kClass: KClass<*>): String {
        return when (kClass) {
            String::class -> "string"
            Int::class, Long::class -> "integer"
            Double::class, Float::class -> "number"
            Boolean::class -> "boolean"
            else -> if (kClass.isData) "object" else "any"
        }
    }
}
