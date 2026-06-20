// Craig Foulkrod
// 06082026-06082026

package me.apotterhead.cheers.vars;

import java.util.List;
import java.util.ArrayList;

public class SerialObject {
    private final String uuid;
    private final String className;
    private final List<SerialVariable> variables;
    
    public SerialObject( String uuid, String className ) {
        this.uuid = uuid;
        this.className = className;
        variables = new ArrayList<>();
    }
    
    public void addVariable( SerialVariable variable ) {
        variables.add( variable );
    }
    
    public String getUUID() {
        return uuid;
    }
    
    public String getClassName() {
        return className;
    }
    
    public List<SerialVariable> getVariables() {
        return variables;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( uuid ).append( ":OBJECT:" ).append( className ).append( " {\n" );
        
        for( SerialVariable var : variables )
            sb.append( "\t" ).append( var.toString() ).append( "\n" );
        
        sb.append( "}" );
        return sb.toString();
    }
}
