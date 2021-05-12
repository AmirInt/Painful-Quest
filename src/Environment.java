import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class Environment represents the search space (desk)
 */
public class Environment {

//    A 2D array representation of the desk nodes
    private final Node[][] block;
//    Where robot is initially standing
    private Node startingNode;
//    The set of goals waiting for butter!
    private final ArrayList<Node> goals;
//    The set of butter plates on the desk
    private final ArrayList<Node> butterPlates;
//    The width and height of this desk
    private final int height, width;

    /**
     * Instantiates this class
     * @param height The height of the desk
     * @param width The width of the desk
     * @param scanner A Scanner object to read input
     */
    public Environment(int height, int width, Scanner scanner) {
        this.height = height;
        this.width = width;
        block = new Node[height][width];
        goals = new ArrayList<>();
        butterPlates = new ArrayList<>();
        setNodesArray(height, width, scanner);
        setUpGraph(height, width);
    }

    /**
     * Sets up "block"
     * @param height The height of the desk
     * @param width The width of the desk
     * @param scanner A Scanner object to read input
     */
    private void setNodesArray(int height, int width, Scanner scanner) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node newNode = new Node(j, i);
                String position = scanner.next();
                if (position.equals("x")) {
                    newNode.setObstacle(true);
                }
                else {
                    char c = position.charAt(position.length() - 1);
                    switch (c) {
                        case 'r': startingNode = newNode;
                        case 'b': butterPlates.add(newNode);
                        case 'p': goals.add(newNode);
                    }
                    int expense;
                    if(c == 'b' || c == 'p' || c == 'r') {
                        expense = Integer.parseInt(position.substring(0, position.length() - 1));
                    } else {
                        expense = Integer.parseInt(position);
                    }
                    newNode.setExpense(expense);
                }
                block[i][j] = newNode;
            }
        }
    }

    /**
     * Sets up the graph representation of this desk by connecting the
     * nodes together
     * @param height The height of the desk
     * @param width The width of the desk
     */
    private void setUpGraph(int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(j + 1 < width)
                    block[i][j].setEast(block[i][j + 1]);
                if(i - 1 >= 0)
                    block[i][j].setNorth(block[i - 1][j]);
                if(j - 1 >= 0)
                    block[i][j].setWest(block[i][j - 1]);
                if(i + 1 < height)
                    block[i][j].setSouth(block[i + 1][j]);
            }
        }
    }

    /**
     * Sets all nodes' ancestors to "null"
     */
    public void reset() {
        for (Node[] row:
             block) {
            for (Node node:
                 row) {
                node.setAncestor(null);
            }
        }
    }

    /**
     * Sets the starting point for the agent
     * @param startingNode Where the robot starts
     */
    public void setStartingNode(Node startingNode) {
        this.startingNode = startingNode;
    }

    /**
     * @return The robot's location
     */
    public Node getStartingNode() {
        return startingNode;
    }

    /**
     * @return The butter plate node
     */
    public ArrayList<Node> getButterPlates() {
        return butterPlates;
    }

    /**
     * @return The goal node
     */
    public ArrayList<Node> getGoals() {
        return goals;
    }

    /**
     * @return The height of the environment
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The height of the environment
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The whole array representation of the environment
     */
    public Node[][] getBlock() {
        return block;
    }
}
