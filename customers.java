import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class customers {
   public static Connection ConnectToDB() throws Exception {
      //Registering the Driver
      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
      //Getting the connection
      String mysqlUrl = "jdbc:mysql://localhost/mydatabase";
      Connection con = DriverManager.getConnection(mysqlUrl, "root", "password");
      System.out.println("Connection established......");
      return con;
   }
   public static void main(String args[]) {
      //Creating a JSONParser object
      JSONParser jsonParser = new JSONParser();
      try {
         //Parsing the contents of the JSON file
         JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("E:/Customers_data.json"));
         //Retrieving the array
         JSONArray jsonArray = (JSONArray) jsonObject.get("Customers_data");
         Connection con = ConnectToDB();
         //Insert a row into the Customers table
         PreparedStatement pstmt = con.prepareStatement("INSERT INTO Customers values (?, ?, ?, ?, ? )");
         for(Object object : jsonArray) {
            JSONObject record = (JSONObject) object;
            String name = (String) record.get("Name");
            int id = Integer.parseInt((String) record.get("ID"));
            String country_of_birth = (String) record.get("COUNTRY_OF_BIRTH");
            String country_of_residence = (String) record.get("COUNTRY_OF_RESIDENCE");
            String segment = (String) record.get("SEGMENT");
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.setString(3, country_of_birth);
            pstmt.setString(4, country_of_residence);
            pstmt.setString(5, segment);
            pstmt.executeUpdate();
         }  
         System.out.println("Records inserted.....");

		 String sql = "SELECT * FROM Customers";
 
		 Statement statement = conn.createStatement();
		 ResultSet result = statement.executeQuery(sql);
 
		 int count = 0;
 
		 while (result.next()){
			 String name = result.getString("NAME");
			 int id = result.getString("ID");
			 String country_of_birth = result.getString("COUNTRY_OF_BIRTH");
			 String country_of_residence = result.getString("COUNTRY_OF_RESIDENCE");
			 String segment = result.getString("SEGMENT");
 
			 String output = "Customers #%d: %s - %s - %s - %s";
			 System.out.println(String.format(output, ++count, name, country_of_birth, country_of_residence, segment));
		}
		String sql = "UPDATE Customers SET  name=?, country_of_birth=?, country_of_residence WHERE name=?";
 
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, "John");
			statement.setString(2, "123456");
			statement.setString(3, "SG");
			statement.setString(4, "EU");
			statement.setString(5, "RETAIL");
 
		int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("An existing user was updated successfully!");
				}
		String sql = "DELETE FROM Customers WHERE username=?";
 
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, "Tom");
	
		int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("A user was deleted successfully!");
				}
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}