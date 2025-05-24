package flightMaster.structures;

import flightMaster.Passenger;

public class BoardingQueue {
    //node of the queue for easier readability
	private static class Node {
        Passenger passenger;
        Node next;

        Node(Passenger passenger) {
            this.passenger = passenger;
            this.next = null;
        }
    }
	
	//data of queue
    private Node front;
    private Node rear;
    private int size;
    
    //creating an empty boarding queue
    public BoardingQueue() {
        front = null;
        rear = null;
        size = 0;
    }
    
    // queue logic getting passenger as a data
    public void enqueue(Passenger passenger) {
        Node newNode = new Node(passenger);
        if (front == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public Passenger dequeue() {
        if (isEmpty()) {
            return null;
        }
        Passenger passenger = front.passenger;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return passenger;
    }

    public Passenger peek() {
        if (isEmpty()) {
            return null;
        }
        return front.passenger;
    }

    public boolean isEmpty() {
        return this.front == null;
    }

    public int size() {
        return size;
    }

    public void printQueue() {
        Node current = front;
        int placement=1;
        System.out.println("Boarding Queue:");
        while (current != null) {
        	System.out.print(placement+". ");
            System.out.println(current.passenger.toString());
            current = current.next;
            placement++;
        }
    }
}
