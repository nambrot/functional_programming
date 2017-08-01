/**
 * Created by nambrot on 6/26/17.
 */
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private int x;
    private int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }                        // constructs the point (x, y)

    public   void draw(){
        StdDraw.point(x, y);
    }                               // draws this point
    public   void drawTo(Point that){
        StdDraw.line(x, y, that.x, that.y);
    }                   // draws the line segment from this point to that point
    public String toString(){
        return "Point(x: " + x + ", y: " + y + ")";
    }                           // string representation

    public               int compareTo(Point that) {
        if      (y > that.y)  { return 1; }
        else if (y < that.y)  { return -1; }
        else if (x > that.x)  { return 1; }
        else if (x < that.x)  { return -1; }
        else                  { return 0; }

    }    // compare two points by y-coordinates, breaking ties by x-coordinates
    public            double slopeTo(Point that) {
        if (that.y == y) {
            if (that.x == x) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return 0;
            }
        }
        if (that.x == x) {
            return Double.POSITIVE_INFINITY;
        }
        return (that.y - y) / (double) (that.x - x);
    }       // the slope between this point and that point

    private class SlopeComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);
            if      (slope1 > slope2) { return 1; }
            else if (slope2 > slope1) { return -1; }
            else                      { return 0; }
        }
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }              // compare two points by slopes they make with this point
}