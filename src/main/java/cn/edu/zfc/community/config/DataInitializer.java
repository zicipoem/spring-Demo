package cn.edu.zfc.community.config;

import cn.edu.zfc.community.dao.UserDao;
import cn.edu.zfc.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    @Override
    public void run(String... args) throws Exception {
        // 检查是否已有用户，如果没有则创建一个默认用户
        if (userDao.count() == 0) {
            User defaultUser = new User();
            defaultUser.setUsername("admin");
            defaultUser.setPassword("123456");
            userDao.save(defaultUser);
            System.out.println("默认用户已创建：用户名 Elyina，密码 123");
        }
    }
}