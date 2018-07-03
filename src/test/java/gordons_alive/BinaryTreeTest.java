package gordons_alive;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

//other tests - handles null items

public class BinaryTreeTest {
    SearchTreeFactory factory = new SearchTreeFactory();
    @Test
    public void canCreateNewBinaryTree() {
        assertNotNull(factory.newBinaryTree());
    }
    @Test
    public void canBeInitialisedWithASingleInteger() {
        Integer[] itemArray = {1};
        ValuesVsResultsRecord valuesAndResultsSorted = createBinaryTreeInitialiseAndSortResults(itemArray);
        assertArrayEquals(valuesAndResultsSorted.values, valuesAndResultsSorted.results);
    }
    @Test
    public void handlesEmptyArray() {
        Integer[] itemArray = {};
        ValuesVsResultsRecord valuesAndResultsSorted = createBinaryTreeInitialiseAndSortResults(itemArray);
        assertArrayEquals(valuesAndResultsSorted.values, valuesAndResultsSorted.results);
    }
    @Test(expected = NegativeValuesNotAllowed.class)
    public void handlesArrayWithNegativeItem() throws NegativeValuesNotAllowed{
        Integer[] itemArray = {1,-2};
        BinaryTree binTree = factory.newBinaryTree();
        binTree.initialise(itemArray);
    }
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    @Test
    public void handlesArrayWithNegativeItemWithCorrectMessage() throws Exception{
        expectedEx.expect(NegativeValuesNotAllowed.class);
        expectedEx.expectMessage("value -2 is negative and cannot be added to the tree");
        Integer[] itemArray = {1,-2};
        BinaryTree binTree = factory.newBinaryTree();
        binTree.initialise(itemArray);
    }
    @Test
    public void canBeInitialisedWithTwoIntegers(){
        Integer[] itemArray = {1,2};
        ValuesVsResultsRecord valuesAndResultsSorted = createBinaryTreeInitialiseAndSortResults(itemArray);
        assertArrayEquals(valuesAndResultsSorted.values, valuesAndResultsSorted.results);
    }
    @Test
    public void canBeInitialisedWithManyUnorderedIntegers(){
        Integer[] itemArray = {3,2,5,6,1,9,7};
        ValuesVsResultsRecord valuesAndResultsSorted = createBinaryTreeInitialiseAndSortResults(itemArray);
        assertArrayEquals(valuesAndResultsSorted.values, valuesAndResultsSorted.results);
    }
    @Test
    public void canBeInitialisedWithListContainingDuplicates(){
        Integer[] itemArray = {3,2,5,6,1,9,5,4,4,8,3};
        ValuesVsResultsRecord valuesAndResultsSorted = createBinaryTreeInitialiseAndSortResults(itemArray);
        assertArrayEquals(valuesAndResultsSorted.values, valuesAndResultsSorted.results);
    }
    @Test
    public void canAddASingleItem() {
        Integer[] initialvalues = {};
        Integer[] additions = {1};
        ValuesVsResultsRecord valuesAndResults = createBinaryTreeInitialiseAndAddAndSortResults(initialvalues, additions);
        assertArrayEquals(valuesAndResults.values, valuesAndResults.results);
    }
    @Test
    public void canAddMutipleUnorderedItems() {
        Integer[] initialvalues = {};
        Integer[] additions = {3,1,5,2,4,7,8,6};
        ValuesVsResultsRecord valuesAndResults = createBinaryTreeInitialiseAndAddAndSortResults(initialvalues, additions);
        assertArrayEquals(valuesAndResults.values, valuesAndResults.results);
    }
    @Test
    public void canAddMutipleUnorderedItemsWithDuplicates() {
        Integer[] initialvalues = {};
        Integer[] additions = {3,1,5,1,2,4,4,7,8,6,3};
        ValuesVsResultsRecord valuesAndResults = createBinaryTreeInitialiseAndAddAndSortResults(initialvalues, additions);
        assertArrayEquals(valuesAndResults.values, valuesAndResults.results);
    }
    @Test
    public void canAddASingleItemToInitisalisedTree() {
        Integer[] initialvalues = {3};
        Integer[] additions = {1};
        ValuesVsResultsRecord valuesAndResults = createBinaryTreeInitialiseAndAddAndSortResults(initialvalues, additions);
        assertArrayEquals(valuesAndResults.values, valuesAndResults.results);
    }
    @Test
    public void canAddMutipleUnorderedItemsToInitisalisedTree() {
        Integer[] initialvalues = {6,3,5,2};
        Integer[] additions = {3,1,5,2,4,7,8,6};
        ValuesVsResultsRecord valuesAndResults = createBinaryTreeInitialiseAndAddAndSortResults(initialvalues, additions);
        assertArrayEquals(valuesAndResults.values, valuesAndResults.results);
    }
    @Test
    public void canAddMutipleUnorderedItemsWithDuplicatesToInitisalisedTree() {
        Integer[] initialvalues = {3,1,5,1,2,4,4,7,8,6,3};
        Integer[] additions = {3,1,5,1,2,4,4,7,8,6,3};
        ValuesVsResultsRecord valuesAndResults = createBinaryTreeInitialiseAndAddAndSortResults(initialvalues, additions);
        assertArrayEquals(valuesAndResults.values, valuesAndResults.results);
    }
    @Test(expected = NegativeValuesNotAllowed.class)
    public void handlesAddingArrayWithNegativeItem() throws NegativeValuesNotAllowed{
        Integer[] additions = {1,-2};
        BinaryTree binTree = factory.newBinaryTree();
        for (Integer item: additions) {
            binTree.add(item);
        }
    }
//    @Rule
//    public ExpectedException expectedEx = ExpectedException.none();
    @Test
    public void handlesAddingArrayWithNegativeItemWithCorrectMessage() throws Exception{
        expectedEx.expect(NegativeValuesNotAllowed.class);
        expectedEx.expectMessage("value -2 is negative and cannot be added to the tree");
        Integer[] initialValues = {3,1,2};
        Integer[] additions = {1,-2};
        BinaryTree binTree = factory.newBinaryTree();
        binTree.initialise(initialValues);
        for (Integer item: additions) {
            binTree.add(item);
        }
    }

