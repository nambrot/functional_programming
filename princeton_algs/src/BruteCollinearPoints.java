import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by nambrot on 6/26/17.
 */
public class BruteCollinearPoints {
    private LineSegment[] arr;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++){
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        Arrays.sort(points);

        for (int i = 0; i < points.length; i++){
            if (i > 0 && points[i].slopeTo(points[i-1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException();
        }


        int arrI = 0;
        arr = new LineSegment[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                double slope1 = points[i].slopeTo(points[j]);
                if (slope1 == Double.NEGATIVE_INFINITY || points[j].compareTo(points[i]) < 0 || i == j)
                    continue;

                for (int k = 0; k < points.length; k++) {
                    double slope2 = points[i].slopeTo(points[k]);
                    if (slope1 != slope2 || slope2 == Double.NEGATIVE_INFINITY || points[k].compareTo(points[j]) < 0 || j == k) {
                        continue;
                    }

                    for (int l = 0; l < points.length; l++) {
                        double slope3 = points[i].slopeTo(points[l]);
                        if (slope2 != slope3 || slope3 == Double.NEGATIVE_INFINITY || points[l].compareTo(points[k]) < 0 || k == l) {
                            continue;
                        }
                        if ((slope2 == slope3) && (slope2 == slope3)) {
//                            System.out.println(points[i] + " " + points[j] + " " + points[k] + " " + points[l]);
//                            System.out.println(i + " " + j + " " + k + " " + l);
//                            System.out.println(slope1 + " " + slope2 + " " + slope3);
                            arr[arrI] = new LineSegment(points[i], points[l]);
                            arrI++;
                        }
                    }
                }
            }
        }

    }    // finds all line segments containing 4 points
    public           int numberOfSegments() { return segments().length; }        // the number of line segments
    public LineSegment[] segments(){
        int length = 0 ;
        for (length = 0; arr[length] != null; length++){

        }
        LineSegment[] newArr = new LineSegment[length];
        for (int i = 0; i < length; i++)
            newArr[i] = arr[i];
        return newArr;
    }                // the line segments

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("/Users/nambrot/Downloads/collinear/equidistant.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
