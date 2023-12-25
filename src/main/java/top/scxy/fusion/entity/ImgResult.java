package top.scxy.fusion.entity;

import java.io.Serializable;

public class ImgResult implements Serializable {
    private Integer success;
    private String message;
    private String url;

    public ImgResult() {
        this.success = 0;
        this.message = "success";
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

