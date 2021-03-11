package model;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<String>();
        int n = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            test.enqueue(StdIn.readString());
        }

        int count = 0;
        for (String s : test){
            if (count == n) break;
            StdOut.println(s);
            count++;
        }
    }
}
