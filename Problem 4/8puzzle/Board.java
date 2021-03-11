import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public final class Board {

    private final int dimenstion;
    private final int[][] board;

    public Board(int[][] blocks) {
        // making difensive copy of array
        board = deepCopy(blocks);

        dimenstion = board[0].length;
    }

    public int dimension() {
        return dimenstion;
    }

    // For testing
    private Board getgoal() {
        return new Board(makegoal());
    }

    /**
     * Hamming priority function. The number of blocks in the wrong position,
     * plus the number of moves made so far to get to the search node.
     * Intuitively, a search node with a small number of blocks in the wrong position is close to the goal,
     * and we prefer a search node that have been reached using a small number of moves.
     *
     * @return
     */

    public int hamming() {
        // Comparing this board to goal board by every element
        int count = 0;
        int[][] goal = makegoal();
        for (int row = 0; row < dimenstion; row++) {
            for (int col = 0; col < dimenstion; col++) {
                if (!(board[row][col] == goal[row][col]) && board[row][col] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Manhattan priority function.
     * The sum of the Manhattan distances (sum of the vertical and horizontal distance) from
     * the blocks to their goal positions,
     * plus the number of moves made so far to get to the search node.
     *
     * @return
     */

    public int manhattan() {
        int count = 0;
        for (int row = 0; row < dimenstion; row++) {
            for (int col = 0; col < dimenstion; col++) {
                int x = board[row][col];
                if (x == 0) continue;

                //calculating poistion of x in goal
                // search for way to implment 3 /2 = 2 and this function is ready
                int r = (int) Math.ceil((double) x / dimenstion);
                int c = x - ((r * dimenstion) - dimenstion);

                // calculating no of manhatten
                count += abs((row + 1) - r) + abs((col + 1) - c);
            }
        }
        return count;
    }

    public boolean isGoal() {
        return this.equals(new Board(makegoal()));
    }

    public Board twin() {
        Board b;
        for (int row = 0; row < dimenstion; row++) {
            for (int col = 0; col < dimenstion; col++) {
                if (board[row][col] == 0 && col + 1 != dimenstion) {
                    b = new Board(board);
                    swap(b, row + 1, col + 1, row + 1, col);
                    return b;
                }

                //special case for 1 0 / 3 2

                if (board[row][col] == 0) {
                    b = new Board(board);
                    swap(b, row + 1, col - 1, row + 1, col);
                    return b;
                }

                if (board[row][col] != 0 && board[row][col + 1] != 0) {
                    b = new Board(board);
                    swap(b, row, col, row, col + 1);
                    return b;
                }
            }
        }

        return null;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimenstion != that.dimenstion) return false;
        for (int row = 0; row < dimenstion; row++) {
            for (int col = 0; col < dimenstion; col++) {
                if (!(this.board[row][col] == that.board[row][col])) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> it = new Stack<>();
        int originrow = 0, origincolumn = 0;
        for (int row = 0; row < dimenstion; row++) {
            for (int col = 0; col < dimenstion; col++) {
                if (board[row][col] == 0) {
                    originrow = row;
                    origincolumn = col;
                    break;
                }
            }
        }

        if (originrow - 1 >= 0) {
            Board b1 = new Board(board);
            swap(b1, originrow, origincolumn, originrow - 1, origincolumn);
            it.push(b1);
        }

        if (originrow + 1 < dimenstion) {
            Board b2 = new Board(board);
            swap(b2, originrow, origincolumn, originrow + 1, origincolumn);
            it.push(b2);
        }

        if (origincolumn - 1 >= 0) {
            Board b3 = new Board(board);
            swap(b3, originrow, origincolumn, originrow, origincolumn - 1);
            it.push(b3);
        }

        if (origincolumn + 1 < dimenstion) {
            Board b4 = new Board(board);
            swap(b4, originrow, origincolumn, originrow, origincolumn + 1);
            it.push(b4);
        }
        return it;
    }

    private void swap(Board board, int torow, int tocol, int fromrow, int fromcol) {
        int temp = board.board[torow][tocol];
        board.board[torow][tocol] = board.board[fromrow][fromcol];
        board.board[fromrow][fromcol] = temp;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimenstion + "\n");
        for (int i = 0; i < dimenstion; i++) {
            for (int j = 0; j < dimenstion; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    private static int abs(int a) {
        return (a < 0) ? -a : a;
    }

    private int[][] makegoal() {
        // making goal board
        int[][] goal;
        int i = 1;
        goal = new int[dimenstion][dimenstion];

        for (int row = 0; row < dimenstion; row++) {
            for (int col = 0; col < dimenstion; col++) {
                goal[row][col] = i;
                i++;
            }
        }
        // set last elemnt to 0
        goal[dimenstion - 1][dimenstion - 1] = 0;
        return goal;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.getgoal());
    }
}
