package top.scxy.fusion.model;

public class ChatMessage {
    private String sender;
    private String receiver;
    private String msg;
    private MessageType type;
    public static enum MessageType {
        JOIN,
        CHAT,
        LEAVE
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String content) {
        this.msg = content;
    }

    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
}
