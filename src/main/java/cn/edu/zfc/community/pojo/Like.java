package cn.edu.zfc.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

// 点赞实体类
@Entity
@Table(name = "tb_like")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 点赞ID
    
    @Column(name = "article_id", nullable = false)
    private Long articleId;             // 文章ID
    
    @Column(name = "user_id", nullable = false)
    private Long userId;                // 用户ID
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;    // 点赞时间
    
    // 创建前的回调方法
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}