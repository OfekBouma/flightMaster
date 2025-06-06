package flightMaster.structures;

import flightMaster.Passenger;

public class PassengerList {
	
	/*
	 * this is a fully functional passenger list if we want we can later use it
	 * for other structures like passengerHistoryList
	 * or easterEggs like WantedPassengerList
	 */
	
	//private class Node for readability
	private class Node{
		
		private Node next;
		private Node prev;
		private Passenger passenger;
		
		public Node(Passenger passenger){
			this.passenger=passenger;
		}
	}
	
	//Data of passenger list
	private Node head;
	private Node tail;
	private int size;
	
	public PassengerList() {
		head=null;
		tail=null;
		size=0;
	}
	
	public void add(Passenger passenger) {
		Node newNode=new Node(passenger);
		if(head==null) {
			head=tail=newNode;
		}
		else {
			tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
		}
		size++;
	}
	
	//removing first Passenger from the head
	public Passenger removeFirst() {
        if (head == null) 
        	return null;
        //setting head as the next node to start removal
        Passenger p = head.passenger;
        head = head.next;
        
        if (head != null) 
        	head.prev = null;
        else 
        	tail = null;
        size--;
        return p;
	}
	
	public Passenger removeLast() {
	    if (tail == null) return null;

	    Passenger p = tail.passenger;
	    tail = tail.prev;

	    if (tail != null)
	        tail.next = null;
	    else
	        head = null;

	    size--;
	    return p;
	}
	
	
	//searches of passenger by name or id
	public Passenger searchByID(String id) {
	    Node current = head;
	    while (current != null) {
	        if (current.passenger.getId().equals(id)) {
	            return current.passenger;
	        }
	        current = current.next;
	    }
	    return null;
	}
	
	public Passenger searchByName(String name) {
	    Node current = head;
	    while (current != null) {
	        if (current.passenger.getName().equals(name)) {
	            return current.passenger;
	        }
	        current = current.next;
	    }
	    return null; // Not found
	}

	public Passenger removeByID(String id) {
	    Node current = head;

	    while (current != null) {
	        if (current.passenger.getId().equals(id)) {
	            // If it's the head node
	            if (current == head) {
	                return removeFirst();
	            }
	            // If it's the tail node
	            if (current == tail) {
	                return removeLast();
	            }
	            // It's a middle node: unlink it
	            current.prev.next = current.next;
	            current.next.prev = current.prev;
	            size--;
	            return current.passenger;
	        }
	        current = current.next;
	    }
	    return null; // Not found
	}
	
	public Passenger searchByNameIgnoreCase(String name) {
	    Node current = head;
	    while (current != null) {
	        if (current.passenger.getName().equalsIgnoreCase(name)) {
	            return current.passenger;
	        }
	        current = current.next;
	    }
	    return null; // Not found
	}
	
	public int size() {
	    return size;
	}
	
	public boolean isEmpty() {
	    return size == 0;
	}
	
	public void clear() {
	    Node current = head;
	    while (current != null) {
	        Node next = current.next;
	        current.next = null;
	        current.prev = null;
	        current.passenger = null;
	        current = next;
	    }
	    head = null;
	    tail = null;
	    size = 0;
	}
	
	public void listAllPassengers() {
	    if (isEmpty()) {
	        System.out.println("No passengers.");
	        return;
	    }

	    Node current = head;
	    while (current != null) {
	        System.out.println(current.passenger);  // This uses Passenger.toString()
	        current = current.next;
	    }
	}
	
	public void enqueueAllToQueue(BoardingQueue queue) {
	    Node current = head;
	    while (current != null) {
	        queue.enqueue(current.passenger);
	        current = current.next;
	    }
	}
	
	public void enqueuePassengersForFlight(BoardingQueue queue, String flightNumber) {
	    Node current = head;
	    while (current != null) {
	        Passenger p = current.passenger;
	        if (p.getTicket() != null && flightNumber.equals(p.getTicket().getFlightNumber())) {
	            queue.enqueue(p);
	        }
	        current = current.next;
	    }
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("PassengerList [size=").append(size).append("]\n");
	    Node current = head;
	    while (current != null) {
	        sb.append(current.passenger.toString()).append("\n");
	        current = current.next;
	    }
	    return sb.toString();
	}
	
}
