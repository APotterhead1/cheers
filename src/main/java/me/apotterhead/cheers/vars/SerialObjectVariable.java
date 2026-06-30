// Craig Foulkrod
// 06092026-06282026

package me.apotterhead.cheers.vars;

/**
 * {@code SerialObjectVariable} is a class that represents a variable that points
 * to an {@code Object}.
 * <p>
 * {@code SerialObjectVariable} implements {@link SerialVariable} and helps act as
 * an intermediary between the variables in the orignal {@code Object} and the
 * serialzied String.
 * <p>
 * Each {@code SerialObjectVariable} contains a name representing the variable and
 * a UUID representing the object the variable points at.
 *
 * @see SerialPrimitive
 * @since 1.0.0
 */
public class SerialObjectVariable implements SerialVariable {
    private final String name;
    private final String uuid;
    
    /**
     * Constructs a new {@code SerialObjectVariable} with the provided {@code name} and {@code uuid}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param uuid a UUID representing the {@code Object} that the variable is pointing at
     */
    public SerialObjectVariable( String name, String uuid ) {
        this.name = name;
        this.uuid = uuid;
    }
    
    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns a {@code String} representing the UUID of the {@code Object} that the
     * variable is pointing at. The UUID should match the UUID contained inside a
     * {@link SerialObject} that represents the associated {@code Object}.
     *
     * @return a {@code String} representing the UUID of the {@code Object} that the
     * variable is pointing at
     */
    public String getUUID() {
        return uuid;
    }
    
    /**
     * {@inheritDoc}
     *
     * Every {@code SerialObjectVariable} has a type of {@code "OBJECT"}.
     *
     * @return {@inheritDoc}
     */
    public String toString() {
        return name + ":OBJECT:\"" + uuid + "\"";
    }
}
