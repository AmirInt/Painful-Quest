import java.util.ArrayList;
import java.util.Scanner;

public class Environment {

    private Node[][] block;
    private Node startingNode;
    private ArrayList<Node> goals;
    private ArrayList<Node> butterPlates;

    public Environment(int height, int width, Scanner scanner) {
        block = new Node[height][width];
        goals = new ArrayList<>();
        butterPlates = new ArrayList<>();
        setNodesArray(height, width, scanner);
        setUpGraph(height, width);
    }

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
                        case 'r' -> startingNode = newNode;
                        case 'b' -> butterPlates.add(newNode);
                        case 'p' -> goals.add(newNode);
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

    public void setStartingNode(Node startingNode) {
        this.startingNode = startingNode;
    }

    public Node getStartingNode() {
        return startingNode;
    }

    public ArrayList<Node> getButterPlates() {
        return butterPlates;
    }

    public ArrayList<Node> getGoals() {
        return goals;
    }

    public Node[][] getBlock() {
        return block;
    }
}
