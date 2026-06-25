// Craig Foulkrod
// 06232026-06252026

package me.apotterhead.cheers;

import me.apotterhead.cheers.vars.SerialVariable;
import me.apotterhead.cheers.vars.SerialObject;
import me.apotterhead.cheers.vars.SerialObjectVariable;

import java.util.List;

public interface Modification {
    String getPath();
    void apply( List<SerialObject> serialObjects );
    
    static SerialObject getSerialObjectFromPath( List<SerialObject> serialObjects, String path ) {
        String[] parts = path.split( "\\." );
        
        if( parts.length == 0 ) throw new InvalidDeserializationInputException( "Modification path was blank." );
        
        if( !parts[ 0 ].equals( "root" ) ) throw new InvalidDeserializationInputException( "Modification path \"" + path + "\" can not be found." );
        
        SerialObject currentSerialObject = serialObjects.getFirst();
        for( int i = 1; i < parts.length; i++ ) {
            try {
                SerialVariable var = getSerialVariableFromName( parts[ i ], currentSerialObject );
                if( var instanceof SerialObjectVariable ) {
                    String uuid = ( (SerialObjectVariable) var ).getUUID();
                    boolean found = false;
                    for( SerialObject varSObj : serialObjects ) {
                        if( varSObj.getUUID().equals( uuid ) ) {
                            currentSerialObject = varSObj;
                            found = true;
                            break;
                        }
                    }
                    if( !found ) throw new Exception();
                } else throw new Exception();
                
            } catch( Exception _ ) {
                throw new InvalidDeserializationInputException( "Modification path \"" + path + "\" can not be found." );
            }
        }
        
        return currentSerialObject;
    }
    
    private static SerialVariable getSerialVariableFromName( String name, SerialObject obj ) {
        for( SerialVariable var : obj.getVariables() )
            if( var.getName().equals( name ) ) return var;
        throw new InvalidDeserializationInputException( "No variable with the name \"" + name + "\" found in \"" + obj.getClassName() + "\"." );
    }
}
