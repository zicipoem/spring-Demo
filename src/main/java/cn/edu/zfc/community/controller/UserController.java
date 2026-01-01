package cn.edu.zfc.community.controller;

import cn.edu.zfc.community.dao.UserDao;
import cn.edu.zfc.community.pojo.User;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiresRoles("admin")
public class UserController {

    @Autowired
    private UserDao userDao;
    
    // 用户管理页面
    @GetMapping("/user/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("userList");
        mv.addObject("list", userDao.findAll());
        mv.addObject("page","user");
        return mv;
    }
    
    @PostMapping("/user/add/model")
    public User addByModel(@org.springframework.web.bind.annotation.ModelAttribute User user) {
        return userDao.save(user);
    }
    
    @GetMapping("/user/delete/path/{id}")
    public void deleteByPath(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        userDao.deleteById(id);
    }
}