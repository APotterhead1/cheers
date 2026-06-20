package me.apotterhead.cheers;

import me.apotterhead.cheers.vars.SerialPrimitive;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.logging.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

// Craig Foulkrod
// 06092026-06092026
class SerializerTest {
    
    @Test
    void serialize() throws IllegalAccessException {
//        TestSerialObject2 testObj2 = new TestSerialObject2();
//        testObj2.obj.setObj( testObj2 );
//        System.out.println( Serializer.serialize( testObj2 ) );
        
//        List<String> lst = List.of( "Test1", "Test2", "Test3", "Test4" );
//        System.out.println( Serializer.serialize( lst ));

//        int[] ray = { 0, 1, 2, 3, 4, 5, 6 };
//        System.out.println( Serializer.serialize( ray ) );
        
//        System.out.println( Serializer.serialize( TestEnum.TEST1 ) );
        
        System.out.println( Serializer.serialize( new TestRecord( 5, 'a', "This is a test of the emergency broadcast system" ) ) );
    }
    
    public static class TestSerialObject {
        private int a = 5;
        private String str = "This is a test of the emergency broadcast system";
        private TestSerialObject2 obj;
        
        private void setObj( TestSerialObject2 obj ) {
            this.obj = obj;
        }
    }
    
    public static class TestSerialObject2 {
        private TestSerialObject obj = new TestSerialObject();
    }
    
    public static enum TestEnum {
        TEST1,
        TEST2,
        TEST3
    }
    
    public record TestRecord( int i, char a, String c ) {}
}