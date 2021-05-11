import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Environment environment = new Environment(scanner.nextInt(), scanner.nextInt(), scanner);

        AS as = new AS(environment);
//
        BiBFS biBFS = new BiBFS(environment);

        Display screen = new Display(environment);
//        screen.putResults(as.search());

        screen.putResults(biBFS.search());

    }
}
