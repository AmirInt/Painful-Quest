import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class IDS extends Search {

    public IDS(Environment environment) {
        super(environment);
    }

    private int limit = environment.getHeight() * environment.getWidth();

    public Path searchRobot(Node start, Node end) {
        limit = environment.getHeight() * environment.getWidth();
        for (int i = 0; i <= limit; ++i) {
            if (limitedDFS(start, end, i))
                return new Path(end);
        }
        return null;
    }

    /**
     * Implements the limited version of DFS that is to be called in IDS.
     * If we can reach the goal in the given limited depth it returns true otherwise it returns false
     * @param start Starting point
     * @param end Goal node
     * @param limit Depth limit within which search can continue
     * @return True if we can reach the goal in the given depth limit, false otherwise
     */
    private boolean limitedDFS(Node start, Node end, int limit) {

//        Resets the environment to remove all nodes' ancestors
        environment.reset();
//        The explored set
        ArrayList<Node> explored = new ArrayList<>();
//        The two fringe lists for the two searching sides
        Stack<Node> fringe = new Stack<>();
        start.setDepth(0);
        fringe.push(start);
//        Represents the last expanded node
        Node expanded;

        while (!fringe.isEmpty()) {

            expanded = fringe.pop();
            if (expanded.equals(end)) {
                return true;
            }

            if (expanded.getDepth() == limit)
                continue;

//            Generating the descendants
            for (Node child:
                    expanded.getNeighbours()) {
                if ((isNotExplored(explored, child) || expanded.getDepth() + 1 <= child.getDepth()) &&
                        !fringe.contains(child) && isViable(explored, child)) {
                    child.setAncestor(expanded);
                    child.setDepth(expanded.getDepth() + 1);
                    fringe.push(child);
                }
            }
            explored.add(expanded);
        }
        return false;
    }

    public ArrayList<Path> searchPlate(Node butterPlate, Node goal) {

//        The ArrayList of all possible paths, initially empty
        ArrayList<Path> paths = new ArrayList<>();

        for (int i = 0; i < limit; ++i) {
//            Resets the environment to remove all nodes' ancestors
            environment.reset();
//            The explored set
            ArrayList<Node> explored = new ArrayList<>();
//            The two fringe lists for the two searching sides
            Stack<Node> fringe = new Stack<>();
            butterPlate.setDepth(0);
            fringe.push(butterPlate);
//            Represents the last expanded node
            Node expanded;

            while (!fringe.isEmpty()) {

                expanded = fringe.pop();
                if (expanded.equals(goal)) {
                    paths.add(new Path(expanded));
                    limit = expanded.getDepth();
                    continue;
                }

                if (expanded.getDepth() == i)
                    continue;

//                Generating the descendants
                for (Node child:
                        expanded.getNeighbours()) {
                    if ((isNotExplored(explored, child) || expanded.getDepth() + 1 <= child.getDepth()) && !fringe.contains(child) &&
                            isViable(explored, expanded.getOppositeOf(child)) && isViable(explored, child)) {
                        child.setAncestor(expanded);
                        child.setDepth(expanded.getDepth() + 1);
                        fringe.push(child);
                    }
                }
                explored.add(expanded);
            }
        }

        return paths;
    }

    @Override
    public LinkedList<Node> search() {
        return super.search();
    }
}
