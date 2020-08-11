package tw.teng.pratice.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void testMax() {
        assertEquals(8,MainActivity.max(3,5));
        assertNotEquals(7,MainActivity.max(3,5));
    }
}