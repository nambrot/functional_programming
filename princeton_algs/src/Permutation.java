import edu.princeton.cs.algs4.StdIn;

/**
 * Created by nambrot on 6/23/17.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> q = new RandomizedQueue<>();

        for (String s: StdIn.readAllStrings())
            q.enqueue(s);

        for (int i = 0; i<k; i++) {
            System.out.println(q.dequeue());
        }

    }
}
