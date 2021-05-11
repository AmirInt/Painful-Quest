import java.util.ArrayList;
import java.util.LinkedList;

public class AS {

    private final Environment environment;

    public AS(Environment environment) {
        this.environment = environment;
    }

    public ArrayList<Path> searchPlates(Node butterPlate, Node goal) {

        environment.reset();

        ArrayList<Node> explored = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        Heap fringe = new Heap(environment.getHeight() * environment.getWidth());
        Node expanded;

        butterPlate.setF(calculateH(butterPlate, goal));
        fringe.add(butterPlate);

        while (true) {

            expanded = fringe.getTop();
            if (expanded == null)
                break;

            for (Node successor:
                    expanded.getNeighbours()) {


                int successorF = expanded.getF() - calculateH(expanded, goal) +
                        expanded.getExpense() + calculateH(successor, goal);

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

        environment.reset();

        ArrayList<Node> explored = new ArrayList<>();
        Heap fringe = new Heap(environment.getHeight() * environment.getWidth());
        Node expanded;

        start.setAncestor(null);

        start.setF(calculateH(start, end));
        fringe.add(start);

        while (true) {

            expanded = fringe.getTop();

            if (expanded == null)
                return null;

            if (expanded.equals(end))
                return new Path(end);

            for (Node successor:
                    expanded.getNeighbours()) {

                int successorF = expanded.getF() - calculateH(expanded, end) +
                        expanded.getExpense() + calculateH(successor, end);

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

        LinkedList<Node> finalPath = new LinkedList<>();
        LinkedList<Node> partialPath;
        Node originalPlate;
        Node nextStartingPoint = environment.getStartingNode();
        int selectedButterPlate = 0, selectedGoal = 0;
        int partialPathSize;

        while (environment.getButterPlates().size() > 0 && environment.getGoals().size() > 0) {

            partialPathSize = Integer.MAX_VALUE;
            partialPath = new LinkedList<>();

            for (int i = 0; i < environment.getButterPlates().size(); ++i) {

                originalPlate = environment.getButterPlates().get(i);

                for (int j = 0; j < environment.getGoals().size(); ++j) {

                    for (Path path :
                            searchPlates(originalPlate, environment.getGoals().get(j))) {

                        Node n1, n2;
                        LinkedList<Node> trialPath = new LinkedList<>();
                        n1 = path.pop();
                        n2 = path.pop();

                        assert nextStartingPoint != null;
                        Path robotPath = searchRobot(nextStartingPoint, n1.getOppositeOf(n2));
                        if (robotPath == null)
                            continue;

                        robotPath.pop();
                        while (robotPath.isNotEmpty())
                            trialPath.add(robotPath.pop());

                        if (trialPath.size() > 0)
                            environment.setStartingNode(trialPath.peekLast());

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

                        if (trialPath.size() < partialPathSize) {
                            partialPath = new LinkedList<>(trialPath);
                            partialPathSize = trialPath.size();
                            selectedButterPlate = i;
                            selectedGoal = j;
                        }

                        environment.getButterPlates().set(i, originalPlate);
                        environment.setStartingNode(nextStartingPoint);
                    }

                }
            }

            if (partialPath.size() == 0)
                return null;

            finalPath.addAll(partialPath);
            nextStartingPoint = partialPath.peekLast();
            environment.getButterPlates().remove(selectedButterPlate);
            environment.getGoals().get(selectedGoal).setObstacle(true);
            environment.getGoals().remove(selectedGoal);

        }
        return finalPath;
    }

    private int calculateH(Node current, Node Goal) {
        return Math.abs(current.getX() - Goal.getX()) + Math.abs(current.getY() - Goal.getY());
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
