package flightMaster.structures;

import flightMaster.Flight;

public class FlightBST {
    private static class Node {
        Flight flight;
        Node left, right;

        Node(Flight flight) {
            this.flight = flight;
        }
    }

    private Node root;

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

    public void inOrderPrint() {
        inOrderPrintRec(root);
    }

    private void inOrderPrintRec(Node node) {
        if (node == null) return;
        inOrderPrintRec(node.left);
        System.out.println(node.flight);
        inOrderPrintRec(node.right);
    }
}
