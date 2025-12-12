package Lab3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
/**
 *
 * @author CHAN C.K.
 */
public class VocabModel {
    
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
                    + "	meaning text,\n"
                    + " wordClass text);";

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
       
    public void add(String vocab, String meaning, String wordClass) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT OR REPLACE into vocab_table values (?,?,?)");
            stmt.setString(1, vocab);
            stmt.setString(2, meaning);
            stmt.setString(3, wordClass);
            stmt.execute();
            //System.out.println("Record added: " + eng + " : " + meaning);

        } catch (Exception sqlex) {
            System.out.println(sqlex.getMessage());
            System.out.println("Add Command not successful:" + vocab + " " + meaning);

        }
    }

    public boolean delete(String vocab) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE from vocab_table where entry = ?");
            stmt.setString(1, vocab);
            stmt.execute();
            System.out.println("Entry removed");
            return true;

        } catch (Exception sqlex) {

            System.out.println("Delete Command not successful");
            return false;
        }
    }

    public VocabData lookup(String vocab) {

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT meaning, wordClass FROM vocab_table where entry = ?");

            stmt.setString(1, vocab);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String meaning = rs.getString(1);
                String wordClass = rs.getString(2);
                VocabData vd = new VocabData(vocab, meaning, wordClass);
                return (vd);
                
            } else {
                
            }
            
        } catch (Exception sqlex) {
            sqlex.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
