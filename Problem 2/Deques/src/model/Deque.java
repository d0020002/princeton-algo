package model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;                // number of elements on deque
    private Node first;           // beginning of deque
    private Node last;            // end of deque

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // helper linked list class
    private class Node{
        private Item item;
        private Node next;
        private Node previous;
    }

   public boolean isEmpty() { return first == null; }

   public int size() { return n; }

   public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;

        if (size() == 0) last = first;
        else oldfirst.previous = first;
        n++;

   }

   public void addLast(Item item) {
       if (item == null) throw new IllegalArgumentException();

        Node oldlast = last;
        last = new Node();
        last.previous = oldlast;
        last.next = null;
        last.item = item;
        if (size() == 0) first = last;
        else oldlast.next = last;
        n++;

   }

   public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item item = first.item;
        if (first.next != null){
            first = first.next;
        }
        first.previous = null;
        n--;
        if (n == 0){
            first = null;
            last = null;
        }
        return item;
   }

   public Item removeLast() {
       if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.item;
        if (last.previous != null){
            last = last.previous;
        }
        last.next = null;
       n--;
       if (n == 0){
           first = null;
           last = null;
       }
        return item;
   }

    public Iterator<Item> iterator()  {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

   public static void main(String[] args) {
   }
}