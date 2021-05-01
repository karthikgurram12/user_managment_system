package net.javaguides.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.usermanagement.model.User;

//DAO class provides CBAO database operations for the table users in the database
public class UserDAO {
	
	private String jdbcURL="jdbc:mysql://localhost:3306/demo?userSSL=false";
	private String jdbcun="root";
	private String jdbcpass="password";
	
	private static final String INSERT_USERS_SQL ="INSERT INTO users"+"(name,email,country) VALUES"+"(?,?,?);";
	
	private static final String SELECT_USER_BY_ID="select id,name,email,country from users where id=?";
	private static final String SELECT_ALL_USERS="select * from users";
	private static final String DELETE_USERS_SQL="delete from users where id=?;";
	private static final String UPDATE_USERS_SQL = "update users set name=?,email=?,country=? where id=?;";
	
	protected Connection getConnection() {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection(jdbcURL, jdbcun, jdbcpass);
			}catch(SQLException e) {
			//Todo auto-generated catch block
		}catch(ClassNotFoundException e) {
			//Todo auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	//create or insert user
	public void insertUser(User user) {
		try {
			Connection con=getConnection();
			PreparedStatement preparedStatement=con.prepareStatement(INSERT_USERS_SQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//update user
	public boolean updateUser(User user) {
		boolean rowUpdated = false;
		try {
			Connection con=getConnection();
			PreparedStatement statement=con.prepareStatement(UPDATE_USERS_SQL);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			statement.setInt(4, user.getId());
			rowUpdated=statement.executeUpdate()>0;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rowUpdated;
	}
	//select user by id
	public User selectUser(int id) {
		User user=null;
		try {
			//s1: establish the connection
			Connection con=getConnection();
			//s2: create a stmt using connection object
			PreparedStatement statement=con.prepareStatement(SELECT_USER_BY_ID);
			statement.setInt(1, id);
			System.out.println(statement);
			//s3 execute the query or update query
			ResultSet rs=statement.executeQuery();
			//s4: process the resultset object.
			while(rs.next())
			{
				String name=rs.getString("name");
				String email=rs.getString("email");
				String country=rs.getString("country");
				user=new User(id,name,email,country);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return user;
		
	}
	//select users
	public List<User> selectAllUsers() {
		List<User> users=new ArrayList<>();
		User user=null;
		try {
			//s1: establish the connection
			Connection con=getConnection();
			//s2: create a stmt using connection object
			PreparedStatement statement=con.prepareStatement(SELECT_ALL_USERS);
			System.out.println(statement);
			//s3 execute the query or update query
			ResultSet rs=statement.executeQuery();
			//s4: process the resultset object.
			while(rs.next())
			{
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String email=rs.getString("email");
				String country=rs.getString("country");
				users.add(new User(id,name,email,country));
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return users;
		
	}
	//delete user
		public void deleteUser(int id) {
			boolean rowDeleted;
			try {
				Connection con=getConnection();
				PreparedStatement preparedStatement=con.prepareStatement(DELETE_USERS_SQL);
				preparedStatement.setInt(1, id);
				rowDeleted=preparedStatement.executeUpdate()>0;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
}
