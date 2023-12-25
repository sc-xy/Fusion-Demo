package top.scxy.fusion.service;

public interface EmailService {
    /*
    * @Description: 发送验证码邮件，通过前缀区分注册和找回密码
    * @Param: [to, prefix]
    * @return: boolean
    * */
    boolean sendVerifyEmail(String to, String prefix);
    /*
    * @Description: 验证验证码
    * @Param: [email, code, prefix
    * @return: boolean
    * */
    boolean verifyCode(String email, String code, String prefix);
}
