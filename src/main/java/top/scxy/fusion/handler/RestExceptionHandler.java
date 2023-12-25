package top.scxy.fusion.handler;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.scxy.fusion.entity.ResultData;

@RestControllerAdvice
public class RestExceptionHandler {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e) {
        logger.error("服务器异常：", e);
        return ResultData.fail(500,e.getMessage());
    }
}

