package utils

/**
 * Test per le utils che estendono le liste
 * Created by michele on 30/01/16.
 */


import org.junit.*
import kotlin.test.*

class TestMaze {

    @Test fun subListSlice() {
        assertEquals((1..56).toList(), (1..56).toList().subListSlice())
        assertEquals((55..56).toList(), (1..56).toList().subListSlice(-2))
        assertEquals((1..53).toList(), (1..56).toList().subListSlice(toIndex = -3))
        assertEquals((4..54).toList(), (1..56).toList().subListSlice(3,-2))
        assertEquals(emptyList<Int>(), (1..56).toList().subListSlice(-2, -10))
        assertEquals(emptyList<Int>(), (1..56).toList().subListSlice(-2, -2))
        assertEquals(emptyList<Int>(), (1..56).toList().subListSlice(100))
        assertEquals((1..56).toList(), (1..56).toList().subListSlice(-100))
        assertEquals((1..56).toList(), (1..56).toList().subListSlice(-100))
        assertEquals((1..56).toList(), (1..56).toList().subListSlice(toIndex = 100))
    }

    @Test fun max() {
        assertEquals(5, max(2,3,5,-2,-10))
        assertEquals(2.345, max(2.345))
        assertEquals(-3, max(-3,-4))
    }

    @Test fun min() {
        assertEquals(-10, min(2,3,5,-2,-10))
        assertEquals(2.345, min(2.345))
        assertEquals(-4, min(-3,-4))
    }
}