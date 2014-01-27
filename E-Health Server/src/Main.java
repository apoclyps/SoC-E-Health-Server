import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) throws Exception {
		DBModule connection = new DBModule();
		//connection.insertChannel();
		connection.selectChannel();
		connection.selectItem();
	}
}
