import java.util.ArrayList;
import java.util.LinkedList;

public class BiBFS {

    private final Environment environment;

    public BiBFS(Environment environment) {
        this.environment = environment;
    }

    public ArrayList<Path> searchPlates(Node butterPlate, Node goal) {

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
                        paths.add(createPath(successor, expanded));
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

        if (start.equals(end))
            return new Path(null);

        ArrayList<Node> explored = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        LinkedList<Node> headFringe = new LinkedList<>();
        LinkedList<Node> tailFringe = new LinkedList<>();
        Node expanded;

        start.setAncestor(null);
        end.setAncestor(null);
        headFringe.add(start);
        tailFringe.add(end);

        while (true) {

            expanded = headFringe.poll();
            if (expanded == null) {
                break;
            }
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
                        paths.add(createPath(successor, expanded));
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
        Node originalPlate = environment.getButterPlates().get(0);
        Node originalStart = environment.getStartingNode();

        for (Path path:
             searchPlates(environment.getButterPlates().get(0), environment.getGoals().get(0))) {

            environment.setStartingNode(originalStart);
            environment.getButterPlates().remove(0);
            environment.getButterPlates().add(originalPlate);

            Node n1, n2;
            LinkedList<Node> trialPath = new LinkedList<>();
            n1 = path.pop();
            n2 = path.pop();

            Path robotPath = searchRobot(environment.getStartingNode(), n1.getOppositeOf(n2));
            if (robotPath != null && robotPath.isNotEmpty()) {

                robotPath.pop();

                while (robotPath.isNotEmpty())
                    trialPath.add(robotPath.pop());

                environment.setStartingNode(trialPath.peekLast());
                System.out.println();
            }

            while (path.isNotEmpty()) {

                if (!n1.getOppositeOf(n2).equals(environment.getStartingNode())) {
                    robotPath = searchRobot(environment.getStartingNode(), n1.getOppositeOf(n2));
                    if (robotPath != null) {
                        robotPath.pop();

                        while (robotPath.isNotEmpty())
                            trialPath.add(robotPath.pop());

                        environment.setStartingNode(trialPath.peekLast());
                    }
                }
                trialPath.add(n1);
                environment.setStartingNode(n1);
                environment.getButterPlates().set(environment.getButterPlates().indexOf(n1), n2);
                n1 = n2;
                n2 = path.pop();
            }

            if (!n1.getOppositeOf(n2).equals(environment.getStartingNode())) {
                robotPath = searchRobot(environment.getStartingNode(), n1.getOppositeOf(n2));
                if (robotPath != null) {

                    robotPath.pop();

                    while (robotPath.isNotEmpty())
                        trialPath.add(robotPath.pop());

                }
                environment.setStartingNode(trialPath.peekLast());
            }
            trialPath.add(n1);

            if (trialPath.size() < finalPathExpense) {
                finalPath = trialPath;
                finalPathExpense = trialPath.size();
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
