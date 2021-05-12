import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class Main handles inputting the desk as the environment and
 * executing methods
 */
public class Main {

    private static void produceResult(Search searcher, FileWriter result, Node startingPoint) throws IOException {
        LinkedList<Node> path = searcher.search();
        if (path == null)
            result.write("Can't pass the butter");
        else {
            int depth = path.size();
            int cost = 0;
            Node n1 = startingPoint;
            while (path.size() > 0) {
                Node n2 = path.pollFirst();
                result.write(n1.relativeDirectionOf(n2) + " ");
                cost += n1.getExpense();
                n1 = n2;
            }
            result.write("\n" + cost + "\n");
            result.write(depth + "\n");
        }
        result.write("\n\n");
    }

    private static void resetEnvironment(Environment environment, Node startingNode, ArrayList<Node> butterPlates, ArrayList<Node> goals) {
        environment.setStartingNode(environment.getBlock()[startingNode.getY()][startingNode.getX()]);
        for (Node butterPlate:
                butterPlates) {
            environment.getButterPlates().add(butterPlate);
        }
        for (Node goal:
                goals) {
            goal.setObstacle(false);
            environment.getGoals().add(goal);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Input: ");
        Environment environment = new Environment(scanner.nextInt(), scanner.nextInt(), scanner);

        Node start = environment.getStartingNode();
        ArrayList<Node> butterPlates = new ArrayList<>(environment.getButterPlates());
        ArrayList<Node> goals = new ArrayList<>(environment.getGoals());

        System.out.println("Select a search algorithm: ");
        System.out.println("1. Iterative Deepening Search");
        System.out.println("2. Bidirectional Breadth First Search");
        System.out.println("3. A* Search");

        Display screen = new Display(environment);

        int option = scanner.nextInt();

        switch (option) {
            case 1 -> {
                IDS ids = new IDS(environment);
                screen.putResults(ids.search());
            }
            case 2 -> {
                BiBFS biBFS = new BiBFS(environment);
                screen.putResults(biBFS.search());
            }
            case 3 -> {
                AS as = new AS(environment);
                screen.putResults(as.search());
            }
        }

        try (FileWriter result = new FileWriter("Results\\result.txt")) {

            resetEnvironment(environment, start, butterPlates, goals);
            result.write("IDS-traversed path:\n");
            produceResult(new IDS(environment), result, start);
            resetEnvironment(environment, start, butterPlates, goals);

            result.write("BiBFS-traversed path:\n");
            produceResult(new BiBFS(environment), result, start);
            resetEnvironment(environment, start, butterPlates, goals);

            result.write("A*-traversed path:\n");
            produceResult(new AS(environment), result, start);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
