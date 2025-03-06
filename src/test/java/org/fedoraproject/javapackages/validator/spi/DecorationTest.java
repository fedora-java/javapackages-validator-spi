package org.fedoraproject.javapackages.validator.spi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.fedoraproject.javapackages.validator.spi.Decoration.Color;
import org.fedoraproject.javapackages.validator.spi.Decoration.Modifier;
import org.junit.jupiter.api.Test;

/**
 * @author Mikolaj Izdebski
 */
public class DecorationTest {

    @Test
    void testConstructorWithColorAndModifiers() {
        Decoration d = new Decoration(Color.red, Modifier.bold, Modifier.underline);
        assertEquals(Optional.of(Color.red), d.color());
        assertArrayEquals(new Modifier[] { Modifier.bold, Modifier.underline }, d.modifiers());
    }

    @Test
    void testConstructorWithOnlyModifiers() {
        Decoration d = new Decoration(Modifier.bright);
        assertEquals(Optional.empty(), d.color());
        assertArrayEquals(new Modifier[] { Modifier.bright }, d.modifiers());
    }

    @Test
    void testDefaultNoneDecoration() {
        assertEquals(Optional.empty(), Decoration.NONE.color());
        assertArrayEquals(new Modifier[] {}, Decoration.NONE.modifiers());
    }

    @Test
    void testModifiersReturnsCopy() {
        Decoration d = new Decoration(Color.blue, Modifier.bold);
        Modifier[] mods = d.modifiers();
        mods[0] = Modifier.underline;
        assertArrayEquals(new Modifier[] { Modifier.bold }, d.modifiers());
    }

    @Test
    void testEquality() {
        Decoration d1 = new Decoration(Color.green, Modifier.bold);
        Decoration d2 = new Decoration(Color.green, Modifier.bold);
        assertEquals(d1, d2);
    }

    @Test
    void testInequalityDifferentColor() {
        Decoration d1 = new Decoration(Color.yellow, Modifier.bold);
        Decoration d2 = new Decoration(Color.red, Modifier.bold);
        assertNotEquals(d1, d2);
    }

    @Test
    void testInequalityDifferentModifiers() {
        Decoration d1 = new Decoration(Color.magenta, Modifier.bold);
        Decoration d2 = new Decoration(Color.magenta, Modifier.underline);
        assertNotEquals(d1, d2);
    }

    @Test
    void testHashCodeConsistency() {
        Decoration d = new Decoration(Color.cyan, Modifier.bold);
        assertEquals(d.hashCode(), d.hashCode());
    }
}
