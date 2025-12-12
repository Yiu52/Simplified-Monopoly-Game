package lab2;

import java.sql.*;
import java.util.*;

/**
 *
 * @author CHAN C.K.
 */
public class DBDemo {

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    void setupDBConnection() {

        // Step 1: Load Database Driver CLass        
        try {
            Class.forName("org.sqlite.JDBC"); //SQLite
        } catch (ClassNotFoundException cnfex) {
            System.out.println("Problem in loading or registering SQLite JDBC driver");
            cnfex.printStackTrace();
        }

        // Step 2: Opening database connection
        try {
            String dbURL = "jdbc:sqlite:vocab.db";

            // Step 2.A: Create and get connection using DriverManager class
            connection = DriverManager.getConnection(dbURL);

            // Step 2.B: Creating JDBC Statement 
            statement = connection.createStatement();

        // Step 3: Creating a table for holding the data *if it does not exist already*
            String sql = "CREATE TABLE IF NOT EXISTS vocab_table (\n"
                    + "	entry text PRIMARY KEY,\n"
                    + "	meaning text\n"
                    + ");";

            statement.execute(sql);

        } catch (Exception sqlex) {
            sqlex.printStackTrace();
            System.out.println("Open database error");
        }
    }

    void closeDB() {
        try {
            connection.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    public void add(String eng, String meaning) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT into vocab_table values (?,?)");
            stmt.setString(1, eng);
            stmt.setString(2, meaning);
            stmt.execute();
            System.out.println("Record added: " + eng + " : " + meaning);

        } catch (Exception sqlex) {
            System.out.println(sqlex.getMessage());
            System.out.println("Add Command not successful:" + eng + " " + meaning);

        }
    }

    public boolean delete(String eng) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE from vocab_table where entry = ?");
            stmt.setString(1, eng);
            stmt.execute();
            System.out.println("Record deleted: " + eng);
            return true;

        } catch (Exception sqlex) {

            System.out.println("Delete Command not successful");
            return false;
        }
    }

    public String lookup(String eng) {

        try {

            PreparedStatement stmt = connection.prepareStatement("SELECT meaning FROM vocab_table where entry = ?");

            stmt.setString(1, eng);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String meaning = rs.getString(1);
                return (meaning);
            } else {
                return null;
            }
            
        } catch (Exception sqlex) {
            sqlex.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {
        DBDemo test = new DBDemo();
        test.setupDBConnection();
        test.add("Apple", "A type of fruit");
        test.add("Boy", "A young male person");
        System.out.println("Apple means " + test.lookup("Apple"));
        System.out.println("Boy means " + test.lookup("Boy"));
        test.delete("Apple");
        System.out.println("Apple means " + test.lookup("Apple"));
        test.closeDB();
    }
}
