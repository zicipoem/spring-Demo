package cn.edu.zfc.community.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.zfc.community.dao.UserDao;
import cn.edu.zfc.community.pojo.User;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名查询用户
     */
    public User findTopByName(String name) {
        return userDao.findTopByName(name);
    }

    /**
     * 保存用户
     */
    public void save(User user) {
        userDao.save(user);
    }

    /**
     * 初始化管理员
     */
    @PostConstruct 
    public void initAdmin() {
        User haveAdmin = userDao.findTopByRole("admin");
        if (haveAdmin == null) {
            User admin = new User();
            admin.setName("Elyina");
            admin.setPassword("0721");
            admin.setAvatar("https://ts4.tc.mm.bing.net/th/id/OIP-C.U5MkqCY3FSUkbmJ4t9NbeAHaHa?rs=1&pid=ImgDetMain&o=7&rm=3");
            admin.setRole("admin");
            userDao.save(admin);
        }
    }
}
