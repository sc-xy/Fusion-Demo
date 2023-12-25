package top.scxy.fusion.entity;

import java.util.Date;
import java.util.List;

public class Comment {
    private Integer id;
    private Integer parent_id;
    private Integer user_id;
    private Integer blog_id;
    private String content;
    private Date create_time;
    private String user_nick_name;
    private String user_avatar;
    private String parent_nick_name;
    private List<Comment> reply_comments;
    public Comment() {
    }
    public Comment(Integer id, Integer parent_id, Integer user_id, Integer blog_id, String content, Date create_time,
                   String user_nick_name, String user_avatar, String parent_nick_name, List<Comment> reply_comments) {
        this.id = id;
        this.parent_id = parent_id;
        this.user_id = user_id;
        this.blog_id = blog_id;
        this.content = content;
        this.create_time = create_time;
        this.user_nick_name = user_nick_name;
        this.user_avatar = user_avatar;
        this.parent_nick_name = parent_nick_name;
        this.reply_comments = reply_comments;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getParent_nick_name() {
        return parent_nick_name;
    }

    public void setParent_nick_name(String parent_nick_name) {
        this.parent_nick_name = parent_nick_name;
    }

    public List<Comment> getReply_comments() {
        return reply_comments;
    }

    public void setReply_comments(List<Comment> reply_comments) {
        this.reply_comments = reply_comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(Integer blog_id) {
        this.blog_id = blog_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
