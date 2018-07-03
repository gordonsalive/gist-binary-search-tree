package gordons_alive;

public interface BinaryTree {
    void initialise(Integer[] values) throws NegativeValuesNotAllowed;
    void add(Integer value) throws NegativeValuesNotAllowed;
    Integer[] values();
}
