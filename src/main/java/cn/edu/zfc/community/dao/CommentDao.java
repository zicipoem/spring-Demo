package cn.edu.zfc.community.dao;

import cn.edu.zfc.community.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
    
    /**
     * 根据文章ID查询评论列表
     */
    List<Comment> findByArticleId(Long articleId);
    
    /**
     * 统计指定文章的评论数量
     */
    Long countByArticleId(Long articleId);
    
    /**
     * 查询最新的评论（按时间倒序）
     */
    @Query("SELECT c FROM Comment c ORDER BY c.createdAt DESC")
    List<Comment> findLatestComments();
}