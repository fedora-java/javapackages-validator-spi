package org.fedoraproject.javapackages.validator.spi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Mikolaj Izdebski
 */
public class LogEventTest {

    @Test
    void testEqualsAndHashCode() {
        assertEquals(LogEvent.debug, LogEvent.debug);
        assertEquals(LogEvent.skip, LogEvent.skip);
        assertEquals(LogEvent.pass, LogEvent.pass);
        assertEquals(LogEvent.info, LogEvent.info);
        assertEquals(LogEvent.warn, LogEvent.warn);
        assertEquals(LogEvent.fail, LogEvent.fail);
        assertEquals(LogEvent.error, LogEvent.error);

        assertNotEquals(LogEvent.debug, LogEvent.info);
        assertNotEquals(LogEvent.warn, LogEvent.fail);

        assertEquals(LogEvent.debug.hashCode(), LogEvent.debug.hashCode());
        assertEquals(LogEvent.error.hashCode(), LogEvent.error.hashCode());
    }
}
