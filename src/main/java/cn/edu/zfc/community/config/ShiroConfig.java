package cn.edu.zfc.community.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.edu.zfc.community.service.UserService;

@Configuration
public class ShiroConfig {

    @Bean
    public org.apache.shiro.realm.Realm realm(UserService userService) {
        return new Realm(userService);
    }

    // 配置Shiro过滤链，定义URL路径和对应的Shiro拦截器
    @Bean
    public ShiroFilterChainDefinition filter() {
        DefaultShiroFilterChainDefinition cd = new DefaultShiroFilterChainDefinition();

        // 登录
        cd.addPathDefinition("/login", "anon");
        cd.addPathDefinition("/dologin", "anon");

        // 静态资源
        cd.addPathDefinition("/assets/**", "anon");

        // 登出路径
        cd.addPathDefinition("/logout", "logout");

        // 剩下的路径都需要认证
        cd.addPathDefinition("/**", "authc");

        return cd;
    }

    // 启用Shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    // 启用代理支持，让Shiro注解能够被拦截执行
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
