// Craig Foulkrod
// 06092026-06092026

package me.apotterhead.cheers.vars;

public class SerialObjectVariable implements SerialVariable {
    private final String name;
    private final String uuid;
 
    public SerialObjectVariable( String name, String uuid ) {
        this.name = name;
        this.uuid = uuid;
    }
    
    public String getName() {
        return name;
    }
    
    public String getUUID() {
        return uuid;
    }
    
    public String toString() {
        return name + ":OBJECT:\"" + uuid + "\"";
    }
}
