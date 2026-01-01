package cn.edu.zfc.community.controller;

import cn.edu.zfc.community.dao.ArticleDao;
import cn.edu.zfc.community.dao.FavoriteDao;
import cn.edu.zfc.community.dao.LikeDao;
import cn.edu.zfc.community.pojo.Article;
import cn.edu.zfc.community.pojo.Favorite;
import cn.edu.zfc.community.pojo.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 点赞收藏控制器
 */
@RestController
@RequestMapping("/social")
public class SocialController {
    
    @Autowired
    private LikeDao likeDao;
    
    @Autowired
    private FavoriteDao favoriteDao;
    
    @Autowired
    private ArticleDao articleDao;
    
    /**
     * 点赞文章
     */
    @PostMapping("/like")
    public Map<String, Object> likeArticle(@RequestParam Long articleId, @RequestParam Long userId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Like existingLike = likeDao.findByArticleIdAndUserId(articleId, userId);
            
            if (existingLike != null) {
                // 已经点赞过，取消点赞
                likeDao.delete(existingLike);
                Article article = articleDao.findById(articleId).orElse(null);
                if (article != null && article.getLikeCount() > 0) {
                    article.setLikeCount(article.getLikeCount() - 1);
                    articleDao.save(article);
                }
                result.put("action", "unliked");
                result.put("message", "取消点赞成功");
            } else {
                // 点赞
                Like like = new Like();
                like.setArticleId(articleId);
                like.setUserId(userId);
                likeDao.save(like);
                
                Article article = articleDao.findById(articleId).orElse(null);
                if (article != null) {
                    article.setLikeCount(article.getLikeCount() + 1);
                    articleDao.save(article);
                }
                result.put("action", "liked");
                result.put("message", "点赞成功");
            }
            
            // 返回更新后的点赞数
            Long likeCount = likeDao.countByArticleId(articleId);
            result.put("likeCount", likeCount);
            result.put("success", true);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "操作失败：" + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 收藏文章
     */
    @PostMapping("/favorite")
    public Map<String, Object> favoriteArticle(@RequestParam Long articleId, @RequestParam Long userId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Favorite existingFavorite = favoriteDao.findByArticleIdAndUserId(articleId, userId).orElse(null);
            
            if (existingFavorite != null) {
                // 已经收藏过，取消收藏
                favoriteDao.delete(existingFavorite);
                Article article = articleDao.findById(articleId).orElse(null);
                if (article != null && article.getFavoriteCount() > 0) {
                    article.setFavoriteCount(article.getFavoriteCount() - 1);
                    articleDao.save(article);
                }
                result.put("action", "unfavorited");
                result.put("message", "取消收藏成功");
            } else {
                // 收藏
                Favorite favorite = new Favorite();
                favorite.setArticleId(articleId);
                favorite.setUserId(userId);
                favoriteDao.save(favorite);
                
                Article article = articleDao.findById(articleId).orElse(null);
                if (article != null) {
                    article.setFavoriteCount(article.getFavoriteCount() + 1);
                    articleDao.save(article);
                }
                result.put("action", "favorited");
                result.put("message", "收藏成功");
            }
            
            // 返回更新后的收藏数
            Long favoriteCount = favoriteDao.countByArticleId(articleId);
            result.put("favoriteCount", favoriteCount);
            result.put("success", true);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "操作失败：" + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 获取用户对指定文章的点赞收藏状态
     */
    @GetMapping("/status")
    public Map<String, Object> getSocialStatus(@RequestParam Long articleId, @RequestParam Long userId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            boolean liked = likeDao.findByArticleIdAndUserId(articleId, userId) != null;
            boolean favorited = favoriteDao.findByArticleIdAndUserId(articleId, userId) != null;
            
            Long likeCount = likeDao.countByArticleId(articleId);
            Long favoriteCount = favoriteDao.countByArticleId(articleId);
            
            result.put("liked", liked);
            result.put("favorited", favorited);
            result.put("likeCount", likeCount);
            result.put("favoriteCount", favoriteCount);
            result.put("success", true);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取状态失败：" + e.getMessage());
        }
        
        return result;
    }
}