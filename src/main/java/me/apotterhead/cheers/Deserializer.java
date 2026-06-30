// Craig Foulkrod
// 06202026-06302026

/*

    Copyright (c) 2026 Craig Foulkrod
    
    License under the MIT License
    See LICENSE file the project root for full license information
    
 */

package me.apotterhead.cheers;

import java.util.List;
import java.util.Map;
import me.apotterhead.cheers.vars.SerialVariable;
import org.objenesis.Objenesis;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.HashMap;
import me.apotterhead.cheers.vars.SerialArray;
import me.apotterhead.cheers.vars.SerialObject;
import me.apotterhead.cheers.vars.SerialObjectVariable;
import me.apotterhead.cheers.vars.SerialPrimitive;
import me.apotterhead.cheers.vars.SerialPrimitive.SerialPrimitiveType;
import org.objenesis.ObjenesisStd;

/**
 * {@code Deserializer} is a utility class that houses all the logic necessary for
 * deserialization of Strings provided by this library.
 * <p>
 * This class is not meant to be instantiated,
 * and all methods are {@code static}.
 *
 * @since 1.0.0
 */
public final class Deserializer {
    
    private Deserializer() {}
    
    /**
     * Returns the deserialized {@code Object} given a serialized
     * {@code String} and relative {@code Version}. The {@code String}
     * must be formatted correctly, normally by having it be directly outputted from
     * {@link Serializer#serialize(Object, Version)}.
     * <p>
     * To function correctly, the serialized class and all subclasses must be
     * open to reflection by this module through JVM arguments. See the README file
     * for this project for more information.
     * <p>
     * Inputting an empty {@code String} returns {@code null}.
     *
     * @param input a {@code String} representing the serialized {@code Object}.
     *              An empty {@code String} will return {@code null}>
     * @param version a {@code Version} that contains the version history of the
     *                serialized {@code Object}
     * @return an {@code Object} represented by the inputted {@code String}
     * and {@code Version} modifications
     * @throws InvalidDeserializationInputException if the input {@code String} is
     * unable to be turned into an {@code Object}
     */
    public static Object deserialize( String input, Version version ) {
        if( input == null ) throw new InvalidDeserializationInputException( "String can not be null." );
        if( input.isEmpty() ) return null;
        
        String objectVersion = getObjectVersion( input );
        List<SerialObject> serialObjects = generateSerialObjects( input );
        
        modifySerialObjects( serialObjects, version, objectVersion );
        
        Map<String, Object> objects = generateObjects( serialObjects );
        populateObjects( serialObjects, objects );
        
        return objects.get( serialObjects.getFirst().getUUID() );
    }
    
    private static String getObjectVersion( String input ) {
        int index = skipWhitespace( input, 0 );
        
        if( input.charAt( index ) != '"' ) throw new InvalidDeserializationInputException( input.substring( index ), '"' );
        index++;
        
        int subIndex = skipToQuote( input, index );
        
        return input.substring( index, subIndex );
    }
    
    private static List<SerialObject> generateSerialObjects( String input ) {
        List<SerialObject> serialObjects = new ArrayList<>();
        
        int index = skipToQuote( input, 0 );
        index = skipToQuote( input, index + 1 );
        index = skipWhitespace( input, index + 1 );
        
        while( index < input.length() ) {
            int subIndex = skipToColon( input, index );
            String uuid = input.substring( index, subIndex ).strip();
            index = subIndex + 1;
            
            subIndex = skipToColon( input, index );
            String objectType = input.substring( index, subIndex ).strip();
            index = subIndex + 1;
            
            subIndex = skipToBrace( input, index );
            String className = input.substring( index, subIndex ).strip();
            index = subIndex + 1;
            
            SerialObject sObj;
            if( objectType.equals( "OBJECT" ) ) sObj = new SerialObject( uuid, className );
            else if( objectType.equals( "ARRAY" ) ) sObj = new SerialArray( uuid, className );
            else throw new InvalidDeserializationInputException( objectType + " is not a valid Object Type." );
            
            index = skipWhitespace( input, index + 1 );
            
            while( input.charAt( index ) != '}' ) {
                subIndex = skipToColon( input, index );
                String variableName = input.substring( index, subIndex ).strip();
                index = subIndex + 1;
                
                subIndex = skipToColon( input, index );
                String variableType = input.substring( index, subIndex ).strip();
                index = subIndex + 1;
                
                index = skipWhitespace( input, index );
                
                if( input.charAt( index ) != '"' ) throw new InvalidDeserializationInputException( input.substring( index ), '"' );
                index++;
                
                subIndex = skipToQuote( input, index );
                String variableValue = input.substring( index, subIndex );
                index = subIndex + 1;
                
                if( variableType.equals( "OBJECT" ) ) {
                    sObj.addVariable( new SerialObjectVariable( variableName, variableValue ) );
                } else {
                    SerialPrimitiveType primType;
                    try {
                        primType = SerialPrimitiveType.valueOf( variableType );
                    } catch( Exception _ ) {
                        throw new InvalidDeserializationInputException( variableType + " is not a valid Variable Type." );
                    }
                    
                    sObj.addVariable( new SerialPrimitive( variableName, variableValue, primType ) );
                }
                
                index = skipWhitespace( input, index );
            }
            
            serialObjects.add( sObj );
            
            index = skipWhitespace( input, index + 1 );
        }
        
        return serialObjects;
    }
    
