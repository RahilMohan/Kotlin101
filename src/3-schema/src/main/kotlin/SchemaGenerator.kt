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
            val classifier = type.classifier as? KClass<*>
            val jsonType = mapToJsonType(classifier)

            val entry = StringBuilder()
            entry.append("    \"${prop.name}\": {\n")

            when (jsonType) {
                "object" -> {
                    val nested = generateForClass(classifier!!)
                        .split("\n")
                        .joinToString("\n      ")
                    entry.append("      \"schema\": $nested\n")
                }

                "array" -> {
                    entry.append("      \"type\": \"array\",\n")
                    val itemType = type.arguments.firstOrNull()?.type?.classifier as? KClass<*>
                    if (itemType != null) {
                        val itemJsonType = mapToJsonType(itemType)
                        if (itemJsonType == "object") {
                            val nested = generateForClass(itemType)
                                .split("\n")
                                .joinToString("\n        ")
                            entry.append("      \"items\": $nested\n")
                        } else {
                            entry.append("      \"items\": { \"type\": \"$itemJsonType\" }\n")
                        }
                    } else {
                        entry.append("      \"items\": { \"type\": \"any\" }\n")
                    }
                }

                else -> {
                    entry.append("      \"type\": \"$jsonType\"\n")
                }
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

    private fun mapToJsonType(kClass: KClass<*>?): String {
        if (kClass == null) return "any"
        return when {
            kClass == String::class -> "string"
            kClass == Int::class || kClass == Long::class -> "integer"
            kClass == Double::class || kClass == Float::class -> "number"
            kClass == Boolean::class -> "boolean"
            kClass.isData -> "object"
            kClass.java.isAssignableFrom(List::class.java) ||
            kClass.java.isAssignableFrom(Set::class.java) ||
            kClass.java.isAssignableFrom(Collection::class.java) -> "array"
            kClass.java.isAssignableFrom(Map::class.java) -> "object"
            else -> "any"
        }
    }
}
