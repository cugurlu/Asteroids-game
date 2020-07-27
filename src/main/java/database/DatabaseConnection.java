package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class connects to the database.
 */
public class DatabaseConnection {

    // Make a db called project, with table called users with 3 column id(serial),
    // username(char[]) and password(char[])
    private  static final String url = "jdbc:postgresql://localhost:5432/project";
    private static final String user = "postgres";
    private static final String password = "deneme123"; //put your db password here.

    /**
     * This method connects to the database.
     * @return Connection. Returns the connection it is made.
     * @throws SQLException when there is no connection
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
