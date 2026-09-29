package atc.datastructures;

public class Stack {
    private static class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    public void push(Object item) {
        Node newNode = new Node(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Object pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        Object data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        top = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stack[top->");
        Node current = top;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(" -> ");
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}