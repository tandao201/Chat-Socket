package controllers;

import client.Main;
import controllers.AudioController;
import models.Message;
import models.Room;
import models.ServerData;
import models.User;
import screens.MainScreen;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class SocketController {

	public String userName;
	public String password;
	public ServerData connectedServer;
	Socket s;
	public BufferedReader receiver;
	public BufferedWriter sender;

	Thread receiveAndProcessThread;

	public List<String> onlineUsers;
	public List<Room> allRooms;
	List<Message> messages = new ArrayList<>();
	int roomIdMain = 0;

	public SocketController(String name,String password, ServerData connectedServer) {
		onlineUsers = new ArrayList<String>();
		allRooms = new ArrayList<Room>();
		try {
			this.userName = name;
			this.password = password;
			this.connectedServer = connectedServer;
			s = new Socket(connectedServer.ip, connectedServer.port);
			InputStream is = s.getInputStream();
			receiver = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			OutputStream os = s.getOutputStream();
			sender = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

		} catch (IOException e1) {
			Main.loginScreen.loginResultAction("closed");
		}
	}

	public void Login() {
		System.out.println("Data: "+userName+","+password);
		try {
			sender.write("new login");
			sender.newLine();
			sender.write(userName+","+password);
			sender.newLine();
			sender.flush();
			String loginResult = receiver.readLine();
			if (loginResult.equals("login success")) {
				Main.loginScreen.setVisible(false);
				Main.mainScreen = new MainScreen();

				int serverOnlineAccountCount = Integer.parseInt(receiver.readLine());
				System.out.println("Client account online: "+serverOnlineAccountCount);
				for (int i = 0; i < serverOnlineAccountCount; i++)
					onlineUsers.add(receiver.readLine());


				Main.mainScreen.updateOnlineUserJList();

				receiveAndProcessThread = new Thread(() -> {
					try {
						while (true) {
							String header = receiver.readLine();
							System.out.println("Header " + header);
							if (header == null)
								throw new IOException();

							switch (header) {
								case "new user online": {
									String who = receiver.readLine();
									onlineUsers.add(who);
									Main.mainScreen.updateServerData();
									Main.mainScreen.updateOnlineUserJList();
									break;
								}
								case "user quit": {
									String whoQuit = receiver.readLine();
									onlineUsers.remove(whoQuit);
									Main.mainScreen.updateServerData();
									Main.mainScreen.updateOnlineUserJList();
									for (Room room : allRooms) {
										if (room.users.contains(whoQuit)) {
											Main.mainScreen.addNewMessage(room.id, "notify", whoQuit, "Đã thoát ứng dụng");
											room.users.remove(whoQuit);
										}
									}
									Main.mainScreen.updateRoomUsersJList();

									break;
								}
								case "new room": {
									messages = new ArrayList<>();
									int roomID = Integer.parseInt(receiver.readLine());
									roomIdMain = roomID;
									String whoCreate = receiver.readLine();
									String name = receiver.readLine();
									String type = receiver.readLine();
									int roomUserCount = Integer.parseInt(receiver.readLine());
									List<String> users = new ArrayList<String>();
									for (int i = 0; i < roomUserCount; i++){
										String username = receiver.readLine();
										users.add(username);
									}
									// load message cu~
									String data = receiver.readLine();
									System.out.println("Loaded message: " +data);
									if (!data.isEmpty() && data!=null) {
										String[] listMess = data.split("\\|");
										for (String mess: listMess) {
											String[] messItem = mess.split(",");
											Message message = new Message(Integer.parseInt(messItem[0]),Integer.parseInt(messItem[1]),Integer.parseInt(messItem[4]), messItem[2], messItem[3], messItem[5] );
											messages.add(message);
										}
									}

									Room newRoom = new Room(roomID, name, type, users);
									Main.socketController.allRooms.add(newRoom);
									Main.mainScreen.newRoomTab(newRoom);
									for (Message message: messages ){
										Main.mainScreen.addNewMessage(newRoom.id, "text", message.getUsername(), message.getMessage());
									}
//									Main.mainScreen.addNewMessage(newRoom.id, "notify", whoCreate,
//											type.equals("group") ? "Đã tạo group" : "Đã mở chat");

									Main.mainScreen.updateGroupJList();
									break;
								}
								case "text from user to room": {
									String user = receiver.readLine();
									int roomID = Integer.parseInt(receiver.readLine());
									String content = "";
									char c;
									do {
										c = (char) receiver.read();
										if (c != '\0')
											content += c;
									} while (c != '\0');
									Main.mainScreen.addNewMessage(roomID, "text", user, content);
									break;
								}
								case "file from user to room": {
									String user = receiver.readLine();
									int roomID = Integer.parseInt(receiver.readLine());
									String fileName = receiver.readLine();
									System.out.println("Recevie file " + fileName + " from " + user + " to room " + roomID);
									Main.mainScreen.addNewMessage(roomID, "file", user, fileName);
									break;
								}
								case "audio from user to room": {
									String user = receiver.readLine();
									int roomID = Integer.parseInt(receiver.readLine());
									int audioDuration = Integer.parseInt(receiver.readLine());
									System.out.println("Recevie audio from " + user + " to room " + roomID);
									Main.mainScreen.addNewMessage(roomID, "audio", user, "" + audioDuration);
									break;
								}
								case "response download file": {
									int fileSize = Integer.parseInt(receiver.readLine());
									File file = new File(downloadToPath);
									byte[] buffer = new byte[1024];
									InputStream in = s.getInputStream();
									OutputStream out = new FileOutputStream(file);

									int count;
									int receivedFileSize = 0;
									while ((count = in.read(buffer)) > 0) {
										out.write(buffer, 0, count);
										receivedFileSize += count;
										if (receivedFileSize >= fileSize)
											break;
									}

									out.close();
									break;
								}
								case "response audio bytes": {

									int fileSize = Integer.parseInt(receiver.readLine());

									byte[] buffer = new byte[1024];
									InputStream in = s.getInputStream();
									ByteArrayOutputStream receivedBytes = new ByteArrayOutputStream();

									int count;
									int receivedFileSize = 0;
									while ((count = in.read(buffer)) > 0) {
										receivedBytes.write(buffer, 0, count);
										receivedFileSize += count;
										if (receivedFileSize >= fileSize)
											break;
									}

									receivedBytes.close();

									AudioController.play(receivedBytes.toByteArray());
									break;
								}
								case "loaded message": {
									String data = receiver.readLine();
									System.out.println("Loaded message: " +data);
									if (!data.isEmpty() && data!=null) {
										String[] listMess = data.split("\\|");
										for (String mess: listMess) {
											String[] messItem = mess.split(",");
											Message message = new Message(Integer.parseInt(messItem[0]),Integer.parseInt(messItem[1]),Integer.parseInt(messItem[4]), messItem[2], messItem[3], messItem[5] );
											messages.add(message);
										}
										for (Message message: messages ){
											System.out.println("Load message: "+message );
											Main.mainScreen.addNewMessage(roomIdMain, "text", message.getUsername(), message.getMessage());
										}
									}
									break;
								}
							}
						}
					} catch (IOException e) {
						JOptionPane.showMessageDialog(Main.mainScreen, "Server đã đóng, ứng dụng sẽ thoát", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						try {
							Main.socketController.s.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.exit(0);
					}
				});
				receiveAndProcessThread.start();
			} else
				Main.loginScreen.loginResultAction("existed");

		} catch (Exception e1) {
			System.out.println(e1.toString());
		}
	}

	public void register() {
		try {
			sender.write("register");
			sender.newLine();
			sender.write(userName+","+password);
			sender.newLine();
			sender.flush();
			String loginResult = receiver.readLine();
			if (loginResult .equals("success")) {
				Main.registerScreen.setVisible(false);
				Main.loginScreen.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(Main.registerScreen, "Tài khoản đã được đăng kí, thử lại");
			}
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}

	public void sendTextToRoom(int roomID, String content) {
		try {
			sender.write("text to room");
			sender.newLine();
			sender.write("" + roomID);
			sender.newLine();
			sender.write(content);
			sender.write('\0');
			sender.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendFileToRoom(int roomID, String fileName, String filePath) {
		try {
			System.out.println("Send file " + fileName + " to room " + roomID);

			File file = new File(filePath);
			Room room = Room.findRoom(allRooms, roomID);

			sender.write("file to room");
			sender.newLine();
			sender.write("" + roomID);
			sender.newLine();
			sender.write("" + room.messages.size());
			sender.newLine();
			sender.write(fileName);
			sender.newLine();
			sender.write("" + file.length());
			sender.newLine();
			sender.flush();

			byte[] buffer = new byte[1024];
			InputStream in = new FileInputStream(file);
			OutputStream out = s.getOutputStream();

			int count;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}

			in.close();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendAudioToRoom(int roomID, byte[] audioBytes) {

		try {
			System.out.println("Send audio to room " + roomID);

			Room room = Room.findRoom(allRooms, roomID);

			sender.write("audio to room");
			sender.newLine();
			sender.write("" + roomID);
			sender.newLine();
			sender.write("" + room.messages.size());
			sender.newLine();
			sender.write("" + AudioController.getAudioDuration(audioBytes));
			sender.newLine();
			sender.write("" + audioBytes.length);
			sender.newLine();
			sender.flush();

			byte[] buffer = new byte[1024];
			InputStream in = new ByteArrayInputStream(audioBytes);
			OutputStream out = s.getOutputStream();

			int count;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}

			in.close();
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String downloadToPath;

	public void downloadFile(int roomID, int fileMessageIndex, String fileName, String downloadToPath) {

		this.downloadToPath = downloadToPath;
		try {
			sender.write("request download file");
			sender.newLine();
			sender.write("" + roomID);
			sender.newLine();
			sender.write("" + fileMessageIndex);
			sender.newLine();
			sender.write(fileName);
			sender.newLine();
			sender.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void getAudioBytes(int roomID, int fileMessageIndex) {

		try {
			sender.write("request audio bytes");
			sender.newLine();
			sender.write("" + roomID);
			sender.newLine();
			sender.write("" + fileMessageIndex);
			sender.newLine();
			sender.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void createPrivateRoom(String otherUser) {
		try {
			sender.write("request create room");
			sender.newLine();
			sender.write(otherUser); // room name
			sender.newLine();
			sender.write("private"); // room name
			sender.newLine();
			sender.write("2");
			sender.newLine();
			sender.write(userName);
			sender.newLine();
			sender.write(otherUser);
			sender.newLine();
			sender.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	synchronized public void loadMessage(String roomIdStr) {
		messages = new ArrayList<>();
		try {
			System.out.println("Room name: "+roomIdStr);
			sender.write("load message");
			sender.newLine();
			sender.write(roomIdStr);
			sender.newLine();
			sender.flush();
			wait();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void createGroup(String groupName, List<String> otherUsers) {

		try {
			sender.write("request create room");
			sender.newLine();
			sender.write(groupName);
			sender.newLine();
			sender.write("group"); // room name
			sender.newLine();
			sender.write("" + (otherUsers.size() + 1));
			sender.newLine();
			sender.write(userName);
			sender.newLine();
			for (String user : otherUsers) {
				sender.write(user);
				sender.newLine();
			}
			sender.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean serverOnline(String ip, int port) {
		try {
			Socket s = new Socket();
			s.connect(new InetSocketAddress(ip, port), 300);
			s.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public static String serverName(String ip, int port) {

		if (!serverOnline(ip, port))
			return "";

		try {
			Socket s = new Socket(ip, port);
			InputStream is = s.getInputStream();
			BufferedReader receiver = new BufferedReader(new InputStreamReader(is));
			OutputStream os = s.getOutputStream();
			BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(os));

			sender.write("get name");
			sender.newLine();
			sender.flush();

			String name = receiver.readLine();

			s.close();
			return name;
		} catch (IOException ex) {
			return "";
		}
	}

	public static int serverConnectedAccountCount(String ip, int port) {
		try {
			Socket s = new Socket(ip, port);
			InputStream is = s.getInputStream();
			BufferedReader receiver = new BufferedReader(new InputStreamReader(is));
			OutputStream os = s.getOutputStream();
			BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(os));

			sender.write("get connected count");
			sender.newLine();
			sender.flush();

			int count = Integer.parseInt(receiver.readLine());

			s.close();
			return count;
		} catch (IOException ex) {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "SocketController{" +
				"userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", connectedServer=" + connectedServer +
				'}';
	}
}
