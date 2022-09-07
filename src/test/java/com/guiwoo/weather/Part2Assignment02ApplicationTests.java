package com.guiwoo.weather;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Part2Assignment02ApplicationTests {

    @Test
    void equalsTest(){
        assertEquals("a","a");
    }

    @Test
    void nullTest(){
        assertNull(null);
    }

    @Test
    void trueTest(){
        assertTrue(true);
    }
}
