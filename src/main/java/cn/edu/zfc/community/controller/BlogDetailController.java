package cn.edu.zfc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zfc.community.dao.ArticleDao;
import cn.edu.zfc.community.pojo.Article;
import cn.edu.zfc.community.utils.UserUtils;

import java.util.Optional;

@RestController
public class BlogDetailController {

    @Autowired
    private ArticleDao articleDao;
    
    @RequestMapping("/blog-detail.html")
    public ModelAndView blogDetail(@RequestParam(defaultValue = "1") Long id) {
        ModelAndView mv = new ModelAndView("blog-detail");
        
        // 获取文章详情
        Optional<Article> article = articleDao.findById(id);
        if (article.isPresent()) {
            mv.addObject("article", article.get());
        }
        
        // 获取所有文章列表
        mv.addObject("articles", articleDao.findAll());
        mv.addObject("articleId", id);  // 传递文章ID供点赞收藏功能使用

        mv.addObject("currentUser", UserUtils.getCurrentUser());
        mv.addObject("isLogin", UserUtils.getCurrentUser() != null);
        return mv;
    }
}
