package test;

import model.Deque;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testDeque {
    Deque<Integer> test;

    @Before
    public void setup(){
        test = new Deque<Integer>();
        test.addFirst(1);
        test.addFirst(2);
        test.addLast(3);
        test.addFirst(4);
        test.addLast(5);
    }

    @Test
    public void testremoveFirst(){
        assertEquals((int) test.removeFirst(), 4);
        assertEquals((int) test.removeLast(), 5);
        assertEquals((int) test.removeFirst(), 2);
        test.addFirst(0);
        assertEquals((int) test.removeLast(), 3);
        assertEquals((int) test.removeFirst(), 0);
    }
}
