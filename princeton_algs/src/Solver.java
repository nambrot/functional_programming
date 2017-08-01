import edu.princeton.cs.algs4.*;

/**
 * Created by nambrot on 6/27/17.
 */
public class Solver {
    private Stack<Board> solution;
    private int i;
    private class SearchNode implements Comparable<SearchNode> {
        int numberOfMoves;
        int score;
        Board board;
        SearchNode previous;

        public SearchNode(Board board, SearchNode previous){
            this.board = board;
            this.previous = previous;
            if (previous == null)
                numberOfMoves = 0;
            else
                numberOfMoves = previous.numberOfMoves + 1;
            score = numberOfMoves + board.manhattan();
        }

        public int score(){
            return score;
        }

        @Override
        public int compareTo(SearchNode other) {
            if (score() > other.score()) { return 1; }
            else if (score() < other.score()) { return -1; }
            else { return 0; }
        }
    }

    public Solver(Board initial){
        if (initial == null)
            throw new java.lang.IllegalArgumentException();
        MinPQ<SearchNode> initialQueue = new MinPQ<>();
        MinPQ<SearchNode> twinQueue = new MinPQ<>();

        initialQueue.insert(new SearchNode(initial, null));
        twinQueue.insert(new SearchNode(initial.twin(), null));

        SearchNode initialSolved = null;
        SearchNode twinSolved = null;
        i = 0;
        while (initialSolved == null && twinSolved == null) {
            initialSolved = processQueue(initialQueue);
            twinSolved = processQueue(twinQueue);

        }

        if (initialSolved != null) {
            System.out.println(i);
            solution = new Stack<>();
            SearchNode node = initialSolved;
            while (node != null) {
                solution.push(node.board);
                node = node.previous;
            }
        }
    }           // find a solution to the initial board (using the A* algorithm)

    private SearchNode processQueue(MinPQ<SearchNode> queue) {
        i += 1;
        SearchNode node = queue.delMin();
        if (node.board.isGoal()){
            return node;
        } else {
            for (Board neighbor : node.board.neighbors()) {
                if (node.previous != null && node.previous.board.equals(neighbor)) {
                    continue;
                }
                i += 1;
                queue.insert(new SearchNode(neighbor, node));
            }
            return null;
        }
    }
    public boolean isSolvable(){
        return solution != null;
    }            // is the initial board solvable?
    public int moves()   {
        if (solution == null)
            return -1;
        return solution.size() - 1;
    }                  // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()  {
        return solution;
    }    // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {
//        In in = new In(args[0]);
        In in = new In("/Users/nambrot/Downloads/8puzzle/puzzle27.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } // solve a slider puzzle (given below)
}
