package cn.edu.zfc.community.controller;

import cn.edu.zfc.community.pojo.Message;
import cn.edu.zfc.community.pojo.Nav;
import cn.edu.zfc.community.service.MessageService;
import cn.edu.zfc.community.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/message")
@RequiresRoles("admin")
public class MessageAdminController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("messageList");
        
        mv.addObject("list", messageService.listMessages());
        mv.addObject("navs", Nav.all("message"));
        mv.addObject("page", "message");
        return mv;
    }

    @PostMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (UserUtils.getCurrentUser() == null) {
                result.put("success", false);
                result.put("message", "请先登录");
                return result;
            }
            boolean ok = messageService.deleteMessage(id);
            result.put("success", ok);
            result.put("message", ok ? "删除成功" : "留言不存在");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败");
        }
        return result;
    }

    @GetMapping("/view/{id}")
    public Map<String, Object> view(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        Message m = messageService.getMessageById(id);
        if (m != null) {
            result.put("success", true);
            result.put("data", m);
        } else {
            result.put("success", false);
            result.put("message", "留言不存在");
        }
        return result;
    }
}