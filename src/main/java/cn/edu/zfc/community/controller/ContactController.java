package cn.edu.zfc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zfc.community.dao.ArticleDao;

@RestController
public class ContactController {
    
    @Autowired
    private ArticleDao articleDao;
    
    @RequestMapping("/contact.html")
    public ModelAndView contact() {
        ModelAndView mv = new ModelAndView("contact");
        // 使用articleDao查询数据并添加到模型中
        mv.addObject("articles", articleDao.findAll());
        return mv;
    }
}