package top.scxy.fusion.entity;


import java.io.Serializable;

public class UserInfo implements Serializable {
    private Integer id;
    private String username;
    private String email;
    private String nickname;
    private String avatar;
    public UserInfo() {
    }
    public UserInfo(Integer id, String username, String email, String nickname, String avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.avatar = avatar;
    }
    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() { return email; }
    public String getNickname() {
        return nickname;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
