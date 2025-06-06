package flightMaster.structures;

import flightMaster.Flight;
import java.util.*;

public class FlightBST {
    private static class Node {
        Flight flight;
        Node left, right;

        Node(Flight flight) {
            this.flight = flight;
        }
    }

    private Node root;

    // Insert flight
    public void insert(Flight flight) {
        root = insertRec(root, flight);
    }

    private Node insertRec(Node root, Flight flight) {
        if (root == null) return new Node(flight);

        int cmp = flight.getFlightNumber().compareTo(root.flight.getFlightNumber());
        if (cmp < 0) {
            root.left = insertRec(root.left, flight);
        } else if (cmp > 0) {
            root.right = insertRec(root.right, flight);
        } else {
            throw new IllegalArgumentException("Flight already exists");
        }
        return root;
    }

    // Search flight by flight number
    public Flight search(String flightNumber) {
        return searchRec(root, flightNumber);
    }

    private Flight searchRec(Node root, String flightNumber) {
        if (root == null) return null;

        int cmp = flightNumber.compareTo(root.flight.getFlightNumber());
        if (cmp == 0) return root.flight;
        else if (cmp < 0) return searchRec(root.left, flightNumber);
        else return searchRec(root.right, flightNumber);
    }

    // Check if flight exists
    public boolean contains(String flightNumber) {
        return search(flightNumber) != null;
    }

    // In-order print
    public void inOrderPrint() {
        inOrderPrintRec(root);
    }

    private void inOrderPrintRec(Node node) {
        if (node == null) return;
        inOrderPrintRec(node.left);
        System.out.println(node.flight);
        inOrderPrintRec(node.right);
    }

    // Delete flight by flight number
    public void delete(String flightNumber) {
        root = deleteRec(root, flightNumber);
    }

    private Node deleteRec(Node root, String flightNumber) {
        if (root == null) return null;

        int cmp = flightNumber.compareTo(root.flight.getFlightNumber());
        if (cmp < 0) {
            root.left = deleteRec(root.left, flightNumber);
        } else if (cmp > 0) {
            root.right = deleteRec(root.right, flightNumber);
        } else {
            // Node to delete found
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // Node with two children: get inorder successor
            Node successor = minValueNode(root.right);
            root.flight = successor.flight;
            root.right = deleteRec(root.right, successor.flight.getFlightNumber());
        }
        return root;
    }

    private Node minValueNode(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // Count total flights
    public int size() {
        return sizeRec(root);
    }

    private int sizeRec(Node node) {
        if (node == null) return 0;
        return 1 + sizeRec(node.left) + sizeRec(node.right);
    }

    // Check if tree is empty
    public boolean isEmpty() {
        return root == null;
    }
    
    public void printTreeStructured() {
        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                Node current = queue.poll();
                if (current != null) {
                    System.out.print("[" + current.flight.getFlightNumber() + "] ");
                    queue.add(current.left);
                    queue.add(current.right);
                } else {
                    System.out.print("[null] ");
                }
            }
            System.out.println(); // Newline after each level
        }
    }



    private int maxDepth(Node node) {
        if (node == null) return 0;
        return Math.max(maxDepth(node.left), maxDepth(node.right)) + 1;
    }

    // Clear all flights
    public void clear() {
        root = null;
    }
}
