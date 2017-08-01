import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by nambrot on 6/26/17.
 */
public class FastCollinearPoints {
    private Point[] originalPoints;
    private Point[][] candidates;
    private LineSegment[] arr;

    public FastCollinearPoints(Point[] points) {
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


        originalPoints = new Point[points.length];
        for (int i=0;i<points.length;i++)
            originalPoints[i] = points[i];

        arr = new LineSegment[1000000];
        candidates = new Point[100000][];
        int arrI = 0;
        for (int i = 0; i<originalPoints.length; i++) {
            Arrays.sort(points, originalPoints[i].slopeOrder());
            double currentValue = originalPoints[i].slopeTo(points[0]);
            int start = 0;
            int end = 0;
            while (end <= points.length) {
                if ( end < points.length && originalPoints[i].slopeTo(points[end]) == currentValue) {
                    end++;
                } else {
//                    evaluate current progress
                    if (end - start >= 3) {

//                        we found a sequence of sufficient size
                        Point[] linePoints = new Point[end-start+1];
                        for (int j = 0; j < end-start; j++){
                            linePoints[j] = points[start + j];
                        }
                        linePoints[end-start] = originalPoints[i];

                        Arrays.sort(linePoints);

                        boolean found = false;
                        for (int j = 0; j < arrI; j++) {
                            Point a = candidates[j][0];
                            Point b = candidates[j][1];

                            if (a == linePoints[0] && b == linePoints[linePoints.length-1]) {
                                found = true;
                                break;
                            }

                        }

                        if (!found) {
                            Point[] arr = new Point[2];
                            arr[0] = linePoints[0];
                            arr[1] = linePoints[linePoints.length - 1];
                            candidates[arrI++] = arr;
                        }
                    }

                    if (end != points.length) {
                        currentValue = originalPoints[i].slopeTo(points[end]);
                        start = end;
                    } else {
//                        to break the while loop
                        end++;
                    }

                }
            }
        }
    }     // finds all line segments containing 4 or more points

    public           int numberOfSegments()   {
        return segments().length;
    }      // the number of line segments
    public LineSegment[] segments() {
        int length = 0 ;
        for (length = 0; candidates[length] != null; length++){

        }
        LineSegment[] newArr = new LineSegment[length];
        for (int i = 0; i < length; i++)
            newArr[i] = new LineSegment(candidates[i][0], candidates[i][1]);

        return newArr;
    }                // the line segments


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("/Users/nambrot/Downloads/collinear/input40.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}