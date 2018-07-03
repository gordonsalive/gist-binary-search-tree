package gordons_alive;

//extends the simple binary tree and adds the ability to rebalance, to get sorted output
//and also to get a tree shaped view


import java.util.Arrays;

public class BinarySearchTreeObject extends BinaryTreeObject implements BinarySearchTree {
    public String valuesString() {
        return Arrays.toString(values());
    }
    public String treeString() {
        //this is where the fun starts.  What does a tree string look like?
        //An item is enclosed in [], inside this on the left of it will be L[] for the left item then a comma
        //the it's own value, the a comma, then on the right of it will be R[] for the right item.
        //So, adding this, creates something very unbalanced, until it is balanced:
        // {7,4,7,6,5,7,8,7}
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
        // [L[L[],4,R[L[L[L[],5,R[]],6,R[L[L[],7,R[]],7,R[]]],7,R[]]],7,R[L[],8,R[]]]
        //if I get the rootNode, I can then
        if (rootNode == null) {
            return "[]";
        } else {
            return "[" + rootNode.treeString() + "]";
        }
    }
    public void rebalance() {
        //TODO: improve this so that it recurses down the node tree
        //further TODO: develop an optimised algorithm that finds the most balanced arrangement of nodes that fits the rules.
        //we're going to sort the item list, clear down the node tree, then add items starting with the middle value
        //this particular routine should take this list {7,4,7,6,5,7,8,7} and give me this tree:
        // [L[L[L[L[L[],4,R[]],5,R[]],6,R[L[L[],7,R[]],7,R[]]],7,R[]],7,R[L[],8,R[]]]
        //(1) capture vlaues
        Integer[] items = values();
        if (items.length < 2) {
            return; //no need to do anything, great!
        }
        //(2) clear out tree
        rootNode = null;//garbage collection will clear them all away as there are no circular references
        //(3) sort values
        Arrays.sort(items);//it should be sorted already, but virtue of how the items are added and retrieved, but hey, this is just gist
        //(4) find middle value - in Java division is either integer or floating point, depending upon types being operated on
        //System.out.println("items = " + Arrays.toString(items));
        int middle = items.length/2;
        if (items.length % 2 == 1) {
            middle += 1;// middle of 4 is 2, middle of 3 is 2
        }
        try {
            //we're going to step outward from the middle, stopping when we get out of bounds, starting at mid index
            int midIdx = middle -1;
            int up = midIdx +1;
            int down = midIdx -1;
            //int[] debug = {midIdx,up,down};
            //System.out.println("[midIdx,up,down]=" + Arrays.toString(debug));
            rootNode = new Node(items[midIdx]);
            while (up < items.length) {
                //System.out.println("adduping up:" + items[up]);
                rootNode.add(items[up]);
                if (down < 0) {
                    break;//don't continue, we hop up, then down each time, so hopping down is the first point we go out of bounds
                }
                //System.out.println("adduping down:" + items[down]);
                rootNode.add(items[down]);
                up++;
                down--;
            }
        } catch (NegativeValuesNotAllowed E) {
            //this isn't possible as I started with a list from the tree in the first place
            System.out.println("Err, not sure how to say this, but we've got a negative number not allowed exception in rebalance().");
        }
    }
}
