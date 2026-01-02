package cn.edu.zfc.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 评论ID
    
    @Column(name = "article_id", nullable = false)
    private Long articleId;             // 文章ID
    
    @Column(name = "user_id", nullable = false)
    private Long userId;                // 用户ID
    
    @Column(name = "content", nullable = false, length = 1000)
    private String content;              // 评论内容
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;    // 评论时间

    
}