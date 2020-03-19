package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This class is an extra layer to manage the access of the tables in the database.
 */
@SuppressWarnings({
        "PMD.DataflowAnomalyAnalysis", "PMD.BeanMembersShouldSerialize"})
public class JdbcDao {

    private ResultSet rs;
    private PreparedStatement st;

    /**
     * This methodes checks if the username and password are in the database. If it returns true.
     * @param username username will be checked in the database.
     * @param password password will be checked in the database.
     * @param succes succes returns true if
     *               username and password are in the database, otherwise false
     * @return returns false
     */
    public Boolean login(String username, String password, Boolean succes) {
        try {
            String query = "select username, password from users "
                    + "where username = ?";
            st = DatabaseConnection.getConnection().prepareStatement(query);
            st.setString(1, username);
            rs = st.executeQuery();
            if (rs.next()) {
                String checkPassword = rs.getString("password");
                if(checkPassword(password, checkPassword)){
                    st.close();
                    rs.close();
                    succes= true;
                    return succes;
                } else {
                    st.close();
                    rs.close();
                    succes = false;
                    return succes;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return succes;
    }

    /**
     * This method adds a new user.
     * @param username A new user will be added with this username.
     * @param password A new user will be added with this password.
     */
    public void insertUser(String username, String password) {
        try {
            String query = "INSERT INTO users (username,password) VALUES (?,?)";

            st = DatabaseConnection.getConnection().prepareStatement(query);
            st.setString(1, username);
            String hashed = hashPassword(password);
            st.setString(2, hashed);
            rs = st.executeQuery();

            rs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method inserts new high score.
     * @param nickname A user can choice a nickname for inserting a high score.
     * @param score users score will be added.
     */
    public void insertHighScore(String nickname, int score) {
        try {
            String query = "INSERT INTO highscore (nickname,score) VALUES (?,?)";

            st = DatabaseConnection.getConnection().prepareStatement(query);
            st.setString(1, nickname);
            st.setInt(2, score);
            rs = st.executeQuery();

            rs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method Check is a user already exist with a given username.
     * @param username this username will be checked in the database
     * @return if the username exist it returns false, otherwise true
     */
    public Boolean checkUsername(String username) {
        try {
            String check = "";
            String query = "select username from users where username = ?";
            st = DatabaseConnection.getConnection().prepareStatement(query);
            st.setString(1, username);
            rs = st.executeQuery();
            while (rs.next()) {
                check = rs.getString("username");

            }
            if (check.equals(username)) {
                return false;
            }
            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
