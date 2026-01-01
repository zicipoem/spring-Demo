package cn.edu.zfc.community.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // 登录
    @RequestMapping("/dologin")
    public String dologin(String username, String password) {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
            return "redirect:/index.html";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }
}
