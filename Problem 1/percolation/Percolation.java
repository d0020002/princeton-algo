import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private int bottom;
    private int top = 0;
    private boolean[][] grid;
    private WeightedQuickUnionUF qf;
    private int noofopen = 0;

    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        size = n;
        bottom = (size * size) + 1;
        qf = new WeightedQuickUnionUF(size * size + 2);
        grid = new boolean[size][size];
    }

    private int xyTo1D(int row, int col) {
        row = row - 1;
        col = col - 1;
        return (row * size) + col + 1;
    }

    private void isvalid(int row, int col) {
        if (((row > size || row < 1) || (col > size) || (col < 1))) {
            throw new IllegalArgumentException();
        }
    }

    public void open(int row, int col) {
        isvalid(row, col);
        if (grid[row - 1][col - 1]) {
            return;
        }
        grid[row - 1][col - 1] = true;
        noofopen += 1;
        if (row == 1) {
            qf.union(xyTo1D(row, col), top);
            if (isOpen(row+1, col)) {
                qf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
        }

        else if (row == size) {
            qf.union(xyTo1D(row, col), bottom);
             if (isOpen(row - 1, col)) {
                 qf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
        }

        else if (col == 1) {
            if (isOpen(row, col+1)) {
            qf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            if (isOpen(row -1, col)) {
            qf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (isOpen(row+1, col)) {
            qf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
        }

        else if (col == size) {
            if (isOpen(row, col-1)) {
            qf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (isOpen(row-1, col)) {
            qf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (isOpen(row +1, col)) {
            qf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
        }

        else {
            if (isOpen(row, col-1)) {
            qf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (isOpen(row, col+1)) {
            qf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            if (isOpen(row-1, col)) {
            qf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (isOpen(row+1, col)) {
            qf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        isvalid(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        isvalid(row, col);
        return qf.connected(xyTo1D(row, col), top);
    }

    public int numberOfOpenSites() {
        return noofopen;
    }

    public boolean percolates() {
        return qf.connected(top, bottom);
    }

    public static void main(String[] args) {
    }

}