    private static int skipWhitespace( String input, int index ) {
        for( int i = index; i < input.length(); i++ )
            if( !Character.isWhitespace( input.charAt( i ) ) ) return i;
        return input.length();
    }
    
    private static int skipToCharacter( String input, int index, char character ) {
        for( int i = index; i < input.length(); i++ )
            if( input.charAt( i ) == character ) return i;
        throw new InvalidDeserializationInputException( character, input.substring( index ) );
    }
    
    private static int skipToColon( String input, int index ) {
        return skipToCharacter( input, index, ':' );
    }
    
    private static int skipToQuote( String input, int index ) {
        return skipToCharacter( input, index, '"' );
    }
    
    private static int skipToBrace( String input, int index ) {
        return skipToCharacter( input, index, '{' );
    }
    
    private static void modifySerialObjects( List<SerialObject> serialObjects, Version version, String objectVersion ) {
        Modification[] modifications = version.getModifications( objectVersion );
        
        for( Modification modification : modifications )
            modification.apply( serialObjects );
    }
    
    private static Map<String, Object> generateObjects( List<SerialObject> serialObjects ) {
        Map<String, Object> objects = new HashMap<>();
        Objenesis objenesis = new ObjenesisStd();
        
        for( SerialObject sObj : serialObjects ) {
            Class<?> cls;
            try {
                cls = Class.forName( sObj.getClassName() );
            } catch( Exception _ ) {
                throw new InvalidDeserializationInputException( sObj.getClassName() + " is not a valid Class Name." );
            }
            
            Object obj;
            if( cls.isArray() )
                obj = Array.newInstance( cls.getComponentType(), sObj.getVariables().size() );
            else obj = objenesis.newInstance( cls );
            
            objects.put( sObj.getUUID(), obj );
        }
        
        return objects;
    }
    
    private static void populateObjects( List<SerialObject> serialObjects, Map<String, Object> objects ) {
        for( SerialObject sObj : serialObjects ) {
            Object obj = objects.get( sObj.getUUID() );
            if( obj.getClass().isRecord() ) objects.put( sObj.getUUID(), populateRecord( sObj, obj, objects ) );
            else if( sObj instanceof SerialArray ) populateArray( (SerialArray) sObj, obj, objects );
            else populateObject( sObj, obj, objects );
        }
    }
    
    private static Object populateRecord( SerialObject sObj, Object obj, Map<String, Object> objects ) {
        RecordComponent[] components = obj.getClass().getRecordComponents();
        
        Class<?>[] parameterTypes = new Class<?>[components.length];
        
        for( int i = 0; i < components.length; i++ )
            parameterTypes[ i ] = components[ i ].getType();
        
        Constructor<?> constructor;
        try {
            constructor = obj.getClass().getDeclaredConstructor( parameterTypes );
            constructor.setAccessible( true );
        } catch( Exception _ ) {
            throw new InvalidDeserializationInputException( "Record class " + obj.getClass().getName() + " does not have a valid constructor." );
        }
     
         Object[] parameters = new Object[components.length];
        
        for( int i = 0; i < parameters.length; i++ ) {
            SerialVariable var = getSerialVariableFromName( components[ i ].getName(), sObj );
            
            if( var instanceof SerialObjectVariable ) {
                parameters[ i ] = getObjectFromSerialObjectVariable( (SerialObjectVariable) var, objects );
            } else {
                SerialPrimitive prim = (SerialPrimitive) var;
                
                try {
                    switch( prim.getType() ) {
                        case BOOLEAN -> parameters[ i ] = prim.getBoolean();
                        case BYTE -> parameters[ i ] = prim.getByte();
                        case SHORT -> parameters[ i ] = prim.getShort();
                        case INT -> parameters[ i ] = prim.getInt();
                        case LONG -> parameters[ i ] = prim.getLong();
                        case FLOAT -> parameters[ i ] = prim.getFloat();
                        case DOUBLE -> parameters[ i ] = prim.getDouble();
                        case CHAR -> parameters[ i ] = prim.getChar();
                        case STRING -> parameters[ i ] = prim.getString();
                        case NULL -> parameters[ i ] = null;
                    }
                } catch( Exception _ ) {
                    throw new InvalidDeserializationInputException( "Variable \"" + prim.getName() + "\" had mismatching variable type: \"" + prim.getType() + "and value: \"" + prim.getRawValue() + "\"." );
                }
            }
        }
        
        try {
            return constructor.newInstance( parameters );
        } catch( Exception _ ) {
            throw new InvalidDeserializationInputException( "Class \"" + obj.getClass() + "\" did not have correct variables.");
        }
    }
    
