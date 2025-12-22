package cn.edu.zfc.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 使用@Entity标识类为实体类
 * 使用@Table进行数据库表的映射
 * 使用@Id标识属性为数据库表的主键
 * 使用@GeneratedValue配置主键的自增策略
 * 使用@Data生成get和set方法
 * 使用@**Constructor生成构造方法
 */
@Entity
@Table(name = "tb_customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobNo;
    private String name;
    private String department;
}
