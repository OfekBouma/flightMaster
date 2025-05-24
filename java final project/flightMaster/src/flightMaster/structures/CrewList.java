package flightMaster.structures;

import flightMaster.CrewMember;

public class CrewList {
    private class Node {
        CrewMember data;
        Node prev;
        Node next;

        Node(CrewMember data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size = 0;

    public void add(CrewMember crewMember) {
        Node newNode = new Node(crewMember);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public boolean remove(CrewMember crewMember) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(crewMember)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next; // removing head
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev; // removing tail
                }

                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean contains(CrewMember crewMember) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(crewMember)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public CrewMember get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
        Node current = head;
        int currentIndex = 0;
        while (currentIndex < index) {
            current = current.next;
            currentIndex++;
        }
        return current.data;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public void printAll() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data.toString());
            current = current.next;
        }
    }
    
    
}
