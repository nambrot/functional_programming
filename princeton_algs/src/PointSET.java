import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.Set;

/**
 * Created by nambrot on 7/1/17.
 */
public class PointSET {
    private SET<Point2D> data;
    public         PointSET() {
        data = new SET<>();
    }                              // construct an empty set of points
    public           boolean isEmpty() {
        return data.isEmpty();
    }                     // is the set empty?
    public               int size(){
        return data.size();
    }                         // number of points in the set
    public              void insert(Point2D p){
        if (p == null) {
            throw new IllegalArgumentException();
        }
        data.add(p);
    }              // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p){
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return data.contains(p);
    }            // does the set contain point p?
    public              void draw(){
        for(Point2D p : data){
            p.draw();
        }
    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect){
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        SET<Point2D> ret = new SET<>();
        for (Point2D p : data) {
            if (rect.contains(p))
                ret.add(p);
        }
        return ret;
    }             // all points that are inside the rectangle
    public           Point2D nearest(Point2D p){
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()){
            return null;
        }

        Point2D i = data.min();
        double distance = p.distanceSquaredTo(i);

        for (Point2D x : data){
            if (p.distanceSquaredTo(x) < distance) {
                distance = p.distanceSquaredTo(x);
                i = x;
            }
        }
        return i;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }                 // unit testing of the methods (optional)
}
