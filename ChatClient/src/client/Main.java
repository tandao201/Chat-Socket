package client;

import controllers.SocketController;
import models.ServerData;
import screens.LoginScreen;
import screens.MainScreen;
import screens.RegisterScreen;

import java.awt.Image;

import javax.swing.*;

public class Main {

	public static LoginScreen loginScreen;

	public static RegisterScreen registerScreen;
	public static ServerData serverData;
	public static MainScreen mainScreen;
	public static SocketController socketController;

	public static void main(String arg[]) {
		serverData = new ServerData("ahi", "localhost", 1234);
		loginScreen = new LoginScreen(serverData);
	}

	public static ImageIcon getScaledImage(String path, int width, int height) {
		Image img = new ImageIcon(path).getImage();
		Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}

}
