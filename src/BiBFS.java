import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class BiBFS performs a bidirectional search based on the BFS algorithm
 */
public class BiBFS extends Search {

    /**
     * Instantiates this class
     * @param environment The searching environment
     */
    public BiBFS(Environment environment) {
        super(environment);
    }

    /**
     * Searches to find all possible (optimum) paths from the given
     * butter plate to the given goal node
     * @param butterPlate The butter plate to start form
     * @param goal The goal node to be reached
     * @return An ArrayList of all the possible paths from "butterPlate" to "goal"
     */
    @Override
    public ArrayList<Path> searchPlate(Node butterPlate, Node goal) {

//        Resets the environment to remove all nodes' ancestors
        environment.reset();

//        The explored set
        ArrayList<Node> explored = new ArrayList<>();
//        The ArrayList of all possible paths, initially empty
        ArrayList<Path> paths = new ArrayList<>();
//        The two fringe lists for the two searching sides
        LinkedList<Node> headFringe = new LinkedList<>();
        LinkedList<Node> tailFringe = new LinkedList<>();
        headFringe.add(butterPlate);
        tailFringe.add(goal);
//        Represents the last expanded node
        Node expanded;


        while (true) {

//            Moving ahead from the head side
            expanded = headFringe.poll();
//            Search ends if there is no node left in the fringe list to be expanded
            if (expanded == null)
                break;

//            Generating the descendants
            for (Node successor:
                 expanded.getNeighbours()) {
                if (isNotExplored(explored, successor) && !headFringe.contains(successor) &&
                        isViable(explored, expanded.getOppositeOf(successor)) && isViable(explored, successor)) {
                    if (compare(successor, tailFringe)) {
//                        If this descendant exist among the tail fringe nodes
//                        creates a path and adds it to the set of paths
                        paths.add(createPath(expanded, successor));
                    }
                    else {
//                        If this descendant is not found among the tail fringe nodes
//                        it's added to the fringe list to be expanded later
                        successor.setAncestor(expanded);
                        headFringe.add(successor);
                    }
                }
            }
            explored.add(expanded);

//            Moving ahead from the tail side
            expanded = tailFringe.poll();
//            Search ends if there is no node left in the fringe list to be expanded
            if (expanded == null)
                break;

//            Generating the descendants
            for (Node successor:
                    expanded.getNeighbours()) {
                if (!tailFringe.contains(successor) && isNotExplored(explored, successor) &&
                        isViable(explored, successor) && isViable(explored, successor.getOppositeOf(expanded))) {
                    if (compare(successor, headFringe)) {
//                        If this descendant exist among the head fringe nodes
//                        creates a path and adds it to the set of paths
                        paths.add(createPath(successor, expanded));
                    }
                    else {
//                        If this descendant is not found among the tail fringe nodes
//                        it's added to the fringe list to be expanded later
                        successor.setAncestor(expanded);
                        tailFringe.add(successor);
                    }
                }
            }
            explored.add(expanded);

        }
        return paths;
    }

    @Override
    public Path searchRobot(Node start, Node end) {

//        Resets the environment to clear nodes' ancestors
        environment.reset();

//        If start and end nodes are the same creates ta path and immediately
//        returns it
        if (start.equals(end))
            return new Path(start);

//        The explored set
        ArrayList<Node> explored = new ArrayList<>();
//        The set of paths found
        ArrayList<Path> paths = new ArrayList<>();
//        The two fringe lists for the two sides of the search
        LinkedList<Node> headFringe = new LinkedList<>();
        LinkedList<Node> tailFringe = new LinkedList<>();
        headFringe.add(start);
        tailFringe.add(end);
//        Represents the expanded node each time
        Node expanded;


        while (true) {

//            Moving ahead from the head side
            expanded = headFringe.poll();

//            Search ends if there is no node left in the fringe list to be expanded
            if (expanded == null) {
                break;
            }

//            Generating the descendants
            for (Node successor:
                    expanded.getNeighbours()) {

                if (isNotExplored(explored, successor) && !headFringe.contains(successor)
                        && isViable(explored, successor)) {
                    if (compare(successor, tailFringe)) {
//                        If this descendant exist among the tail fringe nodes
//                        creates a path and adds it to the set of paths
                        paths.add(createPath(expanded, successor));
                    }
                    else {
//                        If this descendant is not found among the tail fringe nodes
//                        it's added to the fringe list to be expanded later
                        successor.setAncestor(expanded);
                        headFringe.add(successor);
                    }
                }
            }
            explored.add(expanded);

//            Moving ahead from the head side
            expanded = tailFringe.poll();

//            Search ends if there is no node left in the fringe list to be expanded
            if (expanded == null)
                break;

//            Generating the descendants
            for (Node successor:
                    expanded.getNeighbours()) {

                if (isViable(explored, successor) && !tailFringe.contains(successor)
                        && isNotExplored(explored, successor)) {
                    if (compare(successor, headFringe)) {
//                        If this descendant exist among the head fringe nodes
//                        creates a path and adds it to the set of paths
                        paths.add(createPath(successor, expanded));
                    }
                    else {
//                        If this descendant is not found among the head fringe nodes
//                        it's added to the fringe list to be expanded later
                        successor.setAncestor(expanded);
                        tailFringe.add(successor);
                    }
                }
            }
            explored.add(expanded);

        }

//        Choosing the optimum path among the found paths
        Path finalPath = null;
        for (Path path:
             paths) {
            if (finalPath == null)
                finalPath = path;
            else if (finalPath.getSize() > path.getSize())
                finalPath = path;
        }
        return finalPath;
    }

    @Override
    public LinkedList<Node> search() {
        return super.search();
    }

    /**
     * Creates a path by joining the two ends of the paths found by
     * the two sides (head and tail) in BiBFS
     * @param oneEnd The end of one paths
     * @param theOtherEnd The end of the other path
     * @return The overall path
     */
    private Path createPath(Node oneEnd, Node theOtherEnd) {
        Path aux = new Path(theOtherEnd);
        Path path = new Path(null);
        while (aux.isNotEmpty()) {
            path.push(aux.pop());
        }
        while (oneEnd != null) {
            path.push(oneEnd);
            oneEnd = oneEnd.getAncestor();
        }
        return path;
    }
}
