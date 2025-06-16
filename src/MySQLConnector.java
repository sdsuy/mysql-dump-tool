import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnector {
    private final String host;
    private final String user;
    private final String password;
    private Connection connection;

    public MySQLConnector(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public void connect() throws SQLException {
        String url = "jdbc:mysql://" + host + ":3306/?useSSL=false&allowPublicKeyRetrieval=true";
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public String getServerVersion() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT VERSION()");
        return rs.next() ? rs.getString(1) : "Desconocida";
    }

    public List<String> getAvailableDatabases() throws SQLException {
        List<String> databases = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW DATABASES");

        while (rs.next()) {
            String db = rs.getString(1);
            if (!isSystemDatabase(db)) {
                databases.add(db);
            }
        }
        return databases;
    }

    private boolean isSystemDatabase(String db) {
        return db.equalsIgnoreCase("information_schema") ||
                db.equalsIgnoreCase("performance_schema") ||
                db.equalsIgnoreCase("mysql") ||
                db.equalsIgnoreCase("sys");
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException ignored) {
        }
    }
}
