package cn.edu.zfc.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 评论ID（主键）
    
    @Column(name = "article_id", nullable = false)
    private Long articleId;             // 文章ID
    
    @Column(name = "user_id", nullable = false)
    private Long userId;                // 用户ID
    
    @Column(name = "avatar", nullable = false, length = 100)
    private String avatar;              // 用户头像
    
    @Column(name = "content", nullable = false, length = 1000)
    private String content;              // 评论内容
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;    // 评论时间

    
}