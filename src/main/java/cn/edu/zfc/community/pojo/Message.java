package cn.edu.zfc.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

// 留言实体类
@Entity
@Table(name = "tb_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 留言ID

    @Column(name = "name", nullable = true)
    private String name;                // 留言人姓名

    @Column(name = "email", nullable = true)
    private String email;               // 留言人邮箱

    @Column(name = "content", nullable = false, length = 2000)
    private String content;             // 留言内容

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;    // 留言时间

    // 持久化前的回调方法
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
