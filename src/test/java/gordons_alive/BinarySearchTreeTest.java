package gordons_alive;

//Since in this project the binary tree tests are actually a running against a binary search tree
//I'm only repeating a few of those tests against BinarySearchTree.

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {
    SearchTreeFactory factory = new SearchTreeFactory();
    @Test
    public void canCreateNewBinarySearchTree() {
        assertNotNull(factory.newBinarySearchTree());
    }
    @Test
    public void canBeInitialisedWithListContainingDuplicates(){
        Integer[] itemArray = {3,2,5,6,1,9,5,4,4,8,3};
        ValuesVsResultsRecord valuesAndResultsSorted = createBinarySearchTreeInitialiseAndSortResults(itemArray);
        assertArrayEquals(valuesAndResultsSorted.values, valuesAndResultsSorted.results);
    }
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    @Test
    public void handlesArrayWithNegativeItemWithCorrectMessage() throws Exception{
        expectedEx.expect(NegativeValuesNotAllowed.class);
        expectedEx.expectMessage("value -2 is negative and cannot be added to the tree");
        Integer[] itemArray = {1,-2};
        BinarySearchTree binTree = factory.newBinarySearchTree();
        binTree.initialise(itemArray);
    }
    @Test
    public void canAddMutipleUnorderedItemsWithDuplicatesToInitisalisedTree() {
        Integer[] initialValues = {3,1,5,1,2,4,4,7,8,6,3};
        Integer[] additions = {3,1,5,1,2,4,4,7,8,6,3};
        ValuesVsResultsRecord valuesAndResults = createBinarySearchTreeInitialiseAndAddAndSortResults(initialValues, additions);
        assertArrayEquals(valuesAndResults.values, valuesAndResults.results);
    }
    @Test(expected = NegativeValuesNotAllowed.class)
    public void handlesAddingArrayWithNegativeItem() throws NegativeValuesNotAllowed{
        Integer[] additions = {1,-2};
        BinaryTree binTree = factory.newBinarySearchTree();
        for (Integer item: additions) {
            binTree.add(item);
        }
    }
    @Test
    public void shouldOutputSortedResults() {
        Integer[] initialValues = {3,1,5,1,2,4,4,7,8,6,3};
        BinarySearchTree binTree = createBinaryTreeAndInitialise(initialValues);
        Arrays.sort(initialValues);
        Integer[] results = binTree.values();
        assertArrayEquals(initialValues, results);
    }
    @Test
    public void shouldOutputSortedResultsAsAString() {
        Integer[] initialValues = {3,1,5,1,2,4,4,7,8,6,3};
        Integer[] additions = {3,1,5,1,4,4,8,3};
        BinarySearchTree binTree = createBinaryTreeAndInitialise(initialValues);
        binTree = addItemsToBinarySearchTree(binTree, additions);
        //create the comparison results record and sort the items ready for return
        List<Integer> valuesList = new ArrayList<>(Arrays.asList(initialValues));
        List<Integer> additionsList = new ArrayList<>(Arrays.asList(additions));
        valuesList.addAll(additionsList);
        Integer[] values = valuesList.toArray(new Integer[0]);
        Arrays.sort(values);
        String valuesString = Arrays.toString(values);

        String resultsString = binTree.valuesString();
        assertEquals(valuesString, resultsString);
    }
    @Test
    public void shouldOutputResultsAsATreeString() {
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
        Integer[] initialValues = {7,4,7,6,5,7,8,7};
        String expectedTreeString = "[L[L[],4,R[L[L[L[],5,R[]],6,R[L[L[],7,R[]],7,R[]]],7,R[]]],7,R[L[],8,R[]]]";
        BinarySearchTree binTree = createBinaryTreeAndInitialise(initialValues);
        String resultsTreeString = binTree.treeString();
        System.out.println("expectedTreeString = " + expectedTreeString);
        System.out.println("resultsTreeString = " + resultsTreeString);
        assertEquals(expectedTreeString, resultsTreeString);
    }
    @Test
    public void shouldRebalanceTree() {
        // Current algorithm does a single iteration of rebalancing.
        // for this {7,4,7,6,5,7,8,7}
        // it should turn this [L[L[],4,R[L[L[L[],5,R[]],6,R[L[L[],7,R[]],7,R[]]],7,R[]]],7,R[L[],8,R[]]]
        // into this           [L[L[L[L[L[],4,R[]],5,R[]],6,R[L[L[],7,R[]],7,R[]]],7,R[]],7,R[L[],8,R[]]]
        // Note: this test depends on shouldOutputResultsAsATreeString() to test that this list is initially sored in the first form
        //       so we are not testing this again in this test.
        // TODO: the rebalance algorithm should repeat recusively down the tree to get a slightly more balanced tree
        Integer[] initialValues = {7,4,7,6,5,7,8,7};
        String expectedTreeString = "[L[L[L[L[L[],4,R[]],5,R[]],6,R[L[L[],7,R[]],7,R[]]],7,R[]],7,R[L[],8,R[]]]";
        BinarySearchTree binTree = createBinaryTreeAndInitialise(initialValues);
        binTree.rebalance();
        String resultsTreeString = binTree.treeString();
        System.out.println("expectedTreeString = " + expectedTreeString);
        System.out.println("resultsTreeString = " + resultsTreeString);
        assertEquals(expectedTreeString, resultsTreeString);
    }






    private ValuesVsResultsRecord createAndSortValuesAndResults(Integer[] values, Integer[] results) {
        ValuesVsResultsRecord valuesVsResults = new ValuesVsResultsRecord(values, results);
        Arrays.sort(valuesVsResults.values);
        Arrays.sort(valuesVsResults.results);
        return valuesVsResults;
    }
    private ValuesVsResultsRecord createBinarySearchTreeInitialiseAndSortResults(Integer[] values) {
        //create and initialise the binary tree
        BinarySearchTree binTree = createBinaryTreeAndInitialise(values);
        //create the comparison results record and sort the items ready for return
        return createAndSortValuesAndResults(values, binTree.values());
    }
    private ValuesVsResultsRecord createBinarySearchTreeInitialiseAndAddAndSortResults(Integer[] initialValues, Integer[] additions) {
        //create and initialise the binary tree
        BinarySearchTree binTree = createBinaryTreeAndInitialise(initialValues);
        //add all the additions
        binTree = addItemsToBinarySearchTree(binTree, additions);
        //create the comparison results record and sort the items ready for return
        List<Integer> valuesList = new ArrayList<>(Arrays.asList(initialValues));
        List<Integer> additionsList = new ArrayList<>(Arrays.asList(additions));
        valuesList.addAll(additionsList);
        Integer[] values = valuesList.toArray(new Integer[0]);
        return createAndSortValuesAndResults(values, binTree.values());
    }
    private BinarySearchTree createBinaryTreeAndInitialise(Integer[] values) {
        BinarySearchTree binTree = factory.newBinarySearchTree();
        try {
            binTree.initialise(values);
        } catch (NegativeValuesNotAllowed E) {
            System.out.println("NegativeValuesNotAllowed Exception caught in createBinaryTreeAndInitialise:" + E.getMessage());
            //test will then fail as item lists will not match
        }
        return binTree;
    }
    private BinarySearchTree addItemsToBinarySearchTree(BinarySearchTree binTree, Integer[] values) {
        try {
            for (Integer value: values) {
                binTree.add(value);
            }
        } catch (NegativeValuesNotAllowed E) {
            System.out.println("NegativeValuesNotAllowed Exception caught in createBinaryTreeAndInitialise:" + E.getMessage());
            //test will then fail as item lists will not match
        }
        return binTree;
    }

}
