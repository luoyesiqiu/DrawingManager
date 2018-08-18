package dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Driver;

public abstract class BaseDao {
	protected static Connection connection;
	protected Properties properties=null;
	String userPath=System.getenv("USERPROFILE")+"/DrawingManager/";
	public BaseDao()
	{
		if(connection==null){
			 try {
				properties=new Properties();
				properties.load(new FileReader(userPath+"preference.ini"));
				Class.forName("com.mysql.jdbc.Driver");
				connection=DriverManager.getConnection(properties.getProperty("database_url", "")
						,properties.getProperty("database_username", "")
						,properties.getProperty("database_pwd",""));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnection(String url,String username,String password){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url,username,password);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static  boolean  connectDatabase(String url,String username,String password)
	{
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		 return true;
	}
	
	public static boolean createDatabase(String database,String url,String user,String pwd){
		String sql="create database "+database;
		Connection connection=getConnection(url,user,pwd);
		if(connection==null)
			return false;
		try {
			java.sql.PreparedStatement statement=connection.prepareStatement(sql);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	protected Connection getConnection() {
		return connection;
	}
	
	public abstract boolean createTable();
}
