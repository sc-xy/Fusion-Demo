package top.scxy.fusion.service;

import org.springframework.mail.SimpleMailMessage;

public interface AsyncService {
    /*
    * @Description: 发送邮件
    * @Param: [message]
    * */
    void sendEmailVerifyCode(SimpleMailMessage message);
    /*
    * @Description: 添加信息到Redis
    * @Param: [key, value]
    * */
    void addInfoToRedis(String key, Object value);
    /*
    * @Description: 添加验证码到Redis
    * @Param: [key, value, expireTime, timeUnit]
    * */
    void addVerifyCodeToRedis(String key, String value, long expireTime, java.util.concurrent.TimeUnit timeUnit);
    /*
    * @Description: 删除Redis中的验证码
    * @Param: [key]
    * */
    void deleteVerifyCodeFromRedis(String key);
    /*
    * @Description: 删除文件
    * @Param: [key]
    * */
    void deleteFileFromQiniu(String key);
}
