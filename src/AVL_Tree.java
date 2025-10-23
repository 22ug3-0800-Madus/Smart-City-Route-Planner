package scplanner.tree;

import scplanner.model.Location;
import java.util.*;

class AVLNode {
    Location loc;
    AVLNode left, right;
    int height;
    AVLNode(Location loc) { this.loc = loc; height = 1; }
}

public class AVLTree {
    private AVLNode root;

    public boolean insert(Location loc) {
        if (loc == null) return false;
        if (contains(loc.getName())) return false;
        root = insert(root, loc);
        return true;
    }

    public boolean delete(String name) {
        if (!contains(name)) return false;
        root = delete(root, name);
        return true;
    }

    public boolean contains(String name) {
        return getNode(root, name) != null;
    }

    public List<Location> inorder() {
        List<Location> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    private AVLNode getNode(AVLNode node, String name) {
        if (node == null) return null;
        int cmp = name.compareToIgnoreCase(node.loc.getName());
        if (cmp == 0) return node;
        return cmp < 0 ? getNode(node.left, name) : getNode(node.right, name);
    }

    private void inorder(AVLNode n, List<Location> out) {
        if (n == null) return;
        inorder(n.left, out);
        out.add(n.loc);
        inorder(n.right, out);
    }

    private AVLNode insert(AVLNode node, Location loc) {
        if (node == null) return new AVLNode(loc);
        int cmp = loc.getName().compareToIgnoreCase(node.loc.getName());
        if (cmp < 0) node.left = insert(node.left, loc);
        else node.right = insert(node.right, loc);
        updateHeight(node);
        return balance(node);
    }

    private AVLNode delete(AVLNode node, String name) {
        if (node == null) return null;
        int cmp = name.compareToIgnoreCase(node.loc.getName());
        if (cmp < 0) node.left = delete(node.left, name);
        else if (cmp > 0) node.right = delete(node.right, name);
        else {
            // found
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            // two children: replace with inorder successor
            AVLNode succ = minValueNode(node.right);
            node.loc = succ.loc;
            node.right = delete(node.right, succ.loc.getName());
        }
        updateHeight(node);
        return balance(node);
    }

    private AVLNode minValueNode(AVLNode n) {
        while (n.left != null) n = n.left;
        return n;
    }

    // AVL helpers
    private void updateHeight(AVLNode n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }
    private int height(AVLNode n) { return n == null ? 0 : n.height; }
    private int getBalance(AVLNode n) { return n == null ? 0 : height(n.left) - height(n.right); }

    private AVLNode balance(AVLNode node) {
        int balance = getBalance(node);
        if (balance > 1 && getBalance(node.left) >= 0) return rotateRight(node);
        if (balance > 1) { node.left = rotateLeft(node.left); return rotateRight(node); }
        if (balance < -1 && getBalance(node.right) <= 0) return rotateLeft(node);
        if (balance < -1) { node.right = rotateRight(node.right); return rotateLeft(node); }
        return node;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left; AVLNode T2 = x.right;
        x.right = y; y.left = T2;
        updateHeight(y); updateHeight(x);
        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right; AVLNode T2 = y.left;
        y.left = x; x.right = T2;
        updateHeight(x); updateHeight(y);
        return y;
    }
}