package cn.edu.zfc.community.dao;

import cn.edu.zfc.community.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
    /**
     * 统计指定文章的评论数量
     */
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.articleId = :articleId")
    Long countByArticleId(@Param("articleId") Long articleId);
    
    /**
     * 根据文章ID查找评论
     */
    List<Comment> findByArticleId(Long articleId);
    
    /**
     * 根据文章ID删除所有评论
     */
    void deleteByArticleId(Long articleId);
}