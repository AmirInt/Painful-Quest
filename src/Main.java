import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Environment environment = new Environment(scanner.nextInt(), scanner.nextInt(), scanner);
    }
}
