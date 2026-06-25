package me.apotterhead.cheers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import me.apotterhead.cheers.SerializerTest.TestVersion;

// Craig Foulkrod
// 06232026-06232026
class DeserializerTest {
    
    @Test
    void deserialize() {
        String input = "\"1.0\"\n" +
                "049d9b23-7ce1-41b8-9504-a8d808828af7:OBJECT:me.apotterhead.cheers.SerializerTest$TestSerialObject2 {\n" +
                "\tobj:OBJECT:\"90457038-076b-4fb8-8d76-10a0c5ef4077\"\n" +
                "\ten:OBJECT:\"cb439a7f-52cf-49bd-b7f1-4d92c0f0afde\"\n" +
                "}\n" +
                "90457038-076b-4fb8-8d76-10a0c5ef4077:OBJECT:me.apotterhead.cheers.SerializerTest$TestSerialObject {\n" +
                "\ta:INT:\"5\"\n" +
                "\tstr:STRING:\"This is a test of the emergency broadcast system\"\n" +
                "\tobj:OBJECT:\"049d9b23-7ce1-41b8-9504-a8d808828af7\"\n" +
                "}\n" +
                "cb439a7f-52cf-49bd-b7f1-4d92c0f0afde:OBJECT:me.apotterhead.cheers.SerializerTest$TestEnum {\n" +
                "\tname:STRING:\"TEST2\"\n" +
                "\tordinal:INT:\"1\"\n" +
                "\thash:INT:\"0\"\n" +
                "}";

        SerializerTest.TestSerialObject2 obj = (SerializerTest.TestSerialObject2) Deserializer.deserialize( input, TestVersion.VERSION );

        System.out.println( obj );
    }
}