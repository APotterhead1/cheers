// Craig Foulkrod
// 06182026-06302026

/*

    Copyright (c) 2026 Craig Foulkrod
    
    License under the MIT License
    See LICENSE file the project root for full license information
    
 */

package me.apotterhead.cheers.vars;

/**
 * {@code SerialObject} is a class that extends {@link SerialObject} and
 * represents an array being serialized. The array in question could be
 * either the primary array being serialized or an array referenced by
 * another {@code Object} in the serialization chain.
 *
 * @since 1.0.0
 */
public class SerialArray extends SerialObject {
    
    /**
     * Constructs a new {@code SerialArray} with a {@code String} representing
     * a unique ID ({@code uuid}), and a {@code String} representing a
     * {@code class}. The formatting of the {@code className} varaible should match
     * exactly what is returned by {@link Class#getName}.
     *
     * @param uuid a {@code String} representing a unique ID for the {@code Object}
     * @param className a {@code String} representing the {@code class} of the
     * {@code Object}. The formatting should be consistent with what is returned by
     * {@link Class#getName}
     */
    public SerialArray( String uuid, String className ) {
        super( uuid, className );
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * In the case of {@code SerialArray}, each {@code SerialVariable} represents
     * an element in the array. The order the {@code SerialVariable}s are added
     * here represents the order they will take in the corresponding array,
     * irrespective of the name of the variable.
     *
     * @param variable {@inheritDoc}
     */
    public void addVariable( SerialVariable variable ) {
        super.addVariable( variable );
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * In the case of {@code SerialArray}, the {@code TYPE} is always {@code ARRAY}.
     *
     * @return {@inheritDoc}
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( getUUID() ).append( ":ARRAY:" ).append( getClassName() ).append( " {\n" );
        
        for( SerialVariable var : getVariables() )
            sb.append( "\t" ).append( var.toString() ).append( "\n" );
        
        sb.append( "}" );
        return sb.toString();
    }
}
