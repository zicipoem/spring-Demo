package cn.edu.zfc.community.dao;

import cn.edu.zfc.community.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// 用户数据访问接口
@Repository
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    // 根据用户名查询用户
    User findTopByName(String name);

    // 根据角色查询用户
    User findTopByRole(String role);
}