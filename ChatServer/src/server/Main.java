package server;

import controllers.SocketController;
import models.Message;
import models.User;
import screens.MainScreen;
import services.MessageDAOImpl;
import services.UserDAOImpl;

import java.util.List;

public class Main {

	public static SocketController socketController;
	public static MainScreen mainScreen;

	public static UserDAOImpl userDAO = new UserDAOImpl();
	public static MessageDAOImpl messageDAO;

	public static void main(String[] args) {
		messageDAO = new MessageDAOImpl();
		mainScreen = new MainScreen();
//		List<User> users = userDAO.selectAllUsers();
//		for (User user: users)
//			System.out.println(user);

		System.out.println("-------------Init server-------------");

//		List<Message> messages = messageDAO.getAllMessage();
//		for (Message message: messages) {
//			System.out.println("Message in: " +message);
//		}
	}
}
