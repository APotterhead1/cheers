// Craig Foulkrod
// 06082026-06302026

/*

    Copyright (c) 2026 Craig Foulkrod
    
    License under the MIT License
    See LICENSE file the project root for full license information
    
 */

package me.apotterhead.cheers.vars;

/**
 * {@code SerialPrimitive} is a class that represents a variable that is a primitive datatype.
 *<p>
 * {@code SerialObjectVariable} implements {@link SerialVariable} and helps act as
 * an intermediary between the variables in the orignal {@code Object} and the
 * serialzied String.
 * <p>
 * For the purpose of this library, {@code String} is considered a primitive datatype desipite
 * its internal implementation. Similarly, any {@code null} value that exists is treated as a
 * primitive type.
 *
 * @see SerialObjectVariable
 * @since 1.0.0
 */
public class SerialPrimitive implements SerialVariable {
    private final String name;
    private final String value;
    private final SerialPrimitiveType type;
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#BOOLEAN} with the provided
     * {@code name} and {@code String} representation of {@code value}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the boolean value of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name, boolean value ) {
        this.name = name;
        this.value = Boolean.toString( value );
        type = SerialPrimitiveType.BOOLEAN;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#BYTE} with the provided
     * {@code name} and {@code String} representation of {@code value}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the byte value of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name, byte value ) {
        this.name = name;
        this.value = Byte.toString( value );
        type = SerialPrimitiveType.BYTE;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#SHORT} with the provided
     * {@code name} and {@code String} representation of {@code value}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the short value of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name, short value ) {
        this.name = name;
        this.value = Short.toString( value );
        type = SerialPrimitiveType.SHORT;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#INT} with the provided
     * {@code name} and {@code String} representation of {@code value}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the int value of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name, int value ) {
        this.name = name;
        this.value = Integer.toString( value );
        type = SerialPrimitiveType.INT;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#LONG} with the provided
     * {@code name} and {@code String} representation of {@code value}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the long value of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name, long value ) {
        this.name = name;
        this.value = Long.toString( value );
        type = SerialPrimitiveType.LONG;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#FLOAT} with the provided
     * {@code name} and {@code String} representation of {@code value}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the float value of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name, float value ) {
        this.name = name;
        this.value = Float.toString( value );
        type = SerialPrimitiveType.FLOAT;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#DOUBLE} with the provided
     * {@code name} and {@code String} representation of {@code value}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the double value of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name, double value ) {
        this.name = name;
        this.value = Double.toString( value );
        type = SerialPrimitiveType.DOUBLE;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#CHAR} with the provided
     * {@code name} and {@code String} representation of {@code value}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the char value of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name, char value ) {
        this.name = name;
        this.value = Integer.toString( value );
        type = SerialPrimitiveType.CHAR;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#STRING} with the provided
     * {@code name} and {@code String} representation of {@code value}.
     * <p>
     * The {@code String} provided for {@code value} will be encoded with
     * the following substitutions for common escape characters:
     * {@code \\, \n, \t, \", \r, \b, \f }
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the {@code String} value of the variable in the {@code Object}
     *              being serialized
     */
    public SerialPrimitive( String name, String value) {
        this.name = name;
        this.value = value.replace( "\\", "\\\\" ).replace( "\n", "\\n" ).replace( "\t", "\\t" ).replace( "\"", "\\\"" ).replace( "\r", "\\r" ).replace( "\b", "\\b" ).replace( "\f", "\\f" );
        this.type = SerialPrimitiveType.STRING;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} where
     * {@code type=}{@link SerialPrimitiveType#NULL} with the provided
     * {@code name} and {@code value} of {@code "null"}.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name ) {
        this.name = name;
        this.value = "null";
        this.type = SerialPrimitiveType.NULL;
    }
    
    /**
     * Constructs a new {@code SerialPrimitive} with the provided
     * {@code name}, {@code value} and {@code type}. There is zero type validation
     * when this constructor is used, and it is meant only for internal use.
     *
     * @param name the name of the variable in the {@code Object} being serialized
     * @param value the value of the variable in the {@code Object} being serialized.
     *              Although it is a {@code String}, it does not go through the same
     *              process of encoding as {@link #SerialPrimitive(String, String)}
     *              and is ment for raw data.
     * @param type the type of the variable in the {@code Object} being serialized
     */
    public SerialPrimitive( String name, String value, SerialPrimitiveType type ) {
        this.name = name;
        this.value = value;
        this.type = type;
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
     * Returns a {@code SerialPrimitiveType} representing the datatype of the variable.
     *
     * @return a {@code SerialPrimitiveType} representing the datatype of the variable
     */
    public SerialPrimitiveType getType() {
        return type;
    }
    
    /**
     * Returns the value of the variable as a {@code boolean}. This method does
     * not contain any type validation and will throw an error if there is a
     * datatype mismatch.
     *
     * @return the value of the variable as a {@code boolean}
     */
    public boolean getBoolean() {
        return Boolean.parseBoolean( value );
    }
    
    /**
     * Returns the value of the variable as a {@code byte}. This method does
     * not contain any type validation and will throw an error if there is a
     * datatype mismatch.
     *
     * @return the value of the variable as a {@code byte}
     */
    public byte getByte() {
        return Byte.parseByte( value );
    }
    
    /**
     * Returns the value of the variable as a {@code short}. This method does
     * not contain any type validation and will throw an error if there is a
     * datatype mismatch.
     *
     * @return the value of the variable as a {@code short}
     */
    public short getShort() {
        return Short.parseShort( value );
    }
    
    /**
     * Returns the value of the variable as an {@code int}. This method does
     * not contain any type validation and will throw an error if there is a
     * datatype mismatch.
     *
     * @return the value of the variable as an {@code int}
     */
    public int getInt() {
        return Integer.parseInt( value );
    }
    
    /**
     * Returns the value of the variable as a {@code long}. This method does
     * not contain any type validation and will throw an error if there is a
     * datatype mismatch.
     *
     * @return the value of the variable as a {@code long}
     */
    public long getLong() {
        return Long.parseLong( value );
    }
    
    /**
     * Returns the value of the variable as a {@code float}. This method does
     * not contain any type validation and will throw an error if there is a
     * datatype mismatch.
     *
     * @return the value of the variable as a {@code float}
     */
    public float getFloat() {
        return Float.parseFloat( value );
    }
    
    /**
     * Returns the value of the variable as a {@code double}. This method does
     * not contain any type validation and will throw an error if there is a
     * datatype mismatch.
     *
     * @return the value of the variable as a {@code double}
     */
    public double getDouble() {
        return Double.parseDouble( value );
    }
    
    /**
     * Returns the value of the variable as a {@code char}. This method does
     * not contain any type validation and will throw an error if there is a
     * datatype mismatch.
     *
     * @return the value of the variable as a {@code char}
     */
    public char getChar() {
        return (char) Integer.parseInt( value );
    }
    
    /**
     * Returns the value of the variable as a {@code String}.
     * <p>
     * The {@code String} returned by this method is decoded with the
     * following substitutions for common escape characters:
     * {@code \\, \n, \t, \", \r, \b, \f }
     *
     * @return the value of the variable as a {@code Stirng}
     */
    public String getString() {
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < value.length(); i++ ) {
            if( value.charAt( i ) != '\\' ) {
                sb.append( value.charAt( i ) );
                continue;
            }
            i++;
            if( i == value.length() ) continue;
            
            switch( value.charAt( i ) ) {
                case 'n' -> sb.append( '\n' );
                case 't' -> sb.append( '\t' );
                case '"' -> sb.append( '\"' );
                case '\\' -> sb.append( '\\' );
                case 'r' -> sb.append( '\r' );
                case 'b' -> sb.append( '\b' );
                case 'f' -> sb.append( '\f' );
                default -> {}
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Returns {@code value} as a {@code String}. This method does not perform any
     * modifications on {@code value} as its stored internally, and may not trully
     * the variable it is supposed to.
     *
     * @return the raw {@code value} of the variable as a {@code String}
     */
    public String getRawValue() {
        return value;
    }
    
    /**
     * {@inheritDoc}
     *
     * For the case of {@code SerialPrimitive}, {@code "TYPE"} is determined
     * by calling {@link SerialPrimitiveType#name()}
     *
     * @return {@inheritDoc}}
     */
    public String toString() {
        return name + ":" + type.name() + ":\"" + value + "\"";
    }
    
    /**
     * {@code SerialPrimitiveType} is an enum that represents the datatype of a
     * primitive variable. This determins the {@code "TYPE"} that is speciefied
     * during the serialization process and allows
     * {@link me.apotterhead.cheers.Deserializer#deserialize} to know what datatype
     * it should expect to be stored.
     * <p>
     * For the purpose of this library, {@code String} is considered a primitive
     * datatype desipite its internal implementation. Similarly, any {@code null}
     * value that exists is treated as a primitive type.
     */
    public enum SerialPrimitiveType {
        /**
         * A {@code SerialPrimitiveType} to represent a variable with a
         * {@code boolean} datatype.
         */
        BOOLEAN,
        /**
         * A {@code SerialPrimitiveType} to represent a variable with a
         * {@code byte} datatype.
         */
        BYTE,
        /**
         * A {@code SerialPrimitiveType} to represent a variable with a
         * {@code short} datatype.
         */
        SHORT,
        /**
         * A {@code SerialPrimitiveType} to represent a variable with an
         * {@code int} datatype.
         */
        INT,
        /**
         * A {@code SerialPrimitiveType} to represent a variable with a
         * {@code long} datatype.
         */
        LONG,
        /**
         * A {@code SerialPrimitiveType} to represent a variable with a
         * {@code float} datatype.
         */
        FLOAT,
        /**
         * A {@code SerialPrimitiveType} to represent a variable with a
         * {@code double} datatype.
         */
        DOUBLE,
        /**
         * A {@code SerialPrimitiveType} to represent a variable with a
         * {@code char} datatype.
         */
        CHAR,
        /**
         * A {@code SerialPrimitiveType} to represent a variable with a
         * {@code String} datatype.
         */
        STRING,
        /**
         * A {@code SerialPrimitiveType} to represent a variable whose value
         * is {@code null}.
         */
        NULL
    }
}
