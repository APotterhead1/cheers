// Craig Foulkrod
// 06092026-06302026

/*

    Copyright (c) 2026 Craig Foulkrod
    
    License under the MIT License
    See LICENSE file the project root for full license information
    
 */

package me.apotterhead.cheers;

import java.util.List;
import java.util.ArrayList;
import me.apotterhead.cheers.vars.SerialObject;

class ObjectMap {
    private final List<Node> nodes;
    
    public ObjectMap() {
        this.nodes = new ArrayList<>();
    }
    
    public void put( Object key, SerialObject value ) {
        nodes.add( new Node( key, value ) );
    }
    
    public boolean contains( Object key ) {
        for( Node node : nodes )
            if( node.key() == key ) return true;
        return false;
    }
    
    public SerialObject get( Object key ) {
        for( Node node : nodes )
            if( node.key() == key ) return node.value();
        return null;
    }
    
    public SerialObject[] getValues() {
        SerialObject[] values = new SerialObject[ nodes.size() ];
        for( int i = 0; i < nodes.size(); i++ )
            values[ i ] = nodes.get( i ).value();
        return values;
    }
    
    private record Node( Object key, SerialObject value ) {}
}
