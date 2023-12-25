package top.scxy.fusion.entity;

public class ResultData<T> {
    private int code;
    private String message;
    private T data;
    public ResultData() {
        this.code = 0;
        this.message = "success";
    }
    public ResultData(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }
    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setData(data);
        resultData.setCode(0);
        resultData.setMessage("success");
        return resultData;
    }
    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        return resultData;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

