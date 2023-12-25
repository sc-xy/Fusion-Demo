package top.scxy.fusion.entity;

public class Message {
    private UserInfo sender;
    private UserInfo receiver;
    private String msg;
    private TYPE type;
    static enum TYPE {
        JOIN,
        CHAT,
        LEAVE
    }
    public UserInfo getSender() {
        return sender;
    }
    public void setSender(UserInfo sender) {
        this.sender = sender;
    }
    public UserInfo getReceiver() {
        return receiver;
    }
    public void setReceiver(UserInfo receiver) {
        this.receiver = receiver;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String content) {
        this.msg = content;
    }
    public TYPE getType() {
        return type;
    }
    public void setType(TYPE type) {
        this.type = type;
    }
}
