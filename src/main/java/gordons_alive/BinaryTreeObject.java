package gordons_alive;

//* The BinaryTree class is effectively the root node.
//* it can be initialised with a list of positive integers and it can take further single additions
//* The sub nodes hold an integer and zero, 1 or 2 sub nodes.
//* if it has a left node then that value must be less than or equal to the node itself
//* if it has a right node then that value must be more than the the node itself
//* This is a kind of binary search tree (where left right sorting rules must apply not only to
//*  to the immediate sub-nodes, but also to all the nodes below them - i.e. not only must the left
//*  sub node be less than the current node, but all the sub nodes of the whole tree of the left
//*  sub node must also be less than the current node - so this creates a bit more work when
//*  adding items, but also makes it simple to do a binary search) since items are always added to the top node
//*  and propagate down following the sorting rule.  As the example below shows, this simple version can be inefficient
//* This tree forms a simple basis for other types of trees.
//* for example, given this list of items I will end up with this tree:
//* {7,4,7,6,5,7,8,7}
//*         7
//*        / \
//*       4   8
//*        \
//*         7
//*        /
//*       6
//*      / \
//*     5   7
//*        /
//*       7

//TODO: add an interface and factory. add method to get sorted values and values as a tree. add method to rebalance tree. reused existing BinaryTree tests against more sophisticated descendant using interface and factory.

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeObject implements BinaryTree {
    protected Node rootNode = null;
    public void initialise(Integer[] values) throws NegativeValuesNotAllowed {
        if (values.length == 0) {
            rootNode = null;
        } else {
            rootNode = new Node(values[0]);
            for (int x = 1; x < values.length; x++) {
                rootNode.add(values[x]);
            }
        }
    }
    public void add(Integer value) throws NegativeValuesNotAllowed{
        if (rootNode == null) {
            //initialise the tree with a single value
            Integer[] initial = {value};
            initialise(initial);
        } else {
            //add this value into the tree
            rootNode.add(value);
        }
    }
    public Integer[] values() {
        if (rootNode != null) {
            List<Integer> results = rootNode.values();
            return results.toArray(new Integer[0]);
        } else {
            return new Integer[0];
        }
    }
}

class NegativeValuesNotAllowed extends Exception {
    public NegativeValuesNotAllowed(String message) {
        super(message);
    }
}

class Node {
    //For the sake of my sanity, Node only works with ArrayLists not plain arrays.
    private Integer value = null;
    private Node leftNode = null;
    private Node rightNode = null;
    Node(int value) throws NegativeValuesNotAllowed {
        if (value < 0) {
            throw new NegativeValuesNotAllowed("value " + value + " is negative and cannot be added to the tree");
        }
        this.value = value;
    }
    void add(int value) throws NegativeValuesNotAllowed {
        //it the value is <= me, then I add it to the left, else I add it to the right
        //if nodes are missing, I add them, otherwise I call add on them
        if (value <= this.value) {
            leftNode = newNodeOrAdd(leftNode, value);
        } else {
            rightNode = newNodeOrAdd(rightNode, value);
        }
    }
    private Node newNodeOrAdd(Node node, int value) throws NegativeValuesNotAllowed {
        if (node == null) {
            return new Node(value);
        } else {
            node.add(value);
            return node;
        }
    }
    List<Integer> values() {
        //get the values from the left node if there are any
        //get the values from the right node if there are any
        //sandwich my value between these and return
        List<Integer> result = new ArrayList<>();
        if (leftNode != null) {
            result.addAll(leftNode.values());
        }
        result.add(value);
        if (rightNode != null) {
            result.addAll(rightNode.values());
        }
        return result;
    }
    String treeString() {
        // this doesn't provide the [] around the outside of the root node
        String leftTreeString = "L[]";
        if (leftNode != null) {
            leftTreeString = "L[" + leftNode.treeString() + "]";
        }
        String rightTreeString = "R[]";
        if (rightNode != null) {
            rightTreeString = "R[" + rightNode.treeString() + "]";
        }
        return leftTreeString + "," + value + "," + rightTreeString;
    }
}
