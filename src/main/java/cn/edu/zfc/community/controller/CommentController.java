package cn.edu.zfc.community.controller;

import cn.edu.zfc.community.dao.CommentDao;
import cn.edu.zfc.community.pojo.Comment;
import cn.edu.zfc.community.pojo.Nav;
import cn.edu.zfc.community.service.CommentService;
import cn.edu.zfc.community.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 评论控制器
@RestController
@RequestMapping("/comment")
public class CommentController {
    
    // 评论服务
    @Autowired
    private CommentService commentService;
    
    // 评论数据访问对象
    @Autowired
    private CommentDao commentDao;
    
    // 评论管理列表页面
    @RequestMapping("/list")
    public org.springframework.web.servlet.ModelAndView list() {
        org.springframework.web.servlet.ModelAndView mv = new org.springframework.web.servlet.ModelAndView("commentList");
        List<Comment> comments = commentDao.findAll();
        mv.addObject("list", comments);
        mv.addObject("navs", Nav.all("comment"));
        mv.addObject("page", "comment");
        return mv;
    }
    
    // 获取文章评论列表
    @GetMapping("/list/{articleId}")
    public Map<String, Object> getCommentList(@PathVariable Long articleId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Comment> comments = commentService.getCommentsByArticleId(articleId);
            result.put("success", true);
            result.put("data", comments);
            result.put("count", comments.size());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取评论列表失败");
        }
        return result;
    }
    
    // 添加评论
    @PostMapping("/add")
    public Map<String, Object> addComment(@RequestParam Long articleId, 
                                       @RequestParam String content) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 检查用户是否登录
            if (UserUtils.getCurrentUser() == null) {
                result.put("success", false);
                result.put("message", "请先登录");
                return result;
            }
            
            Comment comment = new Comment();
            comment.setArticleId(articleId);
            comment.setUserId(UserUtils.getCurrentUser().getId());
            comment.setContent(content);
            comment.setAvatar(UserUtils.getCurrentUser().getAvatar());
            comment.setCreatedAt(LocalDateTime.now());
            
            Comment savedComment = commentService.addComment(comment);
            result.put("success", true);
            result.put("message", "评论成功");
            result.put("data", savedComment);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "评论失败");
        }
        return result;
    }
    
    // 删除评论
    @PostMapping("/delete/{commentId}")
    public Map<String, Object> deleteComment(@PathVariable Long commentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 检查用户是否登录
            if (UserUtils.getCurrentUser() == null) {
                result.put("success", false);
                result.put("message", "请先登录");
                return result;
            }
            
            boolean success = commentService.deleteComment(commentId);
            if (success) {
                result.put("success", true);
                result.put("message", "删除成功");
            } else {
                result.put("success", false);
                result.put("message", "评论不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败");
        }
        return result;
    }
    
}