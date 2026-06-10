import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.table.table

// Example classes for the CLI to "discover"
data class User(val id: Int, val username: String, val isActive: Boolean)
data class Product(val name: String, val price: Double, val tags: String? = null)
data class Order(val orderId: String, val user: User, val products: List<Product>)

fun main() {
    val t = Terminal()
    val generator = SchemaGenerator()

    t.println(brightBlue("--- Kotlin Reflection Schema Generator ---"))
    t.println("Select a class to generate a JSON Schema:")

    val options = listOf(
        "User" to User::class,
        "Product" to Product::class,
        "Order" to Order::class
    )

    while (true) {
        t.println("\nOptions:")
        options.forEachIndexed { index, pair ->
            t.println("${index + 1}. ${pair.first}")
        }
        t.println("q. Quit")

        print(magenta("> "))
        val input = readLine()?.trim()?.lowercase() ?: break

        if (input == "q") break

        val choice = input.toIntOrNull()
        if (choice != null && choice in 1..options.size) {
            val selected = options[choice - 1]
            t.println(gray("Generating schema for ${selected.first}..."))

            val schema = generator.generateForClass(selected.second)

            t.println(brightGreen("\nGenerated JSON Schema:"))
            t.println(white(schema))
        } else {
            t.println(red("Invalid choice."))
        }
    }

    t.println(brightBlue("Goodbye!"))
}
