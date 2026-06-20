// Craig Foulkrod
// 06182026-06182026

package me.apotterhead.cheers.vars;

public class SerialArray extends SerialObject {
    public SerialArray( String uuid, String className ) {
        super( uuid, className );
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( getUUID() ).append( ":ARRAY:" ).append( getClassName() ).append( " {\n" );
        
        for( SerialVariable var : getVariables() )
            sb.append( "\t" ).append( var.toString() ).append( "\n" );
        
        sb.append( "}" );
        return sb.toString();
    }
}
