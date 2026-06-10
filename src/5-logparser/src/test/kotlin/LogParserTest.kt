import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LogParserTest {
    private val parser = LogParser()

    @Test
    fun testParseLineInfo() {
        val entry = parser.parseLine("[2026-05-26 10:00:00] INFO: System booting")
        assertNotNull(entry)
        assertEquals("INFO", entry.level)
        assertEquals("System booting", entry.message)
        assertEquals("2026-05-26 10:00:00", entry.timestamp)
    }

    @Test
    fun testParseLineError() {
        val line = "[2026-05-26 10:00:00] ERROR: Stack overflow"
        val entry = parser.parseLine(line)
        assertNotNull(entry)
        assertEquals("ERROR", entry.level)
        assertEquals("Stack overflow", entry.message)
    }

    @Test
    fun testParseLineWarn() {
        val entry = parser.parseLine("[2026-05-26 10:00:00] WARN: Low memory")
        assertNotNull(entry)
        assertEquals("WARN", entry.level)
    }

    @Test
    fun testInvalidLine() {
        assertNull(parser.parseLine("Invalid line format"))
        assertNull(parser.parseLine(""))
        assertNull(parser.parseLine("[2026-05-26] MISSING: Something"))
    }

    @Test
    fun testParseLineWithSpecialCharacters() {
        val entry = parser.parseLine("[2026-05-26 10:00:00] ERROR: Failed with code 404: not-found")
        assertNotNull(entry)
        assertEquals("Failed with code 404: not-found", entry.message)
    }

    @Test
    fun testAggregateStats() = runBlocking {
        val lines = listOf(
            "[2026-05-26 10:00:01] INFO: App started",
            "[2026-05-26 10:00:02] ERROR: Crash",
            "[2026-05-26 10:00:03] INFO: User logged in",
            "[2026-05-26 10:00:04] WARN: Low memory"
        ).asFlow()

        val entries = parser.parseLogs(lines)
        val stats = parser.aggregateStats(entries)

        assertEquals(2, stats["INFO"])
        assertEquals(1, stats["ERROR"])
        assertEquals(1, stats["WARN"])
    }

    @Test
    fun testEmptyFlowProducesEmptyStats() = runBlocking {
        val entries = parser.parseLogs(emptyFlow())
        val stats = parser.aggregateStats(entries)
        assertTrue(stats.isEmpty())
    }

    @Test
    fun testMixedValidAndInvalidLines() = runBlocking {
        val lines = listOf(
            "[2026-05-26 10:00:01] INFO: Valid",
            "garbage line",
            "[2026-05-26 10:00:02] WARN: Also valid",
            "",
            "[2026-05-26 10:00:03] ERROR: Another valid"
        ).asFlow()

        val entries = parser.parseLogs(lines)
        val list = entries.toList()
        assertEquals(3, list.size)
        assertEquals("INFO", list[0].level)
        assertEquals("WARN", list[1].level)
        assertEquals("ERROR", list[2].level)
    }

    @Test
    fun testAllLogLevels() = runBlocking {
        val lines = listOf(
            "[2026-05-26 10:00:01] INFO: one",
            "[2026-05-26 10:00:02] WARN: two",
            "[2026-05-26 10:00:03] ERROR: three"
        ).asFlow()

        val stats = parser.aggregateStats(parser.parseLogs(lines))
        assertEquals(3, stats.size)
    }

    @Test
    fun testSingleEntryStats() = runBlocking {
        val lines = listOf("[2026-05-26 10:00:01] ERROR: solo").asFlow()
        val stats = parser.aggregateStats(parser.parseLogs(lines))
        assertEquals(1, stats.size)
        assertEquals(1, stats["ERROR"])
    }
}
