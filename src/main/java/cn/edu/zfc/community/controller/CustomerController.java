package cn.edu.zfc.community.controller;

import cn.edu.zfc.community.dao.CustomerDao;
import cn.edu.zfc.community.pojo.Customer;
import cn.edu.zfc.community.pojo.Nav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 使用RestController注解，表示全部接口返回的都是json格式的数据
 * 使用RequestMapping注解，标识接口请求地址
 * 使用Autowired注解，实现依赖注入
 * 使用ModelAttribute注解，将表单提交的数据封装到Customer对象中
 * 使用PathVariable注解，将路径参数绑定到方法参数上
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerDao customerDao;

    @RequestMapping("/add/auto")
    public Customer addByAuto(String jobNo, String name, String department) {
        Customer customer = new Customer(null, jobNo, name, department);
        return customerDao.save(customer);
    }

    @RequestMapping("/add/model")
    public Customer addByModel(@ModelAttribute Customer customer) {
        return customerDao.save(customer);
    }

    @RequestMapping("/query/path/{id}")
    public Customer queryByPath(@PathVariable("id") Long id) {
        return customerDao.findById(id).orElse(null);
    }

    @RequestMapping("/del/path/{id}")
    public void delByPath(@PathVariable("id") Long id) {
        customerDao.deleteById(id);
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("customerList");// 指定视图名称
        mv.addObject("list", customerDao.findAll());// 将领域模型数据添加到视图中
        mv.addObject("navs", Nav.all("customer"));
        return mv;
    }
}
