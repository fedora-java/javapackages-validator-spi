package org.fedoraproject.javapackages.validator.spi;

import java.util.Objects;

/**
 * Class representing a decorated for displaying.
 *
 * @author Marián Konček
 */
public class Decorated {
    /**
     * Decoration used for RPM file names that are validated.
     */
    protected static final Decoration DECORATION_RPM = new Decoration(Decoration.Color.red, Decoration.Modifier.bright);

    /**
     * Decoration used for actual values found during validation.
     */
    protected static final Decoration DECORATION_ACTUAL = new Decoration(Decoration.Color.magenta, Decoration.Modifier.bright);

    /**
    * Decoration used for values expected to be found during validation.
    */
   protected static final Decoration DECORATION_EXPECTED = new Decoration(Decoration.Color.cyan, Decoration.Modifier.bright);

    /**
     * Decoration used for class names.
     */
    protected static final Decoration DECORATION_STRUCT = new Decoration(Decoration.Color.yellow, Decoration.Modifier.bright);

    /**
     * Decoration used for objects which hold inspected values.
     */
    protected static final Decoration DECORATION_OUTER = new Decoration(Decoration.Color.blue, Decoration.Modifier.bright);

    private final String object;
    private final Decoration decoration;

    /**
     * Decorate an object using custom decorations.
     * @param object An object to decorate, must not be an instance of
     * {@link org.fedoraproject.javapackages.validator.spi.Decorated}.
     * Can be {@code null}.
     * @param decoration Decoration applied to {@code object}.
     * Can be {@code null}.
     * @throws java.lang.IllegalArgumentException If {@code object} is already
     * an instance of {@link org.fedoraproject.javapackages.validator.spi.Decorated}.
     */
    protected Decorated(Object object, Decoration decoration) {
        if (object instanceof Decorated) {
            throw new IllegalArgumentException("Object is already decorated");
        }
        this.object = Objects.toString(object);
        this.decoration = decoration;
    }

    /**
     * Decorate an object using custom decorations.
     * @param object An object to decorate, must not be an instance of
     * {@link org.fedoraproject.javapackages.validator.spi.Decorated}.
     * Can be {@code null}.
     * @param decoration Decorations applied to {@code object}.
     * @return A decorated instance of {@code object}.
     */
    public static Decorated custom(Object object, Decoration decoration) {
        return new Decorated(object, decoration);
    }

    /**
     * Decorate an RPM file object using unified decorating scheme.
     * @param rpm Object to decorate.
     * @return A decorated instance of {@code rpm}.
     */
    public static Decorated rpm(Object rpm) {
        return new Decorated(rpm, DECORATION_RPM);
    }

    /**
     * Decorate an object using unified decorating scheme.
     * @param actual Object to decorate. {@code actual} represents a value that
     * was encountered during validation. {@code actual} must not be an instance
     * of {@link org.fedoraproject.javapackages.validator.spi.Decorated}.
     * Can be {@code null}.
     * @return A decorated instance of {@code actual}.
     */
    public static Decorated actual(Object actual) {
        return new Decorated(actual, DECORATION_ACTUAL);
    }

    /**
     * Decorate an object using unified decorating scheme.
     * @param expected Object to decorate. {@code expected} represents a value
     * that was expected to be encountered during validation. {@code expected}
     * must not be an instance of {@link org.fedoraproject.javapackages.validator.spi.Decorated}.
     * Can be {@code null}.
     * @return A decorated instance of {@code expected}.
     */
    public static Decorated expected(Object expected) {
        return new Decorated(expected, DECORATION_EXPECTED);
    }

    /**
     * Decorate an object using unified decorating scheme.
     * @param struct Object to decorate. {@code struct} represents a class name
     * that is to be logged during validation. It is usually a string or an
     * instance of {@link java.lang.Class}. {@code struct} must not be an
     * instance of {@link org.fedoraproject.javapackages.validator.spi.Decorated}.
     * Can be {@code null}.
     * @return A decorated instance of {@code struct}.
     */
    public static Decorated struct(Object struct) {
        return new Decorated(struct, DECORATION_STRUCT);
    }

    /**
     * Decorate an object using unified decorating scheme.
     * @param outer Object to decorate. {@code struct} represents any entity
     * that contains the inspected values. This can be for example a directory
     * name or a name of an archive. {@code struct} must not be an instance of
     * {@link org.fedoraproject.javapackages.validator.spi.Decorated}.
     * Can be {@code null}.
     * @return A decorated instance of {@code outer}.
     */
    public static Decorated outer(Object outer) {
        return new Decorated(outer, DECORATION_OUTER);
    }

    /**
     * Decorate an object using no decorations.
     * @param object Object to wrap. {@code object} must not be an instance of
     * {@link org.fedoraproject.javapackages.validator.spi.Decorated}.
     * Can be {@code null}.
     * @return A decorated instance of {@code object}.
     */
    public static Decorated plain(Object object) {
        return new Decorated(object, Decoration.NONE);
    }

    /**
     * Get the inner object, on which decorations were applied.
     * @return The object with which this instance was constructed.
     */
    public Object getObject() {
        return object;
    }

    /**
     * Get a copy of the decorations of this instance.
     * @return A copy of the decorations of this instance.
     */
    public Decoration getDecoration() {
        return decoration;
    }
}
