package org.fedoraproject.javapackages.validator.spi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Mikolaj Izdebski
 */
class LogEntryTest {

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
