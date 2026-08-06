// Craig Foulkrod
// 06212026-08052026

/*

    Copyright (c) 2026 Craig Foulkrod. All rights reserved.
    
    Licensed under the MIT License
    See LICENSE file the project root for full license information
    
*/

package io.github.apotterhead1.cheers;

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
