package cn.edu.zfc.community.controller;

import cn.edu.zfc.community.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("/index.html")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("list", articleDao.findAll());
        return mv;
    }

    @RequestMapping("/blog.html")
    public ModelAndView blog() {
        ModelAndView mv = new ModelAndView("blog");
        return mv;
    }
}
