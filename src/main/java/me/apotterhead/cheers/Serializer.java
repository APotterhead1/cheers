// Craig Foulkrod
// 06082026-06252026

package me.apotterhead.cheers;

import java.util.List;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.UUID;
import me.apotterhead.cheers.vars.SerialArray;
import me.apotterhead.cheers.vars.SerialObject;
import me.apotterhead.cheers.vars.SerialObjectVariable;
import me.apotterhead.cheers.vars.SerialPrimitive;

public final class Serializer {
    
    public static String serialize( Object object, Version version ) throws IllegalAccessException {
        if( object == null ) return "";
        StringBuilder sb = new StringBuilder();
        sb.append( '"' ).append( version.getCurrentVersion() ).append( "\"\n" );
        
        ObjectMap map = new ObjectMap();
        
        Class<?> cls = object.getClass();
        if( cls.isArray() ) serializeArray( object, map );
        else serializeObject( object, map );
        
        for( SerialObject sObj : map.getValues() )
            sb.append( sObj.toString() ).append( "\n" );
        return sb.toString();
    }
    
    private static SerialObject serializeObject( Object obj, ObjectMap map ) throws IllegalAccessException {
        Class<?> cls = obj.getClass();

        SerialObject sObj = new SerialObject( UUID.randomUUID().toString(), cls.getName() );
        map.put( obj, sObj );

        List<Field> fields = new ArrayList<>();
        for( Class<?> c = cls; c != null; c = c.getSuperclass() )
            fields.addAll( List.of( c.getDeclaredFields() ) );

        for( Field field : fields ) {
            field.setAccessible( true );
            int modifier = field.getModifiers();
            if( Modifier.isTransient( modifier ) || Modifier.isStatic( modifier ) ) continue;
            if( isPrimitive( field.getType() ) ) {
                sObj.addVariable( serializePrimitive( obj, field ) );
                continue;
            }
            
            if( field.get( obj ) == null ) {
                sObj.addVariable( new SerialPrimitive( field.getName() ) );
                continue;
            }
            
            if( map.contains( field.get( obj ) ) ) {
                sObj.addVariable( new SerialObjectVariable( field.getName(), map.get( field.get( obj ) ).getUUID() ) );
                continue;
            }

            SerialObject varSObj;
            if( field.get( obj ).getClass().isArray() )
                varSObj = serializeArray( field.get( obj ), map );
            else
                varSObj = serializeObject( field.get( obj ), map );
            sObj.addVariable( new SerialObjectVariable( field.getName(), varSObj.getUUID() ) );
        }

        return sObj;
    }
    
    private static SerialArray serializeArray( Object array, ObjectMap map ) throws IllegalAccessException {
        Class<?> cls = array.getClass();
        
        SerialArray sRay = new SerialArray( UUID.randomUUID().toString(), cls.getName() );
        map.put( array, sRay );
        
        if( cls.equals( boolean[].class ) )
            for( int i = 0; i < Array.getLength( array ); i++ )
                sRay.addVariable( new SerialPrimitive( Integer.toString( i ), Array.getBoolean( array, i ) ) );
        else if( cls.equals( byte[].class ) )
            for( int i = 0; i < Array.getLength( array ); i++ )
                sRay.addVariable( new SerialPrimitive( Integer.toString( i ), Array.getByte( array, i ) ) );
        else if( cls.equals( short[].class ) )
            for( int i = 0; i < Array.getLength( array ); i++ )
                sRay.addVariable( new SerialPrimitive( Integer.toString( i ), Array.getShort( array, i ) ) );
        else if( cls.equals( int[].class ) )
            for( int i = 0; i < Array.getLength( array ); i++ )
                sRay.addVariable( new SerialPrimitive( Integer.toString( i ), Array.getInt( array, i ) ) );
        else if( cls.equals( long[].class ) )
            for( int i = 0; i < Array.getLength( array ); i++ )
                sRay.addVariable( new SerialPrimitive( Integer.toString( i ), Array.getLong( array, i ) ) );
        else if( cls.equals( float[].class ) )
            for( int i = 0; i < Array.getLength( array ); i++ )
                sRay.addVariable( new SerialPrimitive( Integer.toString( i ), Array.getFloat( array, i ) ) );
        else if( cls.equals( double[].class ) )
            for( int i = 0; i < Array.getLength( array ); i++ )
                sRay.addVariable( new SerialPrimitive( Integer.toString( i ), Array.getDouble( array, i ) ) );
        else if( cls.equals( char[].class ) )
            for( int i = 0; i < Array.getLength( array ); i++ )
                sRay.addVariable( new SerialPrimitive( Integer.toString( i ), Array.getChar( array, i ) ) );
        else if( cls.equals( String[].class ) )
            for( int i = 0; i < Array.getLength( array ); i++ )
                sRay.addVariable( new SerialPrimitive( Integer.toString( i ), (String) Array.get( array, i ) ) );
        else {
            for( int i = 0; i < Array.getLength( array ); i++ ) {
                Object obj = Array.get( array, i );
                
                if( obj == null ) {
                    sRay.addVariable( new SerialPrimitive( Integer.toString( i ) ) );
                    continue;
                }
                
                Class<?> objCls = obj.getClass();
                
                if( objCls.equals( String.class ) ) {
                    sRay.addVariable( new SerialPrimitive( Integer.toString( i ), (String) Array.get( array, i ) ) );
                    continue;
                }

                if( map.contains( obj ) ) {
                    sRay.addVariable( new SerialObjectVariable( Integer.toString( i ), map.get( obj ).getUUID() ) );
                    continue;
                }

                SerialObject sObj;
                if( objCls.isArray() )
                    sObj = serializeArray( obj, map );
                else
                    sObj = serializeObject( obj, map );
                sRay.addVariable( new SerialObjectVariable( Integer.toString( i ), sObj.getUUID() ) );
            }
        }
        
        return sRay;
    }
    
    private static boolean isPrimitive( Class<?> cls ) {
        return cls.isPrimitive() || cls.equals( String.class );
    }

    private static SerialPrimitive serializePrimitive( Object obj, Field field ) throws IllegalAccessException {
        if( field.getType().equals( boolean.class ) )
            return new SerialPrimitive( field.getName(), field.getBoolean( obj ) );
        else if( field.getType().equals( byte.class ) )
            return new SerialPrimitive( field.getName(), field.getByte( obj ) );
        else if( field.getType().equals( short.class ) )
            return new SerialPrimitive( field.getName(), field.getShort( obj ) );
        else if( field.getType().equals( int.class ) )
            return new SerialPrimitive( field.getName(), field.getInt( obj ) );
        else if( field.getType().equals( long.class ) )
            return new SerialPrimitive( field.getName(), field.getLong( obj ) );
        else if( field.getType().equals( float.class ) )
            return new SerialPrimitive( field.getName(), field.getFloat( obj ) );
        else if( field.getType().equals( double.class ) )
            return new SerialPrimitive( field.getName(), field.getDouble( obj ) );
        else if( field.getType().equals( char.class ) )
            return new SerialPrimitive( field.getName(), field.getChar( obj ) );
        else if( field.get( obj ) == null )
            return new SerialPrimitive( field.getName() );
        else if( field.getType().equals( String.class ) )
            return new SerialPrimitive( field.getName(), (String) field.get( obj ) );
        
        throw new IllegalArgumentException( "Field is not a primitive type" );
    }
}
