package top.scxy.fusion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.scxy.fusion.constant.UtilConstant;
import top.scxy.fusion.service.EmailService;
import top.scxy.fusion.service.UserService;

@RestController
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }
    /*
    * @Description: 发送注册验证码
    * @Param: [email]
    * @return: java.lang.String
    * */
    @PostMapping("/email")
    public String email(@RequestParam String email) {
        logger.info("register email: " + email);
        if (emailService.sendVerifyEmail(email, UtilConstant.EmailVerifyCodePrefix)) {
            logger.info("send email success");
            return "success";
        }
        return "fail";
    }
    /*
    * @Description: 发送找回密码验证码
    * @Param: [new_username]
    * @return: java.lang.String
    * */
    @PostMapping("/email/forget")
    public String forget(@RequestParam String username) {
        String mail = userService.getUserEmailByUsername(username);
        logger.info("forget email: " + mail);
        if (mail==null) {
            return "no such user";
        }
        if (emailService.sendVerifyEmail(mail, UtilConstant.EmailForgetPasswordPrefix)) {
            logger.info("send email success");
            return "success";
        }
        return "fail";
    }
}
