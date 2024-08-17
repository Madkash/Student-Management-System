import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/sms";
    private static final String USER = "root";
    private static final String PASSWORD = "fish13070";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

}
