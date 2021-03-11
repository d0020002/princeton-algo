import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] copyofpoints;
    private int size;
    private LineSegment[] mySegments = null;
    private int num = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        size = points.length;
        copyofpoints = points.clone();

        if (copyofpoints[size - 1] == null) throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < size - 1; i++) {
            if (copyofpoints[i] == null) throw new java.lang.IllegalArgumentException();
            if (copyofpoints[i + 1] == null) throw new java.lang.IllegalArgumentException();
            if (copyofpoints[i].compareTo(copyofpoints[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }

        }
        copyofpoints = points.clone();
        size = points.length;
        // large array for storing segments
        mySegments = new LineSegment[size + 10];

        for (int i = 0; i < size ;i++) {
            //sort the points to maintin natural flagorder(stable sort)
            Arrays.sort(copyofpoints);
            // current origin
            Point origin = points[i];
            Arrays.sort(copyofpoints, origin.slopeOrder());
            int j = 0;
            int currentcount = 1;
            boolean flagorder= true;
            while (j < size - 1) {
                double x1 = origin.slopeTo(copyofpoints[j]);
                double x2 = origin.slopeTo(copyofpoints[j + 1]);
                if (x1 == x2) {
                    currentcount += 1;
                    // check for natural order
                    int y1 = origin.compareTo(copyofpoints[j]);
                    int y2 = origin.compareTo(copyofpoints[j + 1]);
                    if (y1 > 0 && y2 > 0) flagorder= false;
                } else {
                    if (currentcount >= 3 && flagorder) {
                        mySegments[num] = new LineSegment(origin, copyofpoints[j]);
                        num += 1;
                    }
                    currentcount = 1;
                    flagorder= true;
                }
                j += 1;
                // special case of last index
                if ((j == size - 1) && currentcount >= 3 && flagorder) {
                    mySegments[num] = new LineSegment(origin, copyofpoints[j]);
                    num += 1;
                    currentcount = 1;
                    flagorder= true;
                }
            }
        }
    }

    public int numberOfSegments() {
        return num;
    }

    public LineSegment[] segments() {
        int length = numberOfSegments();
        LineSegment[] segments = new LineSegment[length];
        for (int i = 0; i < length; i++) {
            segments[i] = mySegments[i];
        }
        return segments;
    }
}

