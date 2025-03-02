import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
        if (con != null) {
            System.out.println("✅ Connection successful!");
        } else {
            System.out.println("❌ Connection failed! Database is returning null.");
        }
    }
}
