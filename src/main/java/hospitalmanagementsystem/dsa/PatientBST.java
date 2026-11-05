
package hospitalmanagementsystem.dsa;

import hospitalmanagementsystem.model.Patient;

public class PatientBST {

    private class Node {
        Patient patient;
        Node left, right;

        Node(Patient p) {
            patient = p;
        }
    }

    private Node root;

    // Inserts a patient into the BST
    public boolean insert(Patient p) {

        if (p == null || search(p.getId()) != null) {
            return false;
        }

        root = insert(root, p);
        return true;
    }

    // Finds the correct position for a patient
    private Node insert(Node n, Patient p) {

        if (n == null) {
            return new Node(p);
        }

        if (p.getId() < n.patient.getId()) {
            n.left = insert(n.left, p);
        } else if (p.getId() > n.patient.getId()) {
            n.right = insert(n.right, p);
        }

        return n;
    }

    // Searches for a patient using their ID
    public Patient search(int id) {

        Node n = root;

        while (n != null) {

            if (id == n.patient.getId()) {
                return n.patient;
            }

            if (id < n.patient.getId()) {
                n = n.left;
            } else {
                n = n.right;
            }
        }

        return null;
    }

    // Removes a patient from the BST
    public boolean remove(int id) {

        if (search(id) == null) {
            return false;
        }

        root = remove(root, id);
        return true;
    }

    // Finds and removes the patient
    private Node remove(Node n, int id) {

        if (n == null) {
            return null;
        }

        if (id < n.patient.getId()) {
            n.left = remove(n.left, id);

        } else if (id > n.patient.getId()) {
            n.right = remove(n.right, id);

        } else {

            if (n.left == null) {
                return n.right;
            }

            if (n.right == null) {
                return n.left;
            }

            Node min = n.right;

            while (min.left != null) {
                min = min.left;
            }

            n.patient = min.patient;
            n.right = remove(n.right, min.patient.getId());
        }

        return n;
    }

    // Checks whether the BST contains a patient
    public boolean contains(int id) {
        return search(id) != null;
    }
}
