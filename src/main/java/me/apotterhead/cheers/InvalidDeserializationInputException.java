// Craig Foulkrod
// 06212026-06212026

package me.apotterhead.cheers;

class InvalidDeserializationInputException extends RuntimeException {
    public InvalidDeserializationInputException( char character, String deserializerInput ) {
        String addition = "Failed to locate '" + character + "' in \"" + deserializerInput + "\".";
        this( addition );
    }
    
    public InvalidDeserializationInputException( String deserializerInput, char character ) {
        String addition = "Expected \"" + character + "\", but found \"" + deserializerInput + "\".";
        this( addition );
    }
    
    public InvalidDeserializationInputException( String addition ) {
        String message = "Invalid String input to Deserializer. " + addition;
        super( message );
    }
}
