import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private Point[] copyofpoints;
    private int numberofsegment = 0;
    private List<LineSegment> linesegments;
    private int size;

    public BruteCollinearPoints(Point[] points) {
        if (points == null){
            throw new java.lang.IllegalArgumentException();
        }
        size = points.length;
        copyofpoints = points.clone();

        if (copyofpoints[size - 1] == null) throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < size - 1; i++){
            if (copyofpoints[i] == null) throw new java.lang.IllegalArgumentException();
            if (copyofpoints[i + 1] == null) throw new java.lang.IllegalArgumentException();
            if (copyofpoints[i].compareTo(copyofpoints[i + 1]) == 0){
                throw new java.lang.IllegalArgumentException();
            }

        }

        Arrays.sort(copyofpoints);
        linesegments = new ArrayList<>();
    }

    public int numberOfSegments() {
        return numberofsegment;
    }

    public LineSegment[] segments() {
        for (int i = 0; i < size - 3; i++){
            for (int j = i + 1; j < size - 2; j++){
                for (int k = j + 1; k < size - 1; k++){
                    for (int l = k + 1; l < size; l++){
                        double x1 = copyofpoints[i].slopeTo(copyofpoints[j]);
                        double x2 = copyofpoints[i].slopeTo(copyofpoints[k]);
                        double x3 = copyofpoints[i].slopeTo(copyofpoints[l]);

                        if (x1 == x2 && x1 == x3){
                            LineSegment temp = new LineSegment(copyofpoints[i], copyofpoints[l]);
                            if (!linesegments.contains(temp)){
                                numberofsegment++;
                                linesegments.add(temp);
                            }
                        }
                    }
                }
            }
        }
        return linesegments.toArray(new LineSegment[0]);
    }
}
