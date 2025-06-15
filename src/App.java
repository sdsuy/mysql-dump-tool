import java.io.Console;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java App <host> <usuario>");
            return;
        }

        String host = args[0];
        String user = args[1];
        String password = promptPassword("Contraseña para el usuario " + user + ": ");
    }

    private static String promptPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            return new String(console.readPassword(prompt));
        } else {
            System.out.print(prompt);
            Scanner sc = new Scanner(System.in);
            return sc.nextLine();
        }
    }
}
