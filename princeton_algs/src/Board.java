import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

/**
 * Created by nambrot on 6/27/17.
 */
public class Board {
    private int[][] blocks;
    public Board(int[][] blocks) {
        int[][] newBlocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                newBlocks[i][j] = blocks[i][j];
            }
        }
        this.blocks = newBlocks;
    }           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension(){
        return blocks.length;
    }                 // board dimension n
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++){
                int value = blocks[i][j];
                if (value == 0)
                    continue;

                if (value % dimension() == (j + 1) % dimension() &&
                        (value-1) / dimension() == i){
//                    we good
                } else {
                    sum += 1;
                }
            }
        }
        return sum;
    }                   // number of blocks out of place
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < blocks.length; i++){
            for (int j = 0; j < blocks.length; j++){
                int value = blocks[i][j];
                if (value == 0)
                    continue;

                sum += Math.abs((value-1) / dimension() - i);
                sum += Math.abs((value-1) % dimension() - j);
            }
        }
        return sum;
    }                // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        return hamming() == 0;
    }               // is this board the goal board?
    private Board twinBySwitching(int x1, int y1, int x2, int y2) {
        int value1 = blocks[x1][y1];
        int value2 = blocks[x2][y2];

        int[][] newBlocks = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++) {
                if (i == x1 && j == y1) {
                    newBlocks[i][j] = value2;
                } else if (i == x2 && j == y2) {
                    newBlocks[i][j] = value1;
                } else {
                    newBlocks[i][j] = blocks[i][j];
                }
            }
        }

        return new Board(newBlocks);
    }
    public Board twin(){
        int block1 = blocks[0][0];
        if (block1 == 0) {
            return twinBySwitching(0, 1, 1, 0);
        } else {
            int block2 = blocks[1][0];
            if (block2 == 0) {
                return twinBySwitching(0, 0, 0, 1);
//                use 0, 0 and 0, 1
            } else {
                return twinBySwitching(0, 0, 1, 0);
//                use 0, 0 and 1,0
            }
        }
    }                    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++){
                if (that.blocks[i][j] != blocks[i][j])
                    return false;
            }
        }
        return true;
    }       // does this board equal y?
    public Iterable<Board> neighbors() {
        int[] pos = zeroPosition();
        int x = pos[0];
        int y = pos[1];

        Stack<Board> q = new Stack<>();
        if (x > 0) {
            q.push(twinBySwitching(x, y, x-1, y));
        }

        if (x < dimension() - 1) {
            q.push(twinBySwitching(x, y, x+1, y));
        }

        if (y > 0) {
            q.push(twinBySwitching(x, y, x, y-1));
        }

        if (y < dimension() - 1) {
            q.push(twinBySwitching(x, y, x, y+1));
        }

        return q;
    }    // all neighboring boards
    private int[] zeroPosition() {
        for (int pos = 0; pos < dimension() * dimension(); pos++) {
            int x = pos / dimension();
            int y = pos % dimension();
            if (blocks[x][y] == 0) {
                return new int[]{x, y};
            }
        }
        return new int[2];
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }              // string representation of this board (in the output format specified below)

    public static void main(String[] args){
        Board b1 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        assert b1.dimension() == 3;

        assert b1.hamming() == 0 : "b1 hamming is: " + b1.hamming();
        assert b1.manhattan() == 0;
        assert b1.isGoal();

        Board b2 = new Board(new int[][]{{5, 2, 3}, {4, 1, 6}, {7, 8, 0}});
        assert b2.hamming() == 2;
        assert b2.manhattan() == 4;
        assert !b2.isGoal();
        assert !b1.equals(b2);

        Board b3 = new Board(new int[][]{{4, 2, 3}, {5, 1, 6}, {7, 8, 0}});
        assert b3.twin().equals(b2);

        Board b4 = new Board(new int[][]{{4, 2, 3}, {5, 0, 6}, {7, 8, 1}});
        for (Board board: b4.neighbors() ){
            System.out.println(board);
        }

        Board b5 = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        assert b5.manhattan() == 4;

        Board b6 = new Board(new int[][]{{5, 8, 7}, {1, 4, 6}, {3, 0, 2}});
        assert b6.manhattan() == 17;
    } // unit tests (not graded)
}