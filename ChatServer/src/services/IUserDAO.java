package services;

import java.sql.SQLException;
import java.util.List;
import models.User;

public interface IUserDAO {
	
	public void insertUser(User user) throws SQLException;
	
	public User selectUser(int id);
	public User selectUserByName(String name, String clientPassword);
	public User selectUserByOnlyName(String name);
	
	public List < User > selectAllUsers();
	
}
