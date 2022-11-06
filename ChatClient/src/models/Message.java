package models;

public class Message {
    private int messageId;
    private int userId;
    private String message;
    private String createdAt;
    private int roomId;
    private String username;

    public Message() {
    }

    public Message(int messageId, int userId, int roomId, String message, String createdAt, String username) {
        this.messageId = messageId;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
        this.roomId = roomId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return messageId +
                "," + userId +
                "," + message +
                "," + createdAt +
                "," + roomId +
                "," + username;
    }
}
