import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class AS performs a search to find the overall optimum path to
 * get all butter plates to all goals through A* algorithm
 */
public class AS extends Search {

    public AS(Environment environment) {
        super(environment);
    }

    public ArrayList<Path> searchPlate(Node butterPlate, Node goal) {

//        Resets the environment to remove all nodes' ancestors
        environment.reset();

//        The explored set
        ArrayList<Node> explored = new ArrayList<>();
//        The ArrayList of all possible paths, initially empty
        ArrayList<Path> paths = new ArrayList<>();
//        The fringe list
        Heap fringe = new Heap(environment.getHeight() * environment.getWidth());
//        Represents the last expanded node
        Node expanded;

//        Sets the objective function for the starting point
//        and adds it to the fringe
        butterPlate.setF(calculateH(butterPlate, goal));
        fringe.add(butterPlate);

        while (true) {

//            Expands from fringe, the node with the minimum objective function
            expanded = fringe.getTop();
//            If nothing's left in the fringe, then search is over
            if (expanded == null)
                break;

//            Generating the descendants
            for (Node successor:
                    expanded.getNeighbours()) {

//                Setting the objective function for the current descendant
                int successorF = expanded.getF() - calculateH(expanded, goal) +
                        expanded.getExpense() + calculateH(successor, goal);

//                If this descendant is already in the fringe list, discards the node
//                with higher value, otherwise if it's already explored, simply discards it
//                and moves to the next descendant
                Node sought = fringe.contains(successor);
                if (sought != null) {
                    if (successorF < sought.getF()) {
                        fringe.delete(sought);
                        successor.setAncestor(expanded);
                        successor.setF(successorF);
                        fringe.add(successor);
                    }
                }
                else if (isNotExplored(explored, successor) && isViable(explored, successor) &&
                        isViable(explored, expanded.getOppositeOf(successor))) {
                    successor.setAncestor(expanded);
                    if (successor.equals(goal))
                        paths.add(new Path(successor));
                    else {
                        successor.setF(successorF);
                        fringe.add(successor);
                    }
                }
            }
            explored.add(expanded);

        }
        return paths;
    }

    public Path searchRobot(Node start, Node end) {

//        Resets the environment to clear nodes' ancestors
        environment.reset();

//        The explored set
        ArrayList<Node> explored = new ArrayList<>();
//        The fringe list
        Heap fringe = new Heap(environment.getHeight() * environment.getWidth());
//        Represents the expanded node each time
        Node expanded;

//        Sets the objective function for the starting point
//        and adds it to the fringe
        start.setF(calculateH(start, end));
        fringe.add(start);

        while (true) {

//            Expands from fringe, the node with the minimum objective function
            expanded = fringe.getTop();

//            If nothing's left in the fringe, then search is over
            if (expanded == null)
                return null;

//            Checks if the expanded node equals the end, if true creates a path
//            and returns it, as it's the shortest path
            if (expanded.equals(end))
                return new Path(end);

//            Generating the descendants
            for (Node successor:
                    expanded.getNeighbours()) {

//                Setting the objective function for the current descendant
                int successorF = expanded.getF() - calculateH(expanded, end) +
                        expanded.getExpense() + calculateH(successor, end);

//                If this descendant is already in the fringe list, discards the node
//                with higher value, otherwise if it's already explored, simply discards it
//                and moves to the next descendant
                Node sought = fringe.contains(successor);
                if (sought != null) {
                    if (successorF < sought.getF()) {
                        fringe.delete(sought);
                        successor.setAncestor(expanded);
                        successor.setF(successorF);
                        fringe.add(successor);
                    }
                }
                else if (isNotExplored(explored, successor) && isViable(explored, successor)) {
                    successor.setAncestor(expanded);
                    successor.setF(successorF);
                    fringe.add(successor);
                }
            }
            explored.add(expanded);

        }
    }

    public LinkedList<Node> search() {
        return super.search();
    }

    /**
     * Calculates the heuristic function for the given node based
     * on the given goal node
     * @param current The node whose heuristic is to be calculated
     * @param Goal The goal node
     * @return The value of the current node heuristic
     */
    private int calculateH(Node current, Node Goal) {
        return Math.abs(current.getX() - Goal.getX()) + Math.abs(current.getY() - Goal.getY());
    }
}
