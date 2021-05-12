import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Input: ");
        Environment environment = new Environment(scanner.nextInt(), scanner.nextInt(), scanner);

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
    }
}
