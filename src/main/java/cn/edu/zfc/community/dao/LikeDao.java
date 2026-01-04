package cn.edu.zfc.community.dao;

import cn.edu.zfc.community.pojo.Like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// 点赞数据访问接口
@Repository
public interface LikeDao extends JpaRepository<Like, Long> {
    // 根据文章ID和用户ID查找点赞记录
    Like findByArticleIdAndUserId(Long articleId, Long userId);

    // 统计指定文章的点赞数量
    @Query("SELECT COUNT(l) FROM Like l WHERE l.articleId = :articleId")
    Long countByArticleId(@Param("articleId") Long articleId);

    // 删除指定文章的点赞记录
    void deleteByArticleIdAndUserId(Long articleId, Long userId);
}