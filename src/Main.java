import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Environment environment = new Environment(scanner.nextInt(), scanner.nextInt(), scanner);

        BiBFS biBFS = new BiBFS(environment);

//        for (Path path:
//                biBFS.searchPlates(environment.getButterPlates().get(1), environment.getGoals().get(1))) {
//            System.out.println("Path:");
//            while (path.isNotEmpty()) {
//                Node node;
//                node = path.pop();
//                System.out.print(node.getX() + "," + node.getY() + "      ");
//            }
//            System.out.println();
//        }

//        Path path = biBFS.searchRobot(environment.getStartingNode(), environment.getBlock()[1][2]);
//        while (path.isNotEmpty()) {
//            Node node;
//            node = path.pop();
//            System.out.print(node.getX() + "," + node.getY() + "      ");
//        }

        LinkedList<Node> path = biBFS.search();
        System.out.println(path.size());
        Node node = path.pollFirst();
        while (node != null) {
            System.out.print(node + "      ");
            node = path.pollFirst();
        }

    }
}
