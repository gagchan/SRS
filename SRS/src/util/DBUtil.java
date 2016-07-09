package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

	public static synchronized  Connection getSqliteConnection(){
		String driver="org.sqlite.JDBC";
		String conStr="jdbc:sqlite://d:/local/srs-project-master/srs.db";
		Connection conn=null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(conStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;			
	}

	
	public static Connection getMySqlConnection(){
		String driver="com.mysql.jdbc.Driver";
		String conStr="jdbc:mysql://localhost:3306/srs";
		Connection conn=null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(conStr,"root","123456");
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;			
	}
	
}
