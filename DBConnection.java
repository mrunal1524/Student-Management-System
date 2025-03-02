import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_management?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Savi#15@Mrunal";  

    public static Connection getConnection() {
        Connection conn = null;
        try {
            System.out.println("🔄 Trying to connect to database...");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();  // Print full error details
        }
        return conn;
    }
}
