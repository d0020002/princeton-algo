public class QU2Stack {
    LinkedStack<String> inbox;
    LinkedStack<String> outbox;
    private int n;         // number of elements on queue

    public QU2Stack() {
        inbox = new LinkedStack<String>();
        outbox = new LinkedStack<String>();
    }

    public void enqueue(String s){
        inbox.push(s);
    }

    public String dequeue(){
        if (outbox.isEmpty()) {
            while (!inbox.isEmpty()) {
                outbox.push(inbox.pop());
            }
        }
        return outbox.pop();
    }

    public static void main(String[] args) {
        QU2Stack q = new QU2Stack();
        q.enqueue("S");
        q.enqueue("F");
        System.out.println(q.dequeue());
    }
}
