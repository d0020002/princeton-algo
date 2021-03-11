public class StackMax {
    private LinkedStack<Integer> stack;
    private LinkedStack<Integer> track;

    public StackMax(){
        stack = new LinkedStack<>();
        track = new LinkedStack<>();
    }

    public void push(Integer i){
        stack.push(i);
        if (track.isEmpty()) { track.push(i);}
        else {
            if (track.peek() < i){
                track.push(i);
            }
            else {
                track.push(track.peek());
            }
        }
    }

    public int pop(){
        track.pop();
        return stack.pop();
    }

    public int max(){
        return track.pop();
    }

    public static void main(String[] args) {
        StackMax test = new StackMax();
        test.push(2);
        test.push(5);
        test.push(3);
        test.pop();
        test.pop();
        System.out.println(test.max());
    }
}
