package me.apotterhead.cheers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

// Craig Foulkrod
// 06232026-06232026
class DeserializerTest {
    
    @Test
    void deserialize() {
        String input = "540b8196-0e59-4ccc-83ee-fd1a847a17d9:OBJECT:me.apotterhead.cheers.SerializerTest$TestSerialObject2 {\n" +
                "\tobj:OBJECT:\"48334f1d-78fa-4bfa-b500-33812eca2368\"\n" +
                "\ten:OBJECT:\"dcd0234f-d341-41c5-b87c-86f513c25a2a\"\n" +
                "}\n" +
                "48334f1d-78fa-4bfa-b500-33812eca2368:OBJECT:me.apotterhead.cheers.SerializerTest$TestSerialObject {\n" +
                "\ta:INT:\"5\"\n" +
                "\tstr:STRING:\"This is a test of the emergency broadcast system\"\n" +
                "\tobj:OBJECT:\"540b8196-0e59-4ccc-83ee-fd1a847a17d9\"\n" +
                "}\n" +
                "dcd0234f-d341-41c5-b87c-86f513c25a2a:OBJECT:me.apotterhead.cheers.SerializerTest$TestEnum {\n" +
                "\tname:STRING:\"TEST2\"\n" +
                "\tordinal:INT:\"1\"\n" +
                "\thash:INT:\"0\"\n" +
                "}\n";
        
        SerializerTest.TestSerialObject2 obj = (SerializerTest.TestSerialObject2) Deserializer.deserialize( input );

        System.out.println( obj.en );
    }
}