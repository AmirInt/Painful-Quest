import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Environment environment = new Environment(scanner.nextInt(), scanner.nextInt(), scanner);

        BiBFS biBFS = new BiBFS(environment);

//        Path path = biBFS.searchRobot(environment.getBlock()[3][3], environment.getBlock()[4][4]);
//        while (!path.isEmpty()) {
//            Node node;
//            node = path.pop();
//            System.out.print(node.getX() + "," + node.getY() + "      ");
//        }

        LinkedList<Node> path = biBFS.search();
        Node node = path.pollFirst();
        while (node != null) {
            System.out.print(node.getX() + "," + node.getY() + "      ");
            node = path.pollFirst();
        }
    }
}
