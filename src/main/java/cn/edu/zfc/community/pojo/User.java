package cn.edu.zfc.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 用户实体类
@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;         // 用户ID
    private String name;     // 用户名
    private String password;  // 密码
    private String avatar;   // 头像
    private String role;     // 角色
}