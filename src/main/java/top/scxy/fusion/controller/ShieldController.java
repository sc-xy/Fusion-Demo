package top.scxy.fusion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.scxy.fusion.service.ShieldService;

@Controller
public class ShieldController {
    private final ShieldService shieldService;

    public ShieldController(ShieldService shieldService) {
        this.shieldService = shieldService;
    }

    /*
     * @Description: 查询是否已经屏蔽
     * @Param: [user_name, shield_name]
     * @return: Integer
     * */
    @GetMapping("/shield")
    @ResponseBody
    public Integer selectShield(@RequestParam("user_name") String user_name, @RequestParam("shield_name") String shield_name) {
        // 这里需要交替查询
        Integer result1 = shieldService.selectShield(user_name, shield_name);
        Integer result2 = shieldService.selectShield(shield_name, user_name);
        return (result1 | result2) == 0 ? 0 : 1;
    }

    /*
     * @Description: 屏蔽用户
     * @Param: [user_name, shield_name]
     * @return: Integer
     * */
    @PostMapping("/shield")
    @ResponseBody
    public Integer shield(@RequestParam("user_name") String user_name, @RequestParam("shield_name") String shield_name) {
        return shieldService.shield(user_name, shield_name);
    }
}
