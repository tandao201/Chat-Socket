package services;

import models.Message;

import java.util.List;

public interface IMessageDAO {
    void insertMessage(Message message);

    List<Message> getAllMessage();

    List<Message> getAllMessageByRoom(int roomId);

    Message getMessageById(int id);
}
