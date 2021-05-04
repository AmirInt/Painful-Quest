import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Environment environment = new Environment(scanner.nextInt(), scanner.nextInt(), scanner);

//        AS as = new AS(environment);
//
//        BiBFS biBFS = new BiBFS(environment);

//        for (Path path:
//                as.searchPlates(environment.getButterPlates().get(1), environment.getGoals().get(1))) {
//            System.out.println("Path:");
//            while (path.isNotEmpty()) {
//                Node node;
//                node = path.pop();
//                System.out.print(node.getX() + "," + node.getY() + "      ");
//            }
//            System.out.println();
//        }

//        Path path = as.searchRobot(environment.getStartingNode(), environment.getBlock()[4][1]);
//        while (path.isNotEmpty()) {
//            Node node;
//            node = path.pop();
//            System.out.print(node.getX() + "," + node.getY() + "      ");
//        }

//        Node n1 = environment.getStartingNode(), n2;
//        LinkedList<Node> path = biBFS.search();
//        if (path == null)
//            System.out.println("Not Possible");
//        else {
//            int cost = 0;
//            int depth = path.size();
//            n2 = path.pollFirst();
//            while (n2 != null) {
//                System.out.print(n1.relativeDirectionOf(n2) + "    ");
//                cost += 1;
//                n1 = n2;
//                n2 = path.pollFirst();
//            }
//            System.out.println();
//            System.out.println(cost);
//            System.out.println(depth);
//        }

//        Node n1 = environment.getStartingNode(), n2;
//        LinkedList<Node> path = as.search();
//        if (path == null)
//            System.out.println("Not Possible");
//        else {
//            int cost = 0;
//            int depth = path.size();
//            n2 = path.pollFirst();
//            while (n2 != null) {
//                System.out.print(n1.relativeDirectionOf(n2) + "    ");
//                cost += n1.getExpense();
//                n1 = n2;
//                n2 = path.pollFirst();
//            }
//            System.out.println();
//            System.out.println(cost);
//            System.out.println(depth);
//        }

    }
}
