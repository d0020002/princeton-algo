/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> item = new Stack<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                item.push(p);
            }
        }
        return item;
    }

    public Point2D nearest(Point2D p) {
        Point2D q = new Point2D(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Point2D r : points) {
            if (p.distanceTo(r) < p.distanceTo(q)) q = r;
        }
        return q;
    }

    public static void main(String[] arg) {
        PointSET p = new PointSET();
        p.insert(new Point2D(2, 4));
        p.insert(new Point2D(8, 1));
        p.insert(new Point2D(2, 4));
        p.insert(new Point2D(9, 2));
        p.insert(new Point2D(2, 4));
        p.insert(new Point2D(5, 3));
        p.insert(new Point2D(2, 4));
        p.insert(new Point2D(4, 4));
        p.insert(new Point2D(2, 4));
        p.insert(new Point2D(7, 5));
        p.insert(new Point2D(2, 4));
        p.insert(new Point2D(6, 6));
        p.insert(new Point2D(2, 4));
        p.insert(new Point2D(3, 7));
        p.insert(new Point2D(1, 1));
        RectHV r = new RectHV(1, 4, 4, 4);
        //p.draw();
        for (Point2D q : p.range(r)) {
            StdOut.println(q);
        }
        Point2D q = new Point2D(0, 0);
        StdOut.println(p.nearest(q));
    }
}
