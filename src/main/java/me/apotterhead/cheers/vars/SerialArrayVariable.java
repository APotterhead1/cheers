// Craig Foulkrod
// 06182026-06182026

package me.apotterhead.cheers.vars;

public class SerialArrayVariable extends SerialObjectVariable {
    public SerialArrayVariable( String name, String uuid ) {
        super( name, uuid );
    }
    
    public String toString() {
        return getName() + ":Array:\"" + getUUID() + "\"";
    }
}
