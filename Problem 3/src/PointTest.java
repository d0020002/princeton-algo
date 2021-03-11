import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PointTest {

    Point first;
    Point second;
    Point third;
    Point fourth;
    Point fifth;
    Point horizental1;
    Point horizental2;
    Point vertical1;
    Point vertical2;
    ArrayList<Point> listsofpoints = new ArrayList<>();

    Point origin;


    @BeforeEach
    public void setup(){
        first = new Point(5, 10);
        second = new Point(11, 7);
        third = new Point(6, 10);
        fourth = new Point(5, 10);
        fifth = new Point(7, 10);

        horizental1 = new Point(8, 0);
        horizental2 = new Point(3, 0);

        vertical1 = new Point(0, 7);
        vertical2 = new Point(0, 15);

        origin = new Point(10, 15);

        listsofpoints.add(first);
        listsofpoints.add(second);
        listsofpoints.add(third);
        listsofpoints.add(fourth);
        listsofpoints.add(fifth);
    }


    @Test
    public void compareToTest(){
        // -1 less, +1 greater, 0 equals
        // y0 > y1
        assertEquals(first.compareTo(second), 1);
        // y1 > y0
        assertEquals(first.compareTo(second),1);
        // y0 == y1 and x0 > x1
        assertEquals(third.compareTo(first), 1);
        // y0 == y1 and x0 < x1
        assertEquals(third.compareTo(fifth), -1);
        // y0 == y1 and x0 == x1
        assertEquals(first.compareTo(fourth), 0);
    }

    @Test
    public void slopeToTest(){
        // horizental line: zero
        // vertical line: poistive inifinty
        // degenrate line: negative infinty
        double positiveInfinity = Double.POSITIVE_INFINITY;
        double negativeInfinity = Double.NEGATIVE_INFINITY;

        // genral case
        assertEquals(first.slopeTo(second), -1.0/2.0);
        // horizental case
        assertEquals(horizental1.slopeTo(horizental2), 0);
        // vertical case
        assertEquals(vertical1.slopeTo(vertical2), positiveInfinity);
        // degenrate case
        assertEquals(first.slopeTo(fourth), negativeInfinity);

    }

    @Test
    //TODO
    public void slopeOrderTest() {
//        10000      0
//        0  10000
//        3000   7000
//        7000   3000
//        20000  21000
//        3000   4000
//        14000  15000
//        6000   7000
        Point  x1 = new Point(10000, 0);
        Point x2 = new Point(0, 10000);
        Point x3 = new Point(3000, 7000);
        Point x4 = new Point(7000, 3000);
        Point x5 = new Point(20000, 21000);
        Point x6 = new Point(3000, 4000);
        Point x7 = new Point(14000, 15000);
        Point x8 = new Point(6000, 7000);
        Point[] test = new Point[]{x2, x3, x4, x5, x6, x7, x8};

        System.out.println("printing original array");
        for(Point p : test){
            System.out.println(p);
        }

        System.out.println();

        System.out.println("sort by x1");
        Arrays.sort(test, x1.slopeOrder());
        for (Point p : test){
            System.out.println(p + String.valueOf(x2.slopeTo(p)));
        }
    }

    @Test
    public void UsageTest(){}

    @Test
    public void FaieldTest(){
        Point t1 = new Point(257, 485);
        Point t2 = new Point(257, 394);

        System.out.println(t1.slopeTo(t2));

    }

}
