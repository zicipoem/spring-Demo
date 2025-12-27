package cn.edu.zfc.community.controller;

import cn.edu.zfc.community.dao.CustomerDao;
import cn.edu.zfc.community.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private CustomerDao customerDao;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        Customer customer = customerDao.findByUsername(username);
        if (customer != null && customer.getPassword().equals(password)) {
            // 登录成功，重定向到首页
            return "redirect:/index.html";
        } else {
            // 登录失败，返回登录页面并显示错误信息
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }
}