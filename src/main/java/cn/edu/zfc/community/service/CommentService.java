package cn.edu.zfc.community.service;

import cn.edu.zfc.community.dao.CommentDao;
import cn.edu.zfc.community.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 评论服务类
@Service
public class CommentService {
    
    // 评论数据访问对象
    @Autowired
    private CommentDao commentDao;
    
    // 根据文章ID获取评论列表
    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentDao.findByArticleId(articleId);
    }
    
    // 添加评论
    public Comment addComment(Comment comment) {
        return commentDao.save(comment);
    }
    
    // 删除评论
    public boolean deleteComment(Long commentId) {
        Comment comment = commentDao.findById(commentId).orElse(null);
        if (comment != null) {
            commentDao.delete(comment);
            return true;
        }
        return false;
    }
    

    
    // 统计文章评论数
    public Long countCommentsByArticleId(Long articleId) {
        return commentDao.countByArticleId(articleId);
    }
    
}