    private static void populateArray( SerialArray sObj, Object obj, Map<String, Object> objects ) {
        for( int i = 0; i < sObj.getVariables().size(); i++ ) {
            SerialVariable var = sObj.getVariables().get( i );
            
            if( var instanceof SerialObjectVariable ) {
                Array.set( obj, i, getObjectFromSerialObjectVariable( (SerialObjectVariable) var, objects ) );
            } else {
                SerialPrimitive prim = (SerialPrimitive) var;
                
                try {
                    switch( prim.getType() ) {
                        case BOOLEAN -> Array.setBoolean( obj, i, prim.getBoolean() );
                        case BYTE -> Array.setByte( obj, i, prim.getByte() );
                        case SHORT -> Array.setShort( obj, i, prim.getShort() );
                        case INT -> Array.setInt( obj, i, prim.getInt() );
                        case LONG -> Array.setLong( obj, i, prim.getLong() );
                        case FLOAT -> Array.setFloat( obj, i, prim.getFloat() );
                        case DOUBLE -> Array.setDouble( obj, i, prim.getDouble() );
                        case CHAR -> Array.setChar( obj, i, prim.getChar() );
                        case STRING -> Array.set( obj, i, prim.getString() );
                        case NULL -> Array.set( obj, i, null );
                    }
                } catch( Exception _ ) {
                    throw new InvalidDeserializationInputException( "Variable \"" + prim.getName() + "\" had mismatching variable type: \"" + prim.getType() + "and value: \"" + prim.getRawValue() + "\"." );
                }
            }
        }
    }
    
    private static void populateObject( SerialObject sObj, Object obj, Map<String, Object> objects ) {
        Class<?> cls = obj.getClass();
        
        List<Field> fields = new ArrayList<>();
        for( Class<?> c = cls; c != null; c = c.getSuperclass() )
            fields.addAll( List.of( c.getDeclaredFields() ) );
        
        for( Field field : fields ) {
            int modifier = field.getModifiers();
            if( Modifier.isTransient( modifier ) || Modifier.isStatic( modifier ) ) continue;
            String illegalAccessExceptionMessage = "Field \"" + field.getName() + "\" in class \"" + cls.getName() + "\" is not accessible.";
            try {
                field.setAccessible( true );
            } catch( Exception _ ) {
                throw new InvalidDeserializationInputException( illegalAccessExceptionMessage );
            }
            
            SerialVariable var = getSerialVariableFromName( field.getName(), sObj );
            
            if( var instanceof SerialObjectVariable ) {
                try {
                    field.set( obj, getObjectFromSerialObjectVariable( (SerialObjectVariable) var, objects ) );
                } catch( Exception _ ) {
                    throw new InvalidDeserializationInputException( illegalAccessExceptionMessage );
                }
            } else {
                SerialPrimitive prim = (SerialPrimitive) var;
                
                try {
                    switch( prim.getType() ) {
                        case BOOLEAN -> field.setBoolean( obj, prim.getBoolean() );
                        case BYTE -> field.setByte( obj, prim.getByte() );
                        case SHORT -> field.setShort( obj, prim.getShort() );
                        case INT -> field.setInt( obj, prim.getInt() );
                        case LONG -> field.setLong( obj, prim.getLong() );
                        case FLOAT -> field.setFloat( obj, prim.getFloat() );
                        case DOUBLE -> field.setDouble( obj, prim.getDouble() );
                        case CHAR -> field.setChar( obj, prim.getChar() );
                        case STRING -> field.set( obj, prim.getString() );
                        case NULL -> field.set( obj, null );
                    }
                } catch( Exception _ ) {
                    throw new InvalidDeserializationInputException( "Variable \"" + prim.getName() + "\" had mismatching variable type: \"" + prim.getType() + "and value: \"" + prim.getRawValue() + "\" or " + illegalAccessExceptionMessage );
                }
            }
        }
    }
    
    private static SerialVariable getSerialVariableFromName( String name, SerialObject obj ) {
        for( SerialVariable var : obj.getVariables() )
            if( var.getName().equals( name ) ) return var;
        throw new InvalidDeserializationInputException( "No variable with the name \"" + name + "\" found in \"" + obj.getClassName() + "\"." );
    }
    
    private static Object getObjectFromSerialObjectVariable( SerialObjectVariable var, Map<String, Object> objects ) {
        Object varObj = objects.get( ( var ).getUUID() );
        if( varObj == null ) throw new InvalidDeserializationInputException( "No object with UUID \"" + ( var ).getUUID() + "\" exists" );
        return varObj;
    }
}
