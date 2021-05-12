import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Environment environment = new Environment(scanner.nextInt(), scanner.nextInt(), scanner);

//        AS as = new AS(environment);

        BiBFS biBFS = new BiBFS(environment);

        IDS ids = new IDS(environment);

//        Path path = ids.searchRobot(environment.getStartingNode(), environment.getBlock()[14][11]);
//        if (path != null)
//            while (path.isNotEmpty())
//                System.out.print(path.pop() + "    ");

//        for (Path path:
//             ids.searchPlate(environment.getButterPlates().get(0), environment.getGoals().get(0))) {
//            while (path.isNotEmpty())
//                System.out.print(path.pop() + "    ");
//            System.out.println();
//        }

        Display screen = new Display(environment);

//        screen.putResults(as.search());

//        screen.putResults(biBFS.search());

        screen.putResults(ids.search());

    }
}
