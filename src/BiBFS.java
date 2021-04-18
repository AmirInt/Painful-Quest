import java.util.ArrayList;
import java.util.PriorityQueue;

public class BiBFS {

    private PriorityQueue<Node> headFringe;
    private PriorityQueue<Node> tailFringe;
    private ArrayList<Node> explored;
    private Environment environment;

    public BiBFS(Environment environment, Node start, Node goal) {
        this.environment = environment;
        headFringe = new PriorityQueue<>();
        tailFringe = new PriorityQueue<>();
        explored = new ArrayList<>();
    }

    private void search(Node start, Node end) {

        Node expanded;

        headFringe.clear();
        tailFringe.clear();
        headFringe.add(start);
        tailFringe.add(end);

        while (true) {

            expanded = headFringe.poll();
            if (expanded == null)
                break;
            for (Node successor:
                 expanded.getNeighbours()) {

                if (isViable(successor) && isNotExplored(successor) && isViable(expanded.getOppositeOf(successor))) {
                    if (compare(successor, tailFringe)) {
                        createPath(expanded, successor);
                        explored.add(successor);
                    }
                    else {
                        successor.setAncestor(expanded);
                        headFringe.add(successor);
                    }
                }
            }
            explored.add(expanded);

            expanded = tailFringe.poll();
            if (expanded == null)
                break;
            for (Node successor:
                    expanded.getNeighbours()) {

                if (isViable(successor) && isNotExplored(successor) && isViable(expanded.getOppositeOf(successor))) {
                    if (compare(successor, headFringe)) {
                        createPath(expanded, successor);
                        explored.add(successor);
                    }
                    else {
                        successor.setAncestor(expanded);
                        tailFringe.add(successor);
                    }
                }
            }
            explored.add(expanded);

        }
    }

    private boolean compare(Node node, PriorityQueue<Node> fringe) {
        for (Node fringeNode:
             fringe) {
            if (fringeNode.equals(node))
                return true;
        }
        return false;
    }

    private void createPath(Node oneEnd, Node theOtherEnd) {
    }

    private boolean isViable(Node node) {
        if (node == null || node.isObstacle())
            return false;
        for (Node butterPlate:
             environment.getButterPlates()) {
            if (node.equals(butterPlate))
                return false;
        }
        return true;
    }

    private boolean isNotExplored(Node node) {
        return !explored.contains(node);
    }
}
