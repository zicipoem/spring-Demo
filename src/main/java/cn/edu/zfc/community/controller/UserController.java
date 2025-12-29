package cn.edu.zfc.community.controller;

import cn.edu.zfc.community.dao.UserDao;
import cn.edu.zfc.community.pojo.User;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/login.html")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView login(String username, String password, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // 登录成功，保存用户到 session 并重定向到文章管理页
            session.setAttribute("user", user);
            mv.setViewName("redirect:/article/list");
        } else {
            // 登录失败，返回登录页面并显示错误信息
            mv.setViewName("login");
            mv.addObject("error", "用户名或密码错误");
        }
        
        return mv;
    }
}