package flightMaster.structures;

import flightMaster.Passenger;

public class DeboardingStack {
    private static class Node {
        Passenger data;
        Node next;

        Node(Passenger data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;
    private int size;

    public DeboardingStack() {
        top = null;
        size = 0;
    }

    public void push(Passenger passenger) {
        Node newNode = new Node(passenger);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Passenger pop() {
        if (isEmpty()) {
            return null;
        }
        Passenger data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public Passenger peek() {
        return isEmpty() ? null : top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void printStack() {
        Node current = top;
        System.out.println("Deboarding Order (Top to Bottom):");
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
