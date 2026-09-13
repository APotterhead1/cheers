// Craig Foulkrod
// 06092026-09122026

/*

    Copyright (c) 2026 Craig Foulkrod. All rights reserved.
    
    Licensed under the MIT License
    See LICENSE file the project root for full license information
    
*/

package io.github.apotterhead1.cheers;

import java.util.List;
import io.github.apotterhead1.cheers.vars.SerialObject;
import java.util.ArrayList;

/**
 * {@code ObjectMap} is a custom Map that is designed to store {@link SerialObject} values with the key being the
 * {@code Object} that they represent
 *
 * @since 2.1.0
 */
public class ObjectMap {
    private final List<Node> nodes;
    
    /**
     * Constructs a new, empty {@code ObjectMap}
     */
    public ObjectMap() {
        this.nodes = new ArrayList<>();
    }
    
    void put( Object key, SerialObject value ) {
        nodes.add( new Node( key, value ) );
    }
    
    boolean contains( Object key ) {
        for( Node node : nodes )
            if( node.key() == key ) return true;
        return false;
    }
    
    SerialObject get( Object key ) {
        for( Node node : nodes )
            if( node.key() == key ) return node.value();
        return null;
    }
    
    /**
     * Returns an array of all the {@link SerialObject} values contained in this {@code ObjectMap}
     * @return an array of all the {@link SerialObject} values contained in this {@code ObjectMap}
     */
    public SerialObject[] getValues() {
        SerialObject[] values = new SerialObject[ nodes.size() ];
        for( int i = 0; i < nodes.size(); i++ )
            values[ i ] = nodes.get( i ).value();
        return values;
    }
    
    private record Node( Object key, SerialObject value ) {}
}
