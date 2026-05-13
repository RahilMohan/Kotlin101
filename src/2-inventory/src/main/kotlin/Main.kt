import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.table.table

fun main() {
    val t = Terminal()
    val manager = InventoryManager()

    // Add some initial data
    manager.addItem("MacBook Pro", 5, 2499.0, "Electronics")
    manager.addItem("Pixel Watch 3", 12, 349.0, "Wearables")
    manager.addItem("Coffee Mug", 50, 15.0, null)

    t.println(brightBlue("--- Inventory CLI Manager ---"))

    while (true) {
        val items = manager.getAllItems()
        
        t.println(table {
            header { row(cyan("ID"), cyan("Name"), cyan("Qty"), cyan("Price"), cyan("Category")) }
            body {
                items.forEach { item ->
                    row(
                        item.id.toString(),
                        item.name,
                        if (item.quantity < 10) red(item.quantity.toString()) else green(item.quantity.toString()),
                        yellow("$%.2f".format(item.price)),
                        blue(item.category ?: "N/A") // Safe handling of nullable category
                    )
                }
            }
        })

        t.println("\nCommands: [a]dd, [u]pdate, [r]emove, [s]earch, [q]uit")
        print(magenta("> "))
        val cmd = readLine()?.trim()?.lowercase() ?: break

        when (cmd) {
            "q", "quit" -> break
            "a", "add" -> {
                print("Name: ")
                val name = readLine() ?: ""
                print("Quantity: ")
                val qty = readLine()?.toIntOrNull() ?: 0
                print("Price: ")
                val price = readLine()?.toDoubleOrNull() ?: 0.0
                print("Category (optional): ")
                val cat = readLine()?.let { if (it.isBlank()) null else it }
                
                manager.addItem(name, qty, price, cat)
                t.println(green("Item added successfully."))
            }
            "u", "update" -> {
                print("Enter Item ID to update quantity: ")
                val id = readLine()?.toIntOrNull() ?: -1
                print("New Quantity: ")
                val qty = readLine()?.toIntOrNull() ?: 0
                if (manager.updateQuantity(id, qty)) {
                    t.println(green("Updated ID $id."))
                } else {
                    t.println(red("Error: ID not found."))
                }
            }
            "r", "remove" -> {
                print("Enter Item ID to remove: ")
                val id = readLine()?.toIntOrNull() ?: -1
                if (manager.removeItem(id)) {
                    t.println(yellow("Removed ID $id."))
                } else {
                    t.println(red("Error: ID not found."))
                }
            }
            "s", "search" -> {
                print("Search term: ")
                val query = readLine() ?: ""
                val results = manager.findItemsByName(query)
                if (results.isEmpty()) {
                    t.println(yellow("No items matching '$query'"))
                } else {
                    t.println(table {
                        header { row(cyan("ID"), cyan("Name"), cyan("Qty")) }
                        body {
                            results.forEach { row(it.id.toString(), it.name, it.quantity.toString()) }
                        }
                    })
                }
            }
        }
    }
    t.println(brightBlue("Goodbye!"))
}
