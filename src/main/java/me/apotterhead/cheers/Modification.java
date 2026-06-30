// Craig Foulkrod
// 06232026-06292026

package me.apotterhead.cheers;

import me.apotterhead.cheers.vars.SerialVariable;
import me.apotterhead.cheers.vars.SerialObject;
import me.apotterhead.cheers.vars.SerialObjectVariable;

import java.util.List;

/**
 * {@code Modification} is an interface that represents a change in a
 * {@code class} between versions. Each modification is applied during the
 * deserialization process to make an old {@code Object} compatible with the
 * lastest version its respective{@code class}.
 * <p>
 * Each {@code Modification} instance must contain a path leading to the
 * {@link SerialObject} that it is modifying. The path must start with {@code "root"}
 * representing the {@code Object} that was put into the {@link Serializer}.
 * The path then goes down the map through variable names, with each step separated
 * by a {@code "."}. An example of a valid path is
 * {@code "root.nestedObjectVariable.nestedNestedObjectVariable"}.
 *
 * @since 1.0.0
 */
public interface Modification {
    /**
     * Returns a {@code String} representation of the path to the {@link SerialObject}
     * that this {@code Modification} is supposed to modify.
     * <p>
     * The path must start with {@code "root"} representing the {@code Object}
     * that was put into the {@link Serializer}. The path then goes down the map through variable
     * names, with each step separated by a {@code "."}.
     * <p>
     * An example of a valid path is
     * {@code "root.nestedObjectVariable.nestedNestedObjectVariable"}.
     *
     * @return a {@code String} representation of the path to the
     * {@code SerialObject} that this {@code Modification} is modifying
     */
    String getPath();
    
    /**
     * Modifies the {@link SerialObject} at the path given by {@link #getPath} in
     * such a way that it is compatible with the latest version of the class. This
     * normally entails either adding or removing variables, changing variable datatypes,
     * or remaining variables.
     *
     * @param serialObjects a {@code List} containing a {@code SerialObject}
     *                      for every {@code Object} that is referenced at any point
     *                      in the {@code Object} that was serialized, including the
     *                      root {@code SerialObject}
     */
    void apply( List<SerialObject> serialObjects );
    
    
    /**
     * Returns the {@link SerialObject} from the {@code List} {@code serialObjects}
     * that corresponds to the path given by {@code path}.
     *
     * @param serialObjects a {@code List} containing a {@code SerialObject}
     *                      for every {@code Object} that is referenced at any point
     *                      in the {@code Object} that was serialized, including the
     *                      root {@code SerialObject}
     * @param path a {@code String} representing the path to the
     *             {@code SerialObject}. The path must start with {@code path}
     *             representing the {@code Object} that was put into the
     *             {@link Serializer}. The path then goes down the map through variable
     *             names, with each step separated by a {@code "."}. An example of a
     *             valid path is
     *             {@code "root.nestedObjectVariable.nestedNestedObjectVariable"}
     * @return the {@code SerialObject} that corresponds to the path given by
     * {@code path} from {@code serialObjects}
     */
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
