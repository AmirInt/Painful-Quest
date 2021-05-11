import java.util.HashMap;

/**
 * Class Heap is used to sort the fringe list in A* search method in
 * falling order. It creates a min heap and takes advantage of the high
 * sorting speed.
 */
public class Heap {

//    Array of nodes to sort
    private final Node[] nodes;
//    Heap size
    private int size;
//    HashMap to store each node index in the original array
    private final HashMap<Node, Integer> indices;

    /**
     * Instantiates this class
     * @param n initial size of the array storing nodes
     */
    public Heap(int n) {
        nodes = new Node[n];
        size = 0;
        indices = new HashMap<>();
    }

    /**
     * @return The top element (the minimum of all nodes)
     */
    public Node getTop() {
        if (size == 0)
            return null;
        Node top = nodes[0];
        delete(nodes[0]);
        return top;
    }

    /**
     * Adds a new node to the heap
     * @param newNode New node to be added
     */
    public void add(Node newNode) {
        nodes[size] = newNode;
        indices.put(nodes[size], size);
        sortUp(size);
        ++size;
    }

    /**
     * Deletes a node anywhere in the heap
     * @param poorNode The node to be deleted
     */
    public void delete(Node poorNode) {
        int index = indices.get(poorNode);
        Node inter = nodes[index];
        nodes[index] = nodes[size - 1];
        nodes[size - 1] = inter;
        indices.put(nodes[index], index);
        indices.put(nodes[size - 1], size - 1);
        --size;
        if((index + 1) / 2 - 1 >= 0 && nodes[index].getF() < nodes[(index + 1) / 2 - 1].getF()) {
            sortUp(index);
        }
        else {
            sortDown(index);
        }
    }

    /**
     * Starts sorting the heap from a specific index up to the top element
     * @param index The starting index
     */
    private void sortUp(int index) {
        while ((index + 1) / 2 - 1 >= 0 && nodes[index].getF() < nodes[(index + 1) / 2 - 1].getF()) {
            Node inter = nodes[index];
            nodes[index] = nodes[(index + 1) / 2 - 1];
            nodes[(index + 1) / 2 - 1] = inter;
            indices.put(nodes[index], index);
            indices.put(nodes[(index + 1) / 2 - 1], (index + 1) / 2 - 1);
            index = (index + 1) / 2 - 1;
        }
    }

    /**
     * Starts sorting the heap from a specific element down to a leaf
     * @param index The starting index
     */
    private void sortDown(int index) {
        int left = (index + 1) * 2 - 1;
        int right = (index + 1) * 2;
        int smallest = index;
        if(left < size && nodes[smallest].getF() > nodes[left].getF())
            smallest = left;
        if(right < size && nodes[smallest].getF() > nodes[right].getF())
            smallest = right;
        if(smallest != index) {
            Node inter = nodes[index];
            nodes[index] = nodes[smallest];
            nodes[smallest] = inter;
            indices.put(nodes[index], index);
            indices.put(nodes[smallest], smallest);
            sortDown(smallest);
        }
    }

    /**
     * Checks if a specific node exists in the heap
     * @param sought The searched element
     * @return True if sought is inside the heap
     */
    public Node contains(Node sought) {
        for (int i = 0; i < size; ++i) {
            if (sought.equals(nodes[i]))
                return nodes[i];
        }
        return null;
    }
}
