import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by nambrot on 6/23/17.
 */
public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node previous = null;
        private Node next = null;

        Node(Item i) {
            item = i;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node i = head;
        DequeIterator() {
        }

        public Item next() {
            if (i == null) {
                throw new NoSuchElementException();
            } else {
                Item item = i.item;
                i = i.next;
                return item;
            }
        }

        public boolean hasNext() {
            return i != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node head = null;
    private Node end = null;
    private int size = 0;

    public Deque() {

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newHead = new Node(item);
        if (head != null) {
            Node oldHead = head;
            oldHead.previous = newHead;
            newHead.next = oldHead;
        } else {
            end = newHead;
        }
        head = newHead;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newEnd = new Node(item);
        if (end != null) {
            Node oldEnd = end;
            oldEnd.next = newEnd;
            newEnd.previous = oldEnd;
        } else {
            head = newEnd;
        }
        end = newEnd;
        size++;
    }

    public Item removeFirst() {
        if (head == null) {
            throw new java.util.NoSuchElementException();
        } else {
            Item item = head.item;
            Node newHead = head.next;
            if (newHead == null) {
                head = null;
                end = null;
            } else {
                newHead.previous = null;
                head = newHead;
            }
            size--;
            return item;
        }
    }

    public Item removeLast() {
        if (end == null) {
            throw new java.util.NoSuchElementException();
        } else {
            Item item = end.item;
            Node newEnd = end.previous;

            if (newEnd == null) {
//                we actually only had one element
                head = null;
                end = null;
            } else {
                newEnd.next = null;
                end = newEnd;
            }
            size--;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        assert !d.iterator().hasNext() : "Initializes non-empty";

        d.addFirst("first");
        d.addFirst("second");

        assert d.size() == 2 : "has the correct size";
        assert d.iterator().hasNext() : "Is still empty";
        assert d.iterator().next() == "second" : "Correctly pushes";

        assert d.removeFirst() == "second" : "removes correctly";
        assert d.iterator().next() == "first" : "removes correctly";

        assert d.removeLast() == "first" : "correctly set the end";

        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(0);
        deque.addFirst(1);
        deque.addFirst(2);
        assert !deque.isEmpty() : "Is not empty";
    }
}
