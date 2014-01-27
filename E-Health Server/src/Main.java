import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) throws Exception {
		MySQLDAO connection = new MySQLDAO();
		//connection.insertChannel();
		connection.selectChannel();
		connection.selectItem();
	}
}
