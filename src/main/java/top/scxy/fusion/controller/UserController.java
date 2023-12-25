package top.scxy.fusion.controller;

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
public class UserController {
    private final UserService userService;
    private final EmailService mailService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public UserController(UserService userService, EmailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }
    /*
     * @Description: 用户名是否存在
     * @Param: [username]
     * @return: java.lang.String
     * */
    @GetMapping("/userMessage/{username}")
    @ResponseBody
    public String username(@PathVariable String username) {
        logger.info("username check exist: " + username);
        if (userService.getUserIdByUsername(username) != null) {
            return "exist";
        }
        return "not exist";
    }
    /*
     * @Description: 更改信息界面
     * @Param: []
     * @return: java.lang.String
     * */
    @GetMapping("/userMessage")
    public String userInfo() {
        return "userMessage";
    }
    /*
     * @Description: 获取用户信息
     * @Param: [session]
     * @return: top.scxy.fusion.entity.UserInfo
     * */
    @GetMapping("/userInfo")
    @ResponseBody
    public UserInfo getUserInfo(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return userService.getUserInfo(userId);
    }
    /*
     * @Description: 更新用户信息
     * @Param: [nickname, avatar, session, attributes]                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              ]
     * @return: java.lang.String
     * */
    @PutMapping("/userInfo")
    public String updateUserInfo(
            @RequestParam String nickname, HttpSession session,
            RedirectAttributes attributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        userService.updateUserInfo(userId, nickname, "测试");
        attributes.addFlashAttribute("message", "更改成功");
        return "redirect:/userMessage";
    }
    /*
     * @Description: 更新用户邮箱
     * @Param: [email, verifyCode, session, attributes]
     * @return: java.lang.String
     * */
    @PutMapping("/email")
    public String updateUserEmail(@RequestParam String email, @RequestParam String verifyCode, HttpSession session,
                                  RedirectAttributes attributes) {
        if(!mailService.verifyCode(email, verifyCode, UtilConstant.EmailVerifyCodePrefix)) {
            attributes.addFlashAttribute("message", "验证码错误");
            return "redirect:/userMessage";
        }
        Integer userId = (Integer) session.getAttribute("userId");
        userService.updateUserEmail(userId, email);
        attributes.addFlashAttribute("message", "更新成功");
        return "redirect:/userMessage";
    }
    /*
     * @Description: 重置密码，处理忘记密码
     * @Param: [new_username, new_password, new_verifyCode, attributes]
     * @return: java.lang.String
     * */
    @PutMapping("/password")
    public String resetPassword(@RequestParam String username, @RequestParam String password,
                                @RequestParam String verifyCode, RedirectAttributes attributes) {
        if(!mailService.verifyCode(userService.getUserEmailByUsername(username),
                verifyCode, UtilConstant.EmailForgetPasswordPrefix)) {
            attributes.addFlashAttribute("message", "验证码错误");
            return "redirect:/forget";
        }
        userService.updateUserPassword(userService.getUserIdByUsername(username), password);
        attributes.addFlashAttribute("message", "密码重置成功");
        return "redirect:/login";
    }
    /*
    * @Description: 重置密码，处理更新密码
    * @Param: [old_password, new_password, session, attributes]
    * */
    @PutMapping("/password/update")
    public String updatePassword(@RequestParam String old_password, @RequestParam String new_password, HttpSession session,
                                 RedirectAttributes attributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        if(userService.checkUserWithUsernameAndPassword(userService.getUsernameById(userId), old_password)==null) {
            attributes.addFlashAttribute("message", "原密码错误");
            return "redirect:/userMessage";
        }
        userService.updateUserPassword(userId, new_password);
        attributes.addFlashAttribute("message", "密码更改成功");
        return "redirect:/login";
    }
    /*
    * @Description: 获取用户名
    * @Param: [session]
    * @return: java.lang.String
    * */
    @GetMapping("/username")
    @ResponseBody
    public String getUsername(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return userService.getUsernameById(userId);
    }
    /*
    * @Description: 异常测试
    * @Param: []
    * */
    @GetMapping("/exception")
    public String exception() {
        throw new RuntimeException("测试异常");
    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
