import java.util.Scanner;

/**
 * Main
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-08-08
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int count =  scanner.nextInt();
            System.out.println(count);
        }
        scanner.close();
    }

}
