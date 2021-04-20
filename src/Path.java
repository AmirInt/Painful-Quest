import java.util.Stack;

public class Path {

    private Stack<Node> path;

    public Path(Node end) {
        path = new Stack<>();
        if (end != null)
            while (end != null) {
                path.push(end);
                end = end.getAncestor();
            }
    }

    public void push(Node newNode) {
        path.push(newNode);
    }

    public boolean isNotEmpty() {
        return !path.isEmpty();
    }

    public Node pop() {
        return path.pop();
    }

    public int getSize() {
        return path.size();
    }
}
