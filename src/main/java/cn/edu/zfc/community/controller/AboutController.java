package cn.edu.zfc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zfc.community.dao.ArticleDao;

@RestController
public class AboutController {
    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("/about.html")
    public ModelAndView about() { 
        ModelAndView mv = new ModelAndView("about");
        mv.addObject("articleList", articleDao.findAll());
        return mv;
    }
}