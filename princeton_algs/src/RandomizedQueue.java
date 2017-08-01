import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by nambrot on 6/23/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n = 0;
    private Item[] items;
    public RandomizedQueue() {
        items = (Item[]) new Object[20];
    }                // construct an empty randomized queue
    public boolean isEmpty()   {
        return n == 0;
    }              // is the queue empty?
    public int size() {
        return n;
    }

    private int len() {
        return items.length;
    }// return the number of items on the queue
    public void enqueue(Item item)  {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == items.length) {
            resize(items.length * 2);
        }
        items[n++] = item;
    }         // add the item
    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(n);
        Item item = items[index];
        items[index] = items[--n];
        items[n+1] = null;

        if (n * 4 < items.length && items.length > 20) {
            resize(items.length / 2);
        }
        return item;
    }                    // remove and return a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(n)];
    }                     // return (but do not remove) a random item


    private class RandomIterator implements Iterator<Item> {
        private int[] indicies;
        private int iter = 0;
        RandomIterator() {
            indicies = new int[n];
            for (int i = 0; i<n; i++) {
                indicies[i] = i;
            }

            StdRandom.shuffle(indicies);
        }

        public boolean hasNext() {
            return iter < indicies.length;
        }

        public Item next() {
            if (hasNext()) {
                return items[indicies[iter++]];
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }        // return an independent iterator over items in random order

    private void resize(int targetN) {
        Item[] newItems = (Item[]) new Object[targetN];
        for(int i = 0; i<n; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        q.enqueue("test");
        assert q.size() == 1 : "size";
        assert q.sample() == "test" : "Enqueued and dequued";
        assert q.dequeue() == "test" : "Enqueued and dequued";

        for (int i = 0; i<21; i++) {
            q.enqueue("test" + i);
        }

        assert q.len() == 40 : "Correctly grew";

        for (int i = 0; i<12; i++) {
            q.dequeue();
        }

        assert q.len() == 20 : "Correctly shrinked";
    }  // unit testing (optional)
}