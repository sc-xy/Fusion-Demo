package top.scxy.fusion.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.scxy.fusion.constant.UtilConstant;
import top.scxy.fusion.entity.UserInfo;
import top.scxy.fusion.service.EmailService;
import top.scxy.fusion.service.UserService;

@Controller
public class LoginController {
    private final UserService userService;
    private final EmailService mailService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public LoginController(UserService userService, EmailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }
    /*
     * @Description: 登陆界面
     * @Param: []
     * @return: java.lang.String
     * */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    /*
     * @Description: 首页
     * @Param: []
     * @return: java.lang.String
     * */
    @GetMapping("/index")
    public String index(@ModelAttribute("message") String message) {
        if(message != null) {
            logger.info(message);
        }
        return "index";
    }
    /*
    * @Description: 登录
    * @Param: [username, password, session]
    * @return: java.lang.String
    * */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
                        RedirectAttributes attributes) {
        Integer userId = userService.checkUserWithUsernameAndPassword(username, password);
        if (userId != null) {
            attributes.addFlashAttribute("message", "登录成功");
            session.setAttribute("userId", userId);
            return "redirect:/blogIndex";
        }
        attributes.addFlashAttribute("message", "用户名或密码错误");
        return "redirect:/login";
    }
    /*
    * @Description: 注册
    * @Param: [username, reg_password, email, verifyCode, nickname, avatar, attributes]
    * @return: java.lang.String
    * */
    @PostMapping("/userInfo")
    public String register(@RequestParam String reg_username, @RequestParam String reg_password, @RequestParam String email,
                            @RequestParam String reg_verifyCode,@RequestParam String nickname, RedirectAttributes attributes){
        if(!mailService.verifyCode(email, reg_verifyCode, UtilConstant.EmailVerifyCodePrefix)) {
            attributes.addFlashAttribute("message", "验证码错误");
            return "redirect:/login";
        }
        Integer userId = userService.register(reg_username, reg_password, email, nickname, "测试");
        if (userId != null) {
            attributes.addFlashAttribute("message", "注册成功");
            return "redirect:/login";
        }
        attributes.addFlashAttribute("message", "注册失败");
        return "redirect:/login";
    }
    @GetMapping("/forget")
    public String forget() {
        return "forget";
    }
}
