import java.util.Stack;

/**
 * Class path represents a viable path between two nodes
 */
public class Path {

//    The stack storing nodes of this path
    private final Stack<Node> path;

    /**
     * Instantiates this class
     * @param end The end of the path
     */
    public Path(Node end) {
        path = new Stack<>();
        while (end != null) {
            path.push(end);
            end = end.getAncestor();
        }
    }

    /**
     * Pushes a node to the top of the path stack
     * @param newNode The new node
     */
    public void push(Node newNode) {
        path.push(newNode);
    }

    /**
     * @return True if this path has at least on node
     */
    public boolean isNotEmpty() {
        return !path.isEmpty();
    }

    /**
     * Pops a node from the path stack
     * @return The popped node
     */
    public Node pop() {
        return path.pop();
    }

    /**
     * @return The size of this path
     */
    public int getSize() {
        return path.size();
    }

    /**
     * Offers a string representation of nodes of this path
     * @return String depicting this path's nodes' coordinates
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        while(isNotEmpty())
            stringBuilder.append(path.pop()).append("    ");
        return stringBuilder.toString();
    }
}
