/**
 * Created by nambrot on 6/21/17.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF data;
    private WeightedQuickUnionUF backwash;
    private int nn;
    private boolean[] openState;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        openState = new boolean[n * n];
        int i = 0;
        while (i < n*n) {
            openState[i] = false;
            i += 1;
        }
        numberOfOpenSites = 0;
        nn = n;
        data = new WeightedQuickUnionUF(n * n + 2);
        backwash = new WeightedQuickUnionUF(n * n + 2);
    }

    private void checkBounds(int row, int col) {
        if (row < 1 || row > nn || col < 1 || col > nn) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int index(int row, int col) {
        return (row-1) * nn + (col-1);
    }
    public    void open(int row, int col)  {
        checkBounds(row, col);
        if (!isOpen(row, col)) {
            int index = index(row, col);
            openState[index] = true;
            numberOfOpenSites++;

            if (row == 1) {
                data.union(index(row, col), nn * nn);
                backwash.union(index(row, col), nn * nn);
            }

            connectNeighbor(row, col, row - 1, col);
            connectNeighbor(row, col, row + 1, col);
            connectNeighbor(row, col, row, col + 1);
            connectNeighbor(row, col, row, col - 1);

            if (row == nn) {
                data.union(index(row, col), nn * nn + 1);
            }
        }
    }   // open site (row, col) if it is not open already

    private void connectNeighbor(int row, int col, int nRow, int nCol) {
        if (nRow > 0 && nRow <= nn && nCol > 0 && nCol <= nn && isOpen(nRow, nCol)) {
            data.union(index(row, col), index(nRow, nCol));
            backwash.union(index(row, col), index(nRow, nCol));
        }
    }

    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return openState[index(row, col)];
    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && data.connected(index(row, col), nn*nn) && backwash.connected(index(row, col), nn*nn);
    }  // is site (row, col) full?

    public     int numberOfOpenSites()   {
        return numberOfOpenSites;
    }     // number of open sites

    public boolean percolates()   {
        return data.connected(nn * nn, nn * nn + 1);
    }            // does the system percolate?

    public static void main(String[] args)  {
        Percolation p = new Percolation(3);
        assert !p.isOpen(2, 2) : "Wrong initialization";
        assert !p.isFull(2, 2) : "Wrong initialization";
        assert !p.isFull(1, 1) : "Wrong initialization";

        p.open(1, 1);
        assert p.isOpen(1, 1) : "Did not open";
        assert p.isFull(1, 1) : "Did not open";
        assert p.numberOfOpenSites == 1 : "Does not track number of open sites";

        assert !p.percolates() : "Does not percolate yet";
        p.open(2, 1);
        p.open(3, 1);
        assert p.percolates() : "Did not percolate";

        p = new Percolation(6);
        assert !p.isFull(1, 1) : "Wrong initialization";
        p.open(1, 6);
        p.open(2, 6);
        p.open(3, 6);
        p.open(4, 6);
        p.open(5, 6);
        p.open(5, 5);
        p.open(4, 4);
        p.open(3, 4);
        p.open(2, 4);
        p.open(2, 3);
        p.open(2, 2);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);
        p.open(5, 2);
        p.open(6, 2);
        p.open(5, 4);
        assert p.percolates() : "Did not percolate";
    }  // test client (optional)
}