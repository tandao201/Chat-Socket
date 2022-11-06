package screens;

import client.Main;
import controllers.SocketController;
import models.ServerData;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame implements ActionListener {

    public ServerData connectedServer;

    JButton b1;
    JPanel newPanel;
    JLabel userLabel, passLabel;
    JTextField  usernameText, passText;

    public RegisterScreen(ServerData connectedServer) {
        this.connectedServer = connectedServer;
        //create label for username
        userLabel = new JLabel();
        userLabel.setText("Tài khoản");
        usernameText = new JTextField(15);    //set length of the text

        //create label for password
        passLabel = new JLabel();
        passLabel.setText("Mật khẩu");      //set label value for textField2

        //create text field to get password from the user
        passText = new JPasswordField(15);    //set length for the password

        //create submit button
        b1 = new JButton("Đăng kí"); //set label to button

        //create panel to put form elements
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.add(userLabel);    //set username label to panel
        newPanel.add(usernameText);   //set text field to panel
        newPanel.add(passLabel);    //set password label to panel
        newPanel.add(passText);   //set text field to panel
        newPanel.add(b1);           //set button to panel

        //set border to panel
        this.add(newPanel, BorderLayout.CENTER);

        //perform action on button click
        b1.addActionListener(this);     //add action listener to button
        this.setTitle("Đăng kí tài khoản");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String userValue = usernameText.getText();        //get user entered username from the textField1
            String passValue = passText.getText();        //get user entered pasword from the textField2
            User user = new User(1, userValue, passValue);
            if (userValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tài khoản không để trống!");
                return;
            }
            if (passValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không để trống!");
                return;
            }
            Main.socketController = new SocketController(userValue, passValue, Main.serverData);
            System.out.println("Socket data: "+Main.socketController);
            Main.socketController.register();
//            connectedServer.nickName = userValue;
////            //check whether the credentials are authentic or not
//            Main.socketController.sender.write("new login");
//            Main.socketController.sender.newLine();
//            Main.socketController.sender.write(userValue+","+passValue);
//            Main.socketController.sender.newLine();
//            Main.socketController.sender.flush();
//
//            String loginResult = Main.socketController.receiver.readLine();
//            System.out.println("Receive data: "+loginResult);
//            if (loginResult.equals("login success")) {
//                this.setVisible(false);
//                Main.mainScreen = new MainScreen();
//            } else {
//                JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không đúng!");
//            }
//            Main.socketController.sender.write("Hello");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }
}