    // HELPER FUNCTIONS -----
    private ValuesVsResultsRecord createAndSortValuesAndResults(Integer[] values, Integer[] results) {
        ValuesVsResultsRecord valuesVsResults = new ValuesVsResultsRecord(values, results);
        Arrays.sort(valuesVsResults.values);
        Arrays.sort(valuesVsResults.results);
        return valuesVsResults;
    }
    private ValuesVsResultsRecord createBinaryTreeInitialiseAndSortResults(Integer[] values) {
        //create and initialise the binary tree
        BinaryTree binTree = createBinaryTreeAndInitialise(values);
        //create the comparison results record and sort the items ready for return
        return createAndSortValuesAndResults(values, binTree.values());
    }
    private ValuesVsResultsRecord createBinaryTreeInitialiseAndAddAndSortResults(Integer[] initialValues, Integer[] additions) {
        //create and initialise the binary tree
        BinaryTree binTree = createBinaryTreeAndInitialise(initialValues);
        //add all the additions
        binTree = addItemsToBinaryTree(binTree, additions);
        //create the comparison results record and sort the items ready for return
        List<Integer> valuesList = new ArrayList<>(Arrays.asList(initialValues));
        List<Integer> additionsList = new ArrayList<>(Arrays.asList(additions));
        valuesList.addAll(additionsList);
        Integer[] values = valuesList.toArray(new Integer[0]);
        return createAndSortValuesAndResults(values, binTree.values());
    }
    private BinaryTree createBinaryTreeAndInitialise(Integer[] values) {
        BinaryTree binTree = factory.newBinaryTree();
        //TODO: add a try block in here don't use this if you actually want the exception
        try {
            binTree.initialise(values);
        } catch (NegativeValuesNotAllowed E) {
            System.out.println("NegativeValuesNotAllowed Exception caught in createBinaryTreeAndInitialise:" + E.getMessage());
            //test will then fail as item lists will not match
        }
        return binTree;
    }
    private BinaryTree addItemsToBinaryTree(BinaryTree binTree, Integer[] values) {
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
class ValuesVsResultsRecord {
    Integer[] values;
    Integer[] results;
    ValuesVsResultsRecord(Integer[] values, Integer[] results) {
        this.values = values;
        this.results = results;
    }
}
