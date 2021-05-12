import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class Search is the general searching unit that deploys its
 * subclasses to perform a search process based on the desired
 * algorithms
 */
public class Search {

    protected Environment environment;

    public Search(Environment environment) {
        this.environment = environment;
    }

    /**
     * Searches to find all possible (optimum) paths from the given
     * butter plate to the given goal node
     * @param butterPlate The butter plate to start form
     * @param goal The goal node to be reached
     * @return An ArrayList of all the possible paths from "butterPlate" to "goal"
     */
    public ArrayList<Path> searchPlate(Node butterPlate, Node goal) {
        return null;
    }

    /**
     * Searches to find the optimum path that takes the agent to a given goal
     * @param start The starting point where the robot is currently standing on
     * @param end The goal node
     * @return A Path representing the optimum path, null if no path were available
     */
    public Path searchRobot(Node start, Node end) {
        return null;
    }

    /**
     * Generally searches to find the final optimum path that helps robot take
     * all the butter plates to all the goals residing closest.
     * The search consists of different stages. In each of these stages, one butter
     * plate is taken to on goal, deleted from the list of butters and the corresponding
     * goal is become an obstacle and deleted from the list of goals.
     * @return The final optimum path, null if it's not viable to get all them butters to
     * the goals
     */
    public LinkedList<Node> search() {

//        finalPath holds the optimum final path the robot traverses to
//        get all the butter plates to all the goals (stores the nodes in
//        the path)
        LinkedList<Node> finalPath = new LinkedList<>();
//        Represents the optimum path through which the agent takes "one"
//        butter plate to a goal in each stage
        LinkedList<Node> partialPath;
        int partialPathSize;
//        Stores the original plate for each stage to be reversible
        Node originalPlate;
//        Stores the next starting point for the robot after one plate is taken to
//        a goal at the end of each stage
        Node nextStartingPoint = environment.getStartingNode();
//        Represent the index of the plate and goal selected in each stage
        int selectedButterPlate = 0, selectedGoal = 0;

//        As long as there is an unsatisfied goal the search continues
        while (environment.getButterPlates().size() > 0 && environment.getGoals().size() > 0) {

//            No partial path found so far!
            partialPathSize = Integer.MAX_VALUE;
            partialPath = new LinkedList<>();

//            Loops on the list of the plates present
            for (int i = 0; i < environment.getButterPlates().size(); ++i) {

//                Sets originalPlate to the current plate so that all actions taken
//                in this stage are reversible
                originalPlate = environment.getButterPlates().get(i);

//                For each butter, a search takes place for each of the goals
//                and at the end the best goal with the optimum path is selected
                for (int j = 0; j < environment.getGoals().size(); ++j) {

//                    All of the possible ways to get the current plate to the current goal
//                    are traversed and "tried" by the robot so that the best of them is selected
                    for (Path path :
                            searchPlate(originalPlate, environment.getGoals().get(j))) {

                        Node n1, n2;
                        LinkedList<Node> trialPath = new LinkedList<>();
                        n1 = path.pop();
                        n2 = path.pop();

//                        Searching for robot to reach the plate
                        assert nextStartingPoint != null;
                        Path robotPath = searchRobot(nextStartingPoint, n1.getOppositeOf(n2));

                        if (robotPath == null)
                            continue;

//                        Adding robot path to trial path
                        robotPath.pop();
                        while (robotPath.isNotEmpty())
                            trialPath.add(robotPath.pop());

                        if (trialPath.size() > 0)
                            environment.setStartingNode(trialPath.peekLast());

//                        Traversing the rest of the path from plate to goal
                        while (!n1.equals(environment.getGoals().get(j))) {
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
                            if (path.isNotEmpty())
                                n2 = path.pop();
                        }
//                        Finishing the traverse

//                        If this path found is shorter than paths found previously then sets this
//                        as the selected path so far
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

//            If the no path for the current goal was found, then it's impossible to
//            do the mission. So returns null under such circumstances
            if (partialPath.size() == 0)
                return null;

//            If a path was found for the current goal, adds it the final path
            finalPath.addAll(partialPath);
            nextStartingPoint = partialPath.peekLast();
            environment.getButterPlates().remove(selectedButterPlate);
            environment.getGoals().get(selectedGoal).setObstacle(true);
            environment.getGoals().remove(selectedGoal);

        }
        return finalPath;
    }

    /**
     * Compares the given node with the nodes in the given fringe list
     * @param node The node to be compared
     * @param fringe The fringe list to look in
     * @return True if the given node is in the fringe list
     */
    protected boolean compare(Node node, LinkedList<Node> fringe) {
        for (Node fringeNode:
                fringe) {
            if (fringeNode.equals(node))
                return true;
        }
        return false;
    }

    /**
     * Checks if the given node is possible to move on
     * @param explored The explored set of nodes
     * @param node The given node
     * @return True if agent of plate can move to the given node
     */
    protected boolean isViable(ArrayList<Node> explored, Node node) {
        if (node == null || node.isObstacle())
            return false;
        for (Node butterPlate:
                environment.getButterPlates()) {
            if (!explored.contains(butterPlate) && node.equals(butterPlate))
                return false;
        }
        return true;
    }

    /**
     * Checks if the given node is explored
     * @param explored The explored set of nodes
     * @param node The node to checked
     * @return True if the given node is not yet explored
     */
    protected boolean isNotExplored(ArrayList<Node> explored, Node node) {
        return !explored.contains(node);
    }
}
