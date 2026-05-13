import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.toList
import kotlin.test.*

class LogParserTest {
    private val parser = LogParser()

    @Test
    fun testFlowParsing() = runTest {
        val logs = listOf("INFO: Started", "ERROR: Failed", "WARN: Slow")
        val entries = parser.parseLogs(logs).toList()
        
        assertEquals(3, entries.size)
        assertEquals(LogLevel.ERROR, entries[1].level)
    }

    @Test
    fun testChannelAggregation() = runTest {
        val logs = listOf("ERROR: 1", "INFO: 2", "ERROR: 3", "INFO: 4")
        val flow = parser.parseLogs(logs)
        val stats = parser.aggregateStats(flow)
        
        assertEquals(2, stats.errorCount)
        assertEquals(4, stats.totalProcessed)
    }
}
