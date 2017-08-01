import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by nambrot on 6/26/17.
 */
public class LineSegment {
    Point p;
    Point q;
    public LineSegment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }        // constructs the line segment between points p and q
    public   void draw() {
        p.drawTo(q);
    }                       // draws this line segment
    public String toString(){
        return "Line from " + p.toString() + " to " + q.toString();
    }                    // string representation
}
