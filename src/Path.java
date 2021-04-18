import java.util.Stack;

public class Path {

    private Stack<Node> path;

    public Path() {
        path = new Stack<>();
    }

    public void push(Node newNode) {
        path.push(newNode);
    }

    public Node peek() {
        return path.peek();
    }

    public Node pop() {
        return path.pop();
    }
}
