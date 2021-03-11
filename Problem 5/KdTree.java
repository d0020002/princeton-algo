/* *****************************************************************************
 *  Name: Deepak Singh Kushwaha
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class KdTree {

    private Node root; // root of BST
    private int size = 0;
    private Point2D champion;

    public KdTree() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls put() with a null key");
        root = insert(root, p, true, null, null);
    }

    private Node insert(Node x, Point2D p, boolean isVertical, RectHV parentRect,
                        Point2D parentPoint) {
        // create rectv using parents x and y coordiante and isVertical
        //variable
        if (x == null) {
            size++;
            if (parentRect == null) {
                RectHV rect = new RectHV(0, 0, 1, 1);
                return new Node(p, rect, isVertical);
            }
            RectHV rect;

            if (isVertical) {
                boolean istop = p.y() >= parentPoint.y();
                if (istop) {
                    rect = new RectHV(parentRect.xmin(), parentPoint.y(), parentRect.xmax(),
                                      parentRect.ymax());
                }
                else {
                    rect = new RectHV(parentRect.xmin(), parentRect.ymin(),
                                      parentRect.xmax(),
                                      parentPoint.y());
                }

            }
            else {
                boolean isleft = p.x() < parentPoint.x();
                if (isleft) {
                    rect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentPoint.x(),
                                      parentRect.ymax());
                }
                else {
                    rect = new RectHV(parentPoint.x(), parentRect.ymin(),
                                      parentRect.xmax(), parentRect.ymax());
                }
            }
            return new Node(p, rect, isVertical);
        }

        // Partiting based on x and y alternating
        // cmp == -1 : p < node.p
        // cmp == 0  : p == node.p
        // cmp == 1  : p > node.p
        if (p.compareTo(x.p) == 0) return x;
        int cmp;
        if (isVertical) {
            if (p.x() < x.p.x()) cmp = -1;
            else if (p.x() > x.p.x()) cmp = 1;
            else cmp = 0;
        }

        else {
            if (p.y() < x.p.y()) cmp = -1;
            else if (p.y() > x.p.y()) cmp = 1;
            else cmp = 0;
        }

        if (cmp == -1) x.lb = insert(x.lb, p, !isVertical, x.rect, x.p);
        else if (cmp == 1) x.rt = insert(x.rt, p, !isVertical, x.rect, x.p);
        else if (cmp == 0) x.rt = insert(x.rt, p, !isVertical, x.rect, x.p);
        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(p) != null;
    }

    private Point2D get(Point2D p) {
        return get(root, p, true);
    }

    private Point2D get(Node x, Point2D p, boolean isVertical) {
        if (p == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        if (p.compareTo(x.p) == 0) return x.p;
        int cmp = 0;
        if (isVertical) {
            if (p.x() < x.p.x()) cmp = -1;
            else if (p.x() >= x.p.x()) cmp = 1;
        }

        else {
            if (p.y() < x.p.y()) cmp = -1;
            else if (p.y() >= x.p.y()) cmp = 1;
        }

        if (cmp == -1) return get(x.lb, p, !isVertical);
        else if (cmp == 1) return get(x.rt, p, !isVertical);
        else return x.p;
    }


    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) return;
        draw(x.lb);

        if (x.isvertical) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        }
        else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }


        // StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.BLACK);
        x.p.draw();
        // StdDraw.setPenRadius();
        draw(x.rt);
    }


    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<>();
        range(root, rect, q);
        return q;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> q) {
        if (x == null) return;
        if (!rect.intersects(x.rect)) return;
        if (rect.contains(x.p)) q.enqueue(x.p);
        range(x.lb, rect, q);
        range(x.rt, rect, q);
    }

    public Point2D nearest(Point2D p) {
        champion = new Point2D(2, 2);
        nearest(root, p);
        return champion;
    }

    private void nearest(Node x, Point2D queryPoint) {
        // Base case : if x == null return;
        // Also update champion if needed
        // first go to sub tree which contain point
        // check subtree that contain using rectangle.contain method
        // if query.champ.distance  < query.otherrect.[anything like this xmin()] no need
        // to search other subtree

        // it seems there will be four cases for finding coorect rectangle min point
        // use isvertical field to determine
        // use binary tree properties to determine isleft or isbottom

        if (x == null) return;

        // updating champion
        if (queryPoint.distanceTo(champion) > queryPoint.distanceTo(x.p)) champion = x.p;

        // determine which subtree to go search first
        boolean islb;
        if (x.lb == null && x.rt == null) return;

        if (x.lb == null) islb = false;
        else if (x.lb.rect.contains(queryPoint)) islb = true;
        else islb = false;

        if (islb) {
            nearest(x.lb, queryPoint);
            // now calulate if there is need to go to right subtree
            if (x.rt == null) return;
            if (needtogo(x, true, queryPoint)) nearest(x.rt, queryPoint);
        }
        else {
            nearest(x.rt, queryPoint);
            if (x.lb == null) return;
            if (needtogo(x, false, queryPoint)) nearest(x.lb, queryPoint);
        }
    }

    private boolean needtogo(Node x, boolean islb, Point2D queryPoint) {
        //
        if (x.isvertical) {
            if (islb) {
                Point2D otherside = new Point2D(x.rt.rect.xmin(), queryPoint.y());
                if (queryPoint.distanceTo(champion) < queryPoint.distanceTo(otherside)) {
                    return false;
                }
            }
            else {
                Point2D otherside = new Point2D(x.lb.rect.xmax(), queryPoint.y());
                if (queryPoint.distanceTo(champion) < queryPoint.distanceTo(otherside)) {
                    return false;
                }

            }

        }
        else {
            if (islb) {
                Point2D otherside = new Point2D(queryPoint.x(), x.rt.rect.ymin());
                if (queryPoint.distanceTo(champion) < queryPoint.distanceTo(otherside)) {
                    return false;
                }
            }
            else {
                Point2D otherside = new Point2D(queryPoint.x(), x.lb.rect.ymin());
                if (queryPoint.distanceTo(champion) < queryPoint.distanceTo(otherside)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean isvertical;

        Node(Point2D p, RectHV rect, boolean isvertical) {
            this.p = p;
            this.rect = rect;
            this.isvertical = isvertical;
        }
    }

    public static void main(String[] args) {
        KdTree p = new KdTree();

        In in = new In(args[0]);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D z = new Point2D(x, y);
            p.insert(z);
        }
        StdOut.println(p.nearest(new Point2D(0.5, 0.4)));
    }
}
