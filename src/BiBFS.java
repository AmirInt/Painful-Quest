import java.util.ArrayList;
import java.util.LinkedList;

public class BiBFS {

    private Environment environment;

    public BiBFS(Environment environment) {
        this.environment = environment;
    }

    private ArrayList<Path> searchPlates(Node butterPlate, Node goal) {

        ArrayList<Node> explored = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        LinkedList<Node> headFringe = new LinkedList<>();
        LinkedList<Node> tailFringe = new LinkedList<>();
        Node expanded;

        headFringe.add(butterPlate);
        tailFringe.add(goal);

        while (true) {

            expanded = headFringe.poll();
            if (expanded == null)
                break;
            for (Node successor:
                 expanded.getNeighbours()) {

                if (isNotExplored(explored, successor) && isViable(explored, successor) && isViable(explored, expanded.getOppositeOf(successor))) {
                    if (compare(successor, tailFringe)) {
                        paths.add(createPath(expanded, successor));
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

                if (isViable(explored, successor) && isNotExplored(explored, successor) && isViable(explored, successor.getOppositeOf(expanded))) {
                    if (compare(successor, headFringe)) {
                        paths.add(createPath(expanded, successor));
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
        return paths;
    }

    public Path searchRobot(Node start, Node end) {

        System.out.println("inside robot search");
        ArrayList<Node> explored = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        LinkedList<Node> headFringe = new LinkedList<>();
        LinkedList<Node> tailFringe = new LinkedList<>();
        Node expanded;

        headFringe.add(start);
        tailFringe.add(end);

        while (true) {

            expanded = headFringe.poll();
            if (expanded == null) {
                break;
            }
            System.out.println("expanded: " + expanded.getX() + "," + expanded.getY());
            for (Node successor:
                    expanded.getNeighbours()) {

                if (isNotExplored(explored, successor) && !headFringe.contains(successor)
                        && isViable(explored, successor)) {
                    if (compare(successor, tailFringe)) {
                        paths.add(createPath(expanded, successor));
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

                if (isViable(explored, successor) && !tailFringe.contains(successor)
                        && isNotExplored(explored, successor)) {
                    if (compare(successor, headFringe)) {
                        paths.add(createPath(expanded, successor));
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

    public LinkedList<Node> search() {

        LinkedList<Node> finalPath = new LinkedList<>();
        int finalPathExpense = Integer.MAX_VALUE;

        for (Path path:
             searchPlates(environment.getButterPlates().get(0), environment.getGoals().get(0))) {
            System.out.println("one plate path started");
            Node n1, n2;
            LinkedList<Node> trialPath = new LinkedList<>();
            int trialPathSize = 0;
            n1 = path.pop();
            n2 = path.pop();
            Path robotPath = searchRobot(environment.getStartingNode(), n1.getOppositeOf(n2));
            robotPath.pop();
            Node robotPathNode = robotPath.pop();
            while (robotPath.isNotEmpty()) {
                trialPath.add(robotPathNode);
                ++trialPathSize;
                robotPathNode = robotPath.pop();
            }

            while (path.isNotEmpty()) {
                System.out.println("n1: " + n1.getX() + "," + n1.getY());
                System.out.println("n2: " + n2.getX() + "," + n2.getY());
                if (!n1.getOppositeOf(n2).equals(trialPath.peekLast())) {
                    System.out.println(trialPath.peekLast().getX() + "," + trialPath.peekLast().getY());
                    robotPath = searchRobot(trialPath.peekLast(), n1.getOppositeOf(n2));
                    System.out.println("robot search done");
                    robotPath.pop();
                    while (robotPath.isNotEmpty()) {
                        trialPath.add(robotPath.pop());
                        ++trialPathSize;
                    }
                }
                trialPath.add(n1);
                ++trialPathSize;
                n1 = n2;
                n2 = path.pop();
            }
            System.out.println("n1: " + n1.getX() + "," + n1.getY());
            System.out.println("n2: " + n2.getX() + "," + n2.getY());
            if (!n1.getOppositeOf(n2).equals(trialPath.peekLast())) {
                System.out.println(trialPath.peekLast().getX() + "," + trialPath.peekLast().getY());
                robotPath = searchRobot(trialPath.peekLast(), n1.getOppositeOf(n2));
                robotPath.pop();
                while (robotPath.isNotEmpty()) {
                    trialPath.add(robotPath.pop());
                    ++trialPathSize;
                }
                System.out.println("robot search done");
            }
            trialPath.add(n1);
            ++trialPathSize;

            trialPath.add(n1);
            ++trialPathSize;

            if (trialPathSize < finalPathExpense) {
                finalPath = trialPath;
                finalPathExpense = trialPathSize;
            }
        }

        return finalPath;
    }

    private boolean compare(Node node, LinkedList<Node> fringe) {
        for (Node fringeNode:
             fringe) {
            if (fringeNode.equals(node))
                return true;
        }
        return false;
    }

    private Path createPath(Node oneEnd, Node theOtherEnd) {
        Path aux = new Path(oneEnd);
        Path path = new Path(null);
        while (aux.isNotEmpty()) {
            path.push(aux.pop());
        }
        while (theOtherEnd != null) {
            path.push(theOtherEnd);
            theOtherEnd = theOtherEnd.getAncestor();
        }
        return path;
    }

    private boolean isViable(ArrayList<Node> explored, Node node) {
        if (node == null || node.isObstacle())
            return false;
        for (Node butterPlate:
             environment.getButterPlates()) {
            if (!explored.contains(butterPlate) && node.equals(butterPlate))
                return false;
        }
        return true;
    }

    private boolean isNotExplored(ArrayList<Node> explored, Node node) {
        return !explored.contains(node);
    }
}
