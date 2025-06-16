import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException ignored) {
        }
    }
}
