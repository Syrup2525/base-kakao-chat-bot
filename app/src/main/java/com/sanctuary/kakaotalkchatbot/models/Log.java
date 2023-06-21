package com.sanctuary.kakaotalkchatbot.models;

public class Log {
    private String sender;
    private String groupRoomName;
    private String messageBody;
    private String message;
    private String errorMessage;
    private String date;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGroupRoomName() {
        return groupRoomName;
    }

    public void setGroupRoomName(String groupRoomName) {
        this.groupRoomName = groupRoomName;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
