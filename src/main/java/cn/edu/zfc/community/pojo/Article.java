package cn.edu.zfc.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 新闻ID
    private String title;               // 新闻标题
    private String summary;             // 新闻摘要
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;             // 新闻内容（HTML格式）
    private String coverImage;          // 封面图片URL
    @ElementCollection
    @CollectionTable(name = "tb_article_images", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "image_url")
    private List<String> images;        // 新闻图片列表
    
    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;      // 点赞数量
    
    @Column(name = "favorite_count", nullable = false)
    private Integer favoriteCount = 0;  // 收藏数量
}
