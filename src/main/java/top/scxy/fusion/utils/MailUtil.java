package top.scxy.fusion.utils;

public class MailUtil {
    /*
    * @Description: 生成随机六位验证码
    * @Param: []
    * @return: java.lang.String
    * */
    public static String getRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((int) (Math.random() * 10));
        }
        return code.toString();
    }
    /*
    * @Description: 生成邮件内容
    * @Param: [code]
    * @return: java.lang.String
    * */
    public static String getEmailContent(String code) {
        return "<Fusion>您的验证码为：" + code + "，请在5分钟内使用。";
    }
}
