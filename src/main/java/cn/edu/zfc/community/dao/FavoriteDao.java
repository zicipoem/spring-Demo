package cn.edu.zfc.community.dao;

import cn.edu.zfc.community.pojo.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteDao extends JpaRepository<Favorite, Long> {
    
    /**
     * 根据文章ID和用户ID查找收藏记录
     */
    Optional<Favorite> findByArticleIdAndUserId(Long articleId, Long userId);
    
    /**
     * 统计指定文章的收藏数量
     */
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.articleId = :articleId")
    Long countByArticleId(@Param("articleId") Long articleId);
    
    /**
     * 删除指定文章的收藏记录
     */
    void deleteByArticleIdAndUserId(Long articleId, Long userId);
    
    /**
     * 查找用户的所有收藏
     */
    @Query("SELECT f FROM Favorite f WHERE f.userId = :userId ORDER BY f.createdAt DESC")
    java.util.List<Favorite> findByUserId(@Param("userId") Long userId);
}