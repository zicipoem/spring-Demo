package cn.edu.zfc.community.controller;


import cn.edu.zfc.community.dao.ArticleDao;
import cn.edu.zfc.community.pojo.Article;
import cn.edu.zfc.community.pojo.Nav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 使用RestController注解，表示该类为控制器，负责将领域模型数据提供给视图
 * 使用Autowired注解，依赖注入Spring容器管理的Bean
 * 使用RequestMapping注解，表示方法为接口方法，负责映射请求路径
 * 使用ModelAttribute注解，表示该方法参数为领域模型数据
 * 使用PathVariable注解，表示该方法参数为路径参数
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("/add/model")
    public Article addByModel(@ModelAttribute Article article) {
        return articleDao.save(article);
    }
    
    @RequestMapping("/delete/path/{id}")
    public void deleteByPath(@PathVariable("id") Long id) {
        articleDao.deleteById(id);
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("articleList");// 指定视图名称
        mv.addObject("list", articleDao.findAll());// 将领域模型数据添加到视图中
        mv.addObject("navs", Nav.all("article"));
        return mv;
    }
}
