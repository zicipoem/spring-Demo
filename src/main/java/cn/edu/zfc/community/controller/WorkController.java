package cn.edu.zfc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zfc.community.dao.ArticleDao;
import cn.edu.zfc.community.utils.UserUtils;

@RestController
public class WorkController {

    @Autowired
    private ArticleDao articleDao;
    
    @RequestMapping("/work.html")
    public ModelAndView work() {
        ModelAndView mv = new ModelAndView("work");
        mv.addObject("isAdmin", UserUtils.isAdmin());
        mv.addObject("user", UserUtils.getCurrentUser());
        mv.addObject("articles", articleDao.findAll());
        return mv;
    }

}