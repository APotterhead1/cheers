// Craig Foulkrod
// 06082026-06202026

package me.apotterhead.cheers.vars;

public class SerialPrimitive implements SerialVariable {
    private final String name;
    private final String value;
    private final SerialPrimitiveType type;
    
    public SerialPrimitive( String name, boolean value ) {
        this.name = name;
        this.value = Boolean.toString( value );
        type = SerialPrimitiveType.BOOLEAN;
    }
    
    public SerialPrimitive( String name, byte value ) {
        this.name = name;
        this.value = Byte.toString( value );
        type = SerialPrimitiveType.BYTE;
    }
    
    public SerialPrimitive( String name, short value ) {
        this.name = name;
        this.value = Short.toString( value );
        type = SerialPrimitiveType.SHORT;
    }
    
    public SerialPrimitive( String name, int value ) {
        this.name = name;
        this.value = Integer.toString( value );
        type = SerialPrimitiveType.INT;
    }
    
    public SerialPrimitive( String name, long value ) {
        this.name = name;
        this.value = Long.toString( value );
        type = SerialPrimitiveType.LONG;
    }
    
    public SerialPrimitive( String name, float value ) {
        this.name = name;
        this.value = Float.toString( value );
        type = SerialPrimitiveType.FLOAT;
    }
    
    public SerialPrimitive( String name, double value ) {
        this.name = name;
        this.value = Double.toString( value );
        type = SerialPrimitiveType.DOUBLE;
    }
    
    public SerialPrimitive( String name, char value ) {
        this.name = name;
        this.value = Integer.toString( value );
        type = SerialPrimitiveType.CHAR;
    }
    
    public SerialPrimitive( String name, String value) {
        this.name = name;
        this.value = value.replace( "\n", "\\n" ).replace( "\t", "\\t" ).replace( "\"", "\\\"" ).replace( "\\", "\\\\" ).replace( "\r", "\\r" ).replace( "\b", "\\b" ).replace( "\f", "\\f" );
        this.type = SerialPrimitiveType.STRING;
    }
    
    public SerialPrimitive( String name, Enum<?> value ) {
        this.name = name;
        this.value = value.name();
        this.type = SerialPrimitiveType.ENUM;
    }
    
    public SerialPrimitive( String name ) {
        this.name = name;
        this.value = "null";
        this.type = SerialPrimitiveType.NULL;
    }
    
    public String getName() {
        return name;
    }
    
    public SerialPrimitiveType getType() {
        return type;
    }
    
    public boolean getBoolean() {
        return Boolean.parseBoolean( value );
    }
    
    public byte getByte() {
        return Byte.parseByte( value );
    }
    
    public short getShort() {
        return Short.parseShort( value );
    }
    
    public int getInt() {
        return Integer.parseInt( value );
    }
    
    public long getLong() {
        return Long.parseLong( value );
    }
    
    public float getFloat() {
        return Float.parseFloat( value );
    }
    
    public double getDouble() {
        return Double.parseDouble( value );
    }
    
    public char getChar() {
        return (char) Integer.parseInt( value );
    }
    
    public String getString() {
        return value;
    }
    
    public <T extends Enum<T>> Enum<T> getEnum( Class<T> cls ) {
        return Enum.valueOf( cls, value );
    }
    
    public String toString() {
        return name + ":" + type.name() + ":\"" + value + "\"";
    }
    
    public enum SerialPrimitiveType {
        BOOLEAN,
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        CHAR,
        STRING,
        ENUM,
        NULL
    }
}
