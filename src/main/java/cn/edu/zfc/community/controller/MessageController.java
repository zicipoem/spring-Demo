package cn.edu.zfc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zfc.community.dao.ArticleDao;
import cn.edu.zfc.community.utils.UserUtils;
import cn.edu.zfc.community.service.MessageService;
import cn.edu.zfc.community.pojo.Message;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {
    
    @Autowired
    private ArticleDao articleDao;
    
    @Autowired
    private MessageService messageService;
    
    @RequestMapping("/message.html")
    public ModelAndView message() {
        ModelAndView mv = new ModelAndView("message");
        mv.addObject("isAdmin", UserUtils.isAdmin());
        mv.addObject("user", UserUtils.getCurrentUser());
        // 使用articleDao查询数据并添加到模型中
        mv.addObject("articles", articleDao.findAll());
        // 添加留言列表
        mv.addObject("messages", messageService.listMessages());
        return mv;
    }

    @PostMapping("/message/add")
    public Map<String, Object> addMessage(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String email,
                                          @RequestParam String content) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (content == null || content.trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "留言内容不能为空");
                return result;
            }
            Message m = new Message();
            m.setName(name == null || name.trim().isEmpty() ? "匿名" : name.trim());
            m.setEmail(email == null ? "" : email.trim());
            m.setContent(content.trim());
            Message saved = messageService.addMessage(m);
            result.put("success", true);
            result.put("message", "留言成功");
            result.put("data", saved);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "留言失败");
        }
        return result;
    }
}