import java.sql.*;

public class CreateTable {
   static final String DB_URL = "jdbc:mysql://localhost:3000/Customers?serverTimezone=UTC";

   static final String USER = "root";
   static final String PASS = "1234";
   static final String DATABASE = "CustomerDB";

   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;

   try{
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER,PASS,DATABASE);
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM Customers";
      ResultSet rs = stmt.executeQuery(sql);
      while(rs.next()){
         String name = rs.getString("NAME");
         int id  = rs.getInt("ID");
         String country_of_birth = rs.getString("COUNTRY_OF_BIRTH");
	 String country_of_residence = rs.getString("COUNTRY_OF_RESIDENCE");
	 String segment = rs.getString("SEGMENT");
         System.out.print(", Name: " + name);
	 System.out.print("ID: " + id);
         System.out.print(", COUNTRY_OF_BIRTH: " + country_of_birth);
         System.out.print(", COUNTRY_OF_RESIDENCE: " + country_of_residence);
         System.out.print(", SEGMENT: " + segment);
      }
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
}
}
