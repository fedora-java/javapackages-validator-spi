package org.fedoraproject.javapackages.validator.spi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Mikolaj Izdebski
 */
class LogEntryTest {

    @Test
    void testConstructor() {
        Decorated obj1 = Decorated.rpm("foo-bar");
        Decorated obj2 = Decorated.struct("struct {}");
        LogEntry log = new LogEntry(LogEvent.info, "Test pattern {0} {1}", obj1, obj2);

        assertEquals(LogEvent.info, log.kind());
        assertEquals("Test pattern {0} {1}", log.pattern());
        assertArrayEquals(new Decorated[] { obj1, obj2 }, log.objects());
    }

    @Test
    void testObjectsReturnsCopy() {
        Decorated obj = Decorated.outer("foo");
        LogEntry log = new LogEntry(LogEvent.warn, "Test pattern {0}...", obj);
        log.objects()[0] = Decorated.actual("ACT");
        assertArrayEquals(new Decorated[] { obj }, log.objects());
    }

    @Test
    void testFactoryMethods() {
        Decorated obj = Decorated.outer("lorem ipsum....");
        assertEquals(new LogEntry(LogEvent.debug, "Pattern {0}...", obj), LogEntry.debug("Pattern {0}...", obj));
        assertEquals(new LogEntry(LogEvent.skip, "Pattern {0}...", obj), LogEntry.skip("Pattern {0}...", obj));
        assertEquals(new LogEntry(LogEvent.pass, "Pattern {0}...", obj), LogEntry.pass("Pattern {0}...", obj));
        assertEquals(new LogEntry(LogEvent.info, "Pattern {0}...", obj), LogEntry.info("Pattern {0}...", obj));
        assertEquals(new LogEntry(LogEvent.warn, "Pattern {0}...", obj), LogEntry.warn("Pattern {0}...", obj));
        assertEquals(new LogEntry(LogEvent.fail, "Pattern {0}...", obj), LogEntry.fail("Pattern {0}...", obj));
        assertEquals(new LogEntry(LogEvent.error, "Pattern {0}...", obj), LogEntry.error("Pattern {0}...", obj));
    }

    @Test
    void testEquality() {
        LogEntry log1 = new LogEntry(LogEvent.pass, "Same pattern {0}...", Decorated.outer("foo"));
        LogEntry log2 = new LogEntry(LogEvent.pass, "Same pattern {0}...", Decorated.outer("foo"));
        assertEquals(log1, log2);
    }

    @Test
    void testInequalityDifferentKind() {
        LogEntry log1 = new LogEntry(LogEvent.pass, "Pattern {0}...", Decorated.outer("foo"));
        LogEntry log2 = new LogEntry(LogEvent.fail, "Pattern {0}...", Decorated.outer("foo"));
        assertNotEquals(log1, log2);
    }

    @Test
    void testInequalityDifferentPattern() {
        LogEntry log1 = new LogEntry(LogEvent.info, "Pattern {0}... 1", Decorated.outer("foo"));
        LogEntry log2 = new LogEntry(LogEvent.info, "Pattern {0}... 2", Decorated.outer("foo"));
        assertNotEquals(log1, log2);
    }

    @Test
    void testInequalityDifferentObjects() {
        LogEntry log1 = new LogEntry(LogEvent.warn, "Pattern {0}...", Decorated.rpm("foo-bar"));
        LogEntry log2 = new LogEntry(LogEvent.warn, "Pattern {0}...", Decorated.struct("struct {}"));
        assertNotEquals(log1, log2);
    }

    @Test
    void testHashCodeConsistency() {
        LogEntry log = new LogEntry(LogEvent.error, "Pattern {0}...", Decorated.outer("foo"));
        assertEquals(log.hashCode(), log.hashCode());
    }
}
