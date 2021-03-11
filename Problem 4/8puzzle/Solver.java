import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private MinPQ<Node> pq;
    private MinPQ<Node> twinpq;
    private Board board;
    private Stack<Board> solutions;
    private int m;
    private boolean issolvable;

    public Solver(Board initial) {
        // make later this defensive copy
        this.board = initial;
        // ===============
        // first queue
        pq = new MinPQ<>();
        Node initialNode = new Node(board, 0, null);
        pq.insert(initialNode);

        // twin queue
        twinpq = new MinPQ<>();
        Node initialtwinNode = new Node(board.twin(), 0, null);
        twinpq.insert(initialtwinNode);

        solve();
    }

    private void solve() {
        while (!(pq.isEmpty() || twinpq.isEmpty())) {
            Node node = pq.delMin();
            Node twinnode = twinpq.delMin();

            if (!node.currentBoard.isGoal()) {
                for (Board b : node.currentBoard.neighbors()) {
                    if (node.previousNode != null && b.equals(node.previousNode.currentBoard)) continue;

                    Node n = new Node(b, node.moves + 1, node);
                    pq.insert(n);
                }
            } else {
                traceSolution(node);
                issolvable = true;
                m = node.moves;
                break;
            }

            if (!twinnode.currentBoard.isGoal()) {
                for (Board tb : twinnode.currentBoard.neighbors()) {
                    if (twinnode.previousNode != null && tb.equals(twinnode.previousNode.currentBoard)) continue;

                    Node tn = new Node(tb, twinnode.moves + 1, twinnode);
                    twinpq.insert(tn);
                }
            } else {
                traceSolution(twinnode);
                issolvable = false;
                m = -1;
                break;
            }
        }
    }

    private void traceSolution(Node node) {
        solutions = new Stack<>();
        //m = node.moves;

        while (true) {
            if (node == null) break;
            solutions.push(node.currentBoard);
            node = node.previousNode;
        }
    }


    public boolean isSolvable() {
        return issolvable;
    }

    public int moves() {
        return m;
    }

    public Iterable<Board> solution() {
        return solutions;
    }

    // inner class for Node
    private static class Node implements Comparable<Node> {
        private Board currentBoard;
        private int moves;
        private Node previousNode;
        private int priority;
        private int hamming;

        public Node(Board currentBoard, int moves, Node previousNode) {
            this.currentBoard = currentBoard;
            this.previousNode = previousNode;
            this.moves = moves;
            this.priority = currentBoard.manhattan() + moves;
            this.hamming = currentBoard.hamming() + moves;
        }

        @Override
        public int compareTo(Node that) {
            if (this.priority > that.priority) return 1;
            if (this.priority < that.priority) return -1;
            if (this.hamming > that.hamming) return 1;
            if (this.hamming < that.hamming) return -1;
            return 0;
        }
    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
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


    }
}
