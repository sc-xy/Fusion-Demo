package top.scxy.fusion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.scxy.fusion.annotation.IgnoreResponseAdvice;
import top.scxy.fusion.entity.ResultData;

import java.util.LinkedHashMap;
import java.util.Objects;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    private final ObjectMapper objectMapper;
    @Autowired
    public ResponseAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    public boolean supports(org.springframework.core.MethodParameter returnType, Class<? extends org.springframework.http.converter.HttpMessageConverter<?>> converterType) {
        if(returnType.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        if(Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, org.springframework.core.MethodParameter returnType, org.springframework.http.MediaType selectedContentType, Class<? extends org.springframework.http.converter.HttpMessageConverter<?>> selectedConverterType, org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response) {
        if(o instanceof String){
            try {
                return objectMapper.writeValueAsString(ResultData.success(o));
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        if(o instanceof ResultData){
            return o;
        }
        if(o instanceof LinkedHashMap<?, ?> map) {
            if(map.containsKey("status") && map.containsKey("error"))
                return ResultData.fail((Integer) map.get("status"), (String) map.get("error"));
        }
        return ResultData.success(o);
    }
}
