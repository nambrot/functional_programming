/**
 * Created by nambrot on 7/1/17.
 */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Set;

/**
 * Created by nambrot on 7/1/17.
 */
public class KdTree {
    private SET<Point2D> data;
    private Node root;
    private int size = 0;
    private class Node{
        boolean isXNode;
        Point2D point;
        Node left;
        Node right;

        public Node(boolean isXNode, Point2D point){
            this.isXNode = isXNode;
            this.point = point;
        }

        private boolean isLeft(Point2D o) {
            if (isXNode) {
                return o.x() < point.x();
            } else {
                return o.y() < point.y();
            }
        }

        boolean contains(Point2D o) {
            if (o.equals(point))
                return true;

            if (isLeft(o)) {
                return left != null && left.contains(o);
            } else {
                return right != null && right.contains(o);
            }

        }

        boolean insert(Point2D o){
            if (o.equals(point)) {
                return false;
            }
            if (isLeft(o)) {
                if (left == null) {
                    left = new Node(!isXNode, o);
                    return true;
                }
                return left.insert(o);
            } else {
                if (right == null) {
                    right = new Node(!isXNode, o);
                    return true;
                }
                return right.insert(o);
            }
        }

        void draw(double x0, double y0, double x1, double y1) {
            point.draw();
            if (isXNode) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(point.x(), y0, point.x(), y1);
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x0, point.y(), x1, point.y());
            }
            StdDraw.setPenColor(StdDraw.BLACK)  ;
            if (left != null) {
                if (isXNode)
                    left.draw(x0, y0, point.x(), y1);
                else
                    left.draw(x0, y0, x1, point.y());
            }

            if (right != null) {
                if (isXNode)
                    right.draw(point.x(), y0, x1, y1);
                else
                    right.draw(x0, point.y(), x1, y1);
            }
        }

        Point2D bestPossiblePoint(Point2D o) {
            if (isXNode) {
                return new Point2D(point.x(), o.y());
            } else {
                return new Point2D(o.x(), point.y());
            }
        }


        Point2D nearest(Point2D target, Point2D current, double minDistance) {
            Point2D newCurrent = current;
            double newMinDistance = minDistance;
            if (point.distanceSquaredTo(target) < minDistance) {
                newCurrent = point;
                newMinDistance = point.distanceSquaredTo(target);
            }

            if (left == null && right == null) {
                return newCurrent;
            }

            if (isLeft(target)) {
                if (left != null) {
                    newCurrent = left.nearest(target, newCurrent, newMinDistance);
                    newMinDistance = newCurrent.distanceSquaredTo(target);
                }

                if (right != null && bestPossiblePoint(target).distanceSquaredTo(target) < newMinDistance) {
                    return right.nearest(target, newCurrent, newMinDistance);
                }

            } else {
                if (right != null) {
                    newCurrent = right.nearest(target, newCurrent, newMinDistance);
                    newMinDistance = newCurrent.distanceSquaredTo(target);
                }

                if (left != null && bestPossiblePoint(target).distanceSquaredTo(target) < newMinDistance) {
                    return left.nearest(target, newCurrent, newMinDistance);
                }
            }

            return newCurrent;
        }

        private RectHV intersectingRect() {
            if (isXNode)
                return new RectHV(point.x(), 0, point.x(), 1);
            else
                return new RectHV(0, point.y(), 1, point.y());
        }
        private void rangeH(Node branch, RectHV rect, SET<Point2D> ret) {
            if (branch != null) {
                branch.range(rect, ret);
            }
        }

        private boolean isLeftOfRect(RectHV rect) {
            if (isXNode)
                return point.x() < rect.xmin();
            else
                return point.y() < rect.ymin();
        }

        private boolean isRightOfRect(RectHV rect) {
            if (isXNode)
                return point.x() > rect.xmax();
            else
                return point.y() > rect.ymax();
        }

        void range(RectHV rect, SET<Point2D> ret) {
            if (rect.contains(point)) {
                ret.add(point);
            }

            if (rect.intersects(intersectingRect())) {
                rangeH(left, rect, ret);
                rangeH(right, rect, ret);
            }

            if (isLeftOfRect(rect))
                rangeH(right, rect, ret);

            if (isRightOfRect(rect))
                rangeH(left, rect, ret);

        }
    }
    public         KdTree() {
        data = new SET<>();
        root = null;
    }                              // construct an empty set of points
    public           boolean isEmpty() {
        return root == null;
    }                     // is the set empty?
    public               int size(){
        return size;
    }                         // number of points in the set
    public              void insert(Point2D p){
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            root = new Node(true, p);
            size++;
        } else {
            if (root.insert(p))
                size++;
        }
    }              // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p){
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return false;
        }

        return root.contains(p);
    }            // does the set contain point p?
    public              void draw(){
        if (root == null)
            return;
        root.draw(0, 0, 1, 1);
    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect){
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        SET<Point2D> ret = new SET<>();

        if (root != null) {
            root.range(rect, ret);
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

        double distance = root.point.distanceSquaredTo(p);

        return root.nearest(p, root.point, distance);
    }             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0, 0.5));

        tree.insert(new Point2D(0.5, 1));
        tree.insert(new Point2D(0.5, 0));
        tree.insert(new Point2D(1, 0.5));

        assert tree.nearest(new Point2D(0.26, 0.9)).equals(new Point2D(0.5, 1)) : "The closest point is apparently : " + tree.nearest(new Point2D(0.26, 0.9));

        assert tree.range(new RectHV(1, 0, 1, 1)).iterator().next().equals(new Point2D(1, 0.5));
        assert !tree.range(new RectHV(0, 0, 0.1, 0.1)).iterator().hasNext();
    }                 // unit testing of the methods (optional)
}
