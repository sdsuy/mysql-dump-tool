import java.io.Console;
import java.util.List;
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

        MySQLConnector connector = new MySQLConnector(host, user, password);
        MySQLDumper dumper = new MySQLDumper(host, user, password);

        try {
            connector.connect();

            System.out.println("Versión del servidor MySQL: " + connector.getServerVersion());

            List<String> databases = connector.getAvailableDatabases();
            if (databases.isEmpty()) {
                System.out.println("No hay bases de datos disponibles.");
                return;
            }

            System.out.println("\nBases de datos disponibles:");
            for (int i = 0; i < databases.size(); i++) {
                System.out.println((i + 1) + ". " + databases.get(i));
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Seleccione una base de datos (número): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            if (choice < 1 || choice > databases.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            String dbName = databases.get(choice - 1);
            System.out.println("Generando dump de '" + dbName + "'...");
            dumper.dumpDatabase(dbName);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            connector.close();
        }
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
