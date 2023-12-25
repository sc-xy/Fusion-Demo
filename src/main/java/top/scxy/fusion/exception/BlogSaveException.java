package top.scxy.fusion.exception;

public class BlogSaveException extends Exception {
    public BlogSaveException(String message) {
        super(message);
    }
    public BlogSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
