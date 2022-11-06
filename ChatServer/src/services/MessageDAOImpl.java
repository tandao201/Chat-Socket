package services;

import models.Message;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements IMessageDAO{
    Connection connection;
    private static final String INSERT_MESSAGE_SQL = "INSERT INTO message" + "  (message, createAt, user_id, roomId,username) VALUES " +
            " (?, ?, ?, ?, ?);";

    private static final String SELECT_MESSAGE_BY_ID = "select * from message where id =?";
    private static final String SELECT_ALL_MESSAGE = "select * from message";
    private static final String SELECT_ALL_MESSAGE_BY_ROOM = "select * from message where roomId=? LIMIT 6";
    public MessageDAOImpl() {
        this.connection = ConnectionDb.getConnection();
    }

    @Override
    public void insertMessage(Message message) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MESSAGE_SQL);
            preparedStatement.setString(1, message.getMessage());
            preparedStatement.setString(2, message.getCreatedAt());
            preparedStatement.setInt(3, message.getUserId());
            preparedStatement.setInt(4,message.getRoomId());
            preparedStatement.setString(5, message.getUsername());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println("Error insert: "+e.toString());
        }
    }

    @Override
    public List<Message> getAllMessage() {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MESSAGE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int messageId = resultSet.getInt("message_id");
                String message = resultSet.getString("message");
                String createdAt = resultSet.getString("createAt");
                int userId = resultSet.getInt("user_id");
                int roomId = resultSet.getInt("roomId");
                String username = resultSet.getString("username");
                messages.add(new Message(messageId,userId,roomId, message, createdAt, username));
            }
        } catch (Exception e) {
            System.out.println("Error getAllMessage: "+e.toString());
        }
        return messages;
    }

    @Override
    public List<Message> getAllMessageByRoom(int roomId) {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MESSAGE_BY_ROOM);
            preparedStatement.setInt(1,roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int messageId = resultSet.getInt("message_id");
                String message = resultSet.getString("message");
                String createdAt = resultSet.getString("createAt");
                int userId = resultSet.getInt("user_id");
                int roomIdInt = resultSet.getInt("roomId");
                String username = resultSet.getString("username");
                messages.add(new Message(messageId,userId,roomIdInt, message, createdAt, username));
            }
        } catch (Exception e) {

        }
        return messages;
    }

    @Override
    public Message getMessageById(int id) {
        Message message = new Message();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MESSAGE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int messageId = resultSet.getInt("message_id");
                String messageBody = resultSet.getString("message");
                String createdAt = resultSet.getString("createAt");
                int userId = resultSet.getInt("user_id");
                int roomId = resultSet.getInt("roomId");
                String username = resultSet.getString("username");
                message = new Message(messageId, userId,roomId, messageBody, createdAt, username);
            }
        } catch (Exception e) {
            System.out.println("Error getMessageById: "+e.toString());
        }
        return message;
    }
